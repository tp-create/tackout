package cn.htzb.easybuy.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.biz.impl.ProductServiceImpl;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/manage/Product","/Product"},name = "Product")
public class ProductServlet extends HttpServlet {
	private ProductService productService;

	public void init() throws ServletException {
		this.productService = new ProductServiceImpl();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String actionIndicator = req.getParameter("action");
		ActionResult result = new ActionResult("error");
		Validator validator = new Validator(Validator.toSingleParameters(req));

		if (actionIndicator == null)
			actionIndicator = "list";
		if ("read".endsWith(actionIndicator)) {//显示详情
			result = read(req, validator);
		} else if ("view".endsWith(actionIndicator)) {
			result = view(req, resp,validator);
		} else if ("create".endsWith(actionIndicator)) {//新增
			result = create(req, validator);
		} else if ("list".endsWith(actionIndicator)) {//显示所有
			result = list(req, validator);
		} else if ("delete".endsWith(actionIndicator)) {//删除
			result = delete(req, validator);
		}

		if (!validator.hasErrors() && result.isRedirect()) {
			resp.sendRedirect(result.getViewName());
		} else {
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher(result.getViewName()).forward(req, resp);
		}
	}

	public ActionResult read(HttpServletRequest request, Validator validator) {
		Product product = productService.findById(request
				.getParameter("entityId"));
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
		pupulateRequest(request, product);
		return new ActionResult("product-modify.jsp");
	}

	public ActionResult view(HttpServletRequest request,HttpServletResponse response, Validator validator) {
		Product product = productService.findById(request
				.getParameter("entityId"));
		System.out.println(request.getParameter("entityId"));
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
		pupulateRequest(request, product);
		HttpSession session = request.getSession();		
		//最近浏览
		for (Cookie cookie : request.getCookies()) {
			System.out.println(cookie);
		}
		Cookie cookie=(Cookie)session.getAttribute("cookie");//获取cookie
		String recents="";
		boolean flag=false;		
		if(cookie!=null){
			if(cookie.getName().equals("recentViewProducts")){	//获取自定义的cookie
				recents=cookie.getValue();					
				recents =recents+(";"+request.getParameter("entityId"));					
				String str[]=recents.toString().split(";");
				if(str.length>5){//最近浏览5次						
					recents=recents.substring(recents.indexOf(";", 1)+1);	//存ID号					
				}
				cookie.setValue(recents);					
				session.setAttribute("cookie", cookie);
				flag=true;
			}
		}
        if(!flag) {//第一次创建  Cookie
			cookie = new Cookie("recentViewProducts", request.getParameter("entityId"));
			cookie.setMaxAge(60 * 60 * 24 * 7);
			session.setAttribute("cookie", cookie);
		}

//        response.addCookie(cookie);

        ProductService productService=new ProductServiceImpl();
        List<Product> recentsProduct =new ArrayList<Product> ();
		if(null!=cookie.getName() && cookie.getName().equals("recentViewProducts")){			
			if(cookie.getValue().indexOf(";")>0){
				String str[]=cookie.getValue().toString().split(";");
				for(int j=0;j<str.length;j++){	//从数据库中查找商品				
					recentsProduct.add(productService.findById(str[j]));
				}
			}else{
				recentsProduct.add(productService.findById(cookie.getValue()));
			}			
		}
		session.setAttribute("recentViewProducts", recentsProduct);//把商品放入作用域
		return new ActionResult("product-view.jsp");
	}

	public ActionResult create(HttpServletRequest request, Validator validator) {
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
		if (categories.size() > 0)
			request.setAttribute("parentId", categories.get(0).getId());
		return new ActionResult("product-modify.jsp");
	}

	public ActionResult delete(HttpServletRequest request, Validator validator) {
		productService.delete(request.getParameter("entityId"));
		return new ActionResult("Product", true);
	}

	public ActionResult list(HttpServletRequest request, Validator validator) {
		String fileName = request.getParameter("fileName");//商品名
		String category = request.getParameter("category");//商品分类		
		try {
			//点击"X页"时传的中文乱码处理
			if(request.getMethod().equals("GET") && category!=null){
				category = new String (category.getBytes("iso-8859-1"),"utf-8");
			}
			if(request.getMethod().equals("GET") && fileName!=null){
				fileName = new String (fileName.getBytes("iso-8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		String condition = "";
		if (!Validator.isEmpty(fileName))//带商品名的模糊查询			
			condition =  "  EASYBUY_PRODUCT.ep_name like '%" + fileName+"%'";	
		if (!Validator.isEmpty(category)){//带分类的查询				
			if(!condition.equals("")){
				condition = condition+ "and (epc_id=(select EPC_ID  from EASYBUY_PRODUCT_CATEGORY where EPC_NAME='"+category+"') or EPC_CHILD_ID=(select EPC_ID  from EASYBUY_PRODUCT_CATEGORY where EPC_NAME='"+category+"') )";	
			}else{
				condition = " (epc_id=(select EPC_ID  from EASYBUY_PRODUCT_CATEGORY where EPC_NAME='"+category+"') or EPC_CHILD_ID=(select EPC_ID  from EASYBUY_PRODUCT_CATEGORY where EPC_NAME='"+category+"') )";	
			}				
		}
		
		Product product=new Product();
		product.setFileName(fileName);//分类名
		product.setName(category);//商品名
		
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		String categoryId = request.getParameter("categoryId");		
		long rowCount = productService.getProductRowCount(categoryId,condition);//获取共有多少条记录		
		String rowPerPage = request.getParameter("rowPerPage");
		Pager pager = new Pager(rowCount, pageNo);
		if (!Validator.isEmpty(rowPerPage))
			pager = new Pager(rowCount, Integer.parseInt(rowPerPage), pageNo);
		
		//获取商品
		List<Product> products = productService.getProductsByCategory(categoryId, pager,condition);		
		//获取所有商品分类
		List<ProductCategory> categories = productService.getProductCategories(null);		
		request.setAttribute("categories", categories);
		request.setAttribute("products", products);
		request.setAttribute("product", product);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		return new ActionResult("product.jsp");
	}

	private void pupulateRequest(HttpServletRequest request, Product product) {
		request.setAttribute("entityId", Long.toString(product.getId()));
		request.setAttribute("name", product.getName());
		request.setAttribute("description", product.getDescription());
		request.setAttribute("price", Float.toString(product.getPrice()));
		request.setAttribute("stock", Long.toString(product.getStock()));
		
		Long parentId = product.getChildCategoryId();
		if (parentId == null)
			parentId = product.getCategoryId();
		request.setAttribute("parentId", Long.toString(parentId));
		request.setAttribute("fileName", product.getFileName());
	}
}
