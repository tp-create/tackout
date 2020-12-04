package cn.htzb.easybuy.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.htzb.easybuy.biz.FacilityService;
import cn.htzb.easybuy.biz.OrderService;
import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.biz.UserService;
import cn.htzb.easybuy.biz.impl.FacilityServiceImpl;
import cn.htzb.easybuy.biz.impl.OrderServiceImpl;
import cn.htzb.easybuy.biz.impl.ProductServiceImpl;
import cn.htzb.easybuy.biz.impl.UserServiceImpl;
import cn.htzb.easybuy.entity.News;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.entity.ShoppingCart;
import cn.htzb.easybuy.entity.ShoppingCartItem;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/Cart"},name = "Cart")

public class CartServlet extends HttpServlet {
	protected Map<String, ActionResult> viewMapping = new HashMap<String, ActionResult>();
	private ProductService productService;
	private FacilityService facilityService;
	private OrderService orderService;

	public void init() throws ServletException {
		productService = new ProductServiceImpl();
		facilityService = new FacilityServiceImpl();
		orderService = new OrderServiceImpl();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		createViewMapping();
		String actionIndicator = req.getParameter("action");
		String result = "";
		if (actionIndicator == null)
			actionIndicator = "list";
		if ("list".endsWith(actionIndicator)) {
			result = list(req);
		} else if ("add".endsWith(actionIndicator)) {
			result = add(req);
		} else if ("addBuy".endsWith(actionIndicator)) {
			 add(req);//放入购物车
			 result ="addAddressSuccess";//添加地址
		}else if ("mod".endsWith(actionIndicator)) {//编辑
			result = mod(req);
		} else if ("remove".endsWith(actionIndicator)) {//删除
			result = remove(req);
		} else if ("pay".endsWith(actionIndicator)) {//购买
			result = pay(req);
		}else if ("address".endsWith(actionIndicator)) {//寻找此用户的所有收货地址
			result = address(req);
		}else if("addAddress".endsWith(actionIndicator)){//添加用户的新收货地址
			result = addAddress(req);
		}
		toView(req, resp, result);
	}
	private String addAddress(HttpServletRequest request) {		
		String address=request.getParameter("address");
		//点击"添加新地址"时传的中文乱码处理
		if(request.getMethod().equals("GET") && address!=null){
			try {
				address = new String (address.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}		
		UserService userService=new UserServiceImpl();		
		User user = getUserFromSession(request);
		userService.addAdress(user, address);//向数据库添加新地址
		user.setAddress(user.getAddress()+";"+address);//修改用户地址
		return "addAddressSuccess";
	}
	private String address(HttpServletRequest request) {		
		User user = getUserFromSession(request);
		if(user==null)
			return "login";
		String address=user.getAddress();
		String []add=address.split(";");				
		request.setAttribute("address", add);
		return "addressSuccess";
	}
	private String pay(HttpServletRequest request) {
		ShoppingCart cart = getCartFromSession(request);
		User user = getUserFromSession(request);
		if(user==null)
			return "login";
		String address=(String)request.getParameter("address");
		orderService.payShoppingCart(cart, user,address);
		removeCartFromSession(request);
		request.setAttribute("msg", "恭喜：购买成功！");
		return "paySuccess";
	}

	private void removeCartFromSession(HttpServletRequest request) {
		//结账后清空购物车
		request.getSession().removeAttribute("cart");
		request.getSession().removeAttribute("cart2");
	}

	private User getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("loginUser");
	}

	private String add(HttpServletRequest request) {
		//若用户尚未登录则不能购买商品		
		String id = request.getParameter("entityId");
		String quantityStr = request.getParameter("quantity");
		long quantity = 1;
		if (!Validator.isEmpty(quantityStr))
			quantity = Long.parseLong(quantityStr);
		Product product = productService.findById(id);
		ShoppingCart cart = getCartFromSession(request);
		cart.addItem(product, quantity);
		request.setAttribute("msg", "已经添加到购物车！");		
		return "addSuccess";
	}

	private String mod(HttpServletRequest request) {
		String id = request.getParameter("entityId");
		String quantityStr = request.getParameter("quantity");
		long quantity = 1;
		if (!Validator.isEmpty(quantityStr))
			quantity = Long.parseLong(quantityStr);
		String indexStr = request.getParameter("index");
		ShoppingCart cart = getCartFromSession(request);
		cart.modifyQuantity(Integer.parseInt(indexStr), quantity);
		return "modSuccess";
	}

	private String remove(HttpServletRequest request) {
		String id = request.getParameter("entityId");
		String quantityStr = request.getParameter("quantity");
		long quantity = 1;
		if (!Validator.isEmpty(quantityStr))
			quantity = Long.parseLong(quantityStr);
		String indexStr = request.getParameter("index");
		ShoppingCart cart = getCartFromSession(request);
		cart.getItems().remove(Integer.parseInt(indexStr));
		return "removeSuccess";
	}

	private ShoppingCart getCartFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		double total=0;//计算总价格
		for(int i=0;cart!=null&&i<cart.getItems().size();i++){
			ShoppingCartItem item=cart.getItems().get(i);			
			total+=(item.getCost()*item.getQuantity());
		}
		//获取当前页
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}else{//获取本页要显示的内容	
			ShoppingCart cart2 = new ShoppingCart();//获取购物车当前页商品
			cart2.items=new ArrayList<ShoppingCartItem> ();				
			Pager pager = new Pager((cart.items.size()+1)/2,1, pageNo);
			for(int i=(pageNo-1)*2;i<(pageNo)*2&&i<cart.getItems().size();i++){//从购物车中选出本页商品
				cart2.items.add(cart.getItems().get(i));
			}			
			request.setAttribute("pageNo", pageNo);//当前页
			session.setAttribute("cart2", cart2);//当前页显示的商品
			session.setAttribute("cart", cart);//全部的商品
			request.setAttribute("total", total);//总价格
			request.setAttribute("pager", pager);
		}
		return cart;
	}

	private String list(HttpServletRequest request) {
		getCartFromSession(request);
		return "listSuccess";
	}

	private void prepareCategories(HttpServletRequest request) {
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
	}

	private void prepareNews(HttpServletRequest request) {
		List<News> allNews = facilityService.getAllNews(new Pager(10, 1));
		request.setAttribute("allNews", allNews);
	}

	protected void createViewMapping() {
		this.addMapping("listSuccess", "shopping.jsp");
		this.addMapping("paySuccess", "shopping-put.jsp");
		this.addMapping("addSuccess", "shopping-put.jsp");		
		this.addMapping("removeSuccess", "Cart", true);
		this.addMapping("modSuccess", "Cart", true);
		this.addMapping("login", "login.jsp");
		this.addMapping("addressSuccess", "address.jsp");
		this.addMapping("addAddressSuccess", "Cart?action=address", true);
	}

	private void toView(HttpServletRequest req, HttpServletResponse resp,
			String result) throws IOException, ServletException {
		ActionResult dest = this.viewMapping.get(result);
		if (dest.isRedirect()) {
			resp.sendRedirect(dest.getViewName());
		} else {
			req.getRequestDispatcher(dest.getViewName()).forward(req, resp);
		}
	}

	protected void addMapping(String viewName, String url) {
		this.viewMapping.put(viewName, new ActionResult(url));
	}

	protected void addMapping(String viewName, String url, boolean isDirect) {
		this.viewMapping.put(viewName, new ActionResult(url, isDirect));
	}
}
