package cn.htzb.easybuy.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.htzb.easybuy.biz.FacilityService;
import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.biz.impl.FacilityServiceImpl;
import cn.htzb.easybuy.biz.impl.ProductServiceImpl;
import cn.htzb.easybuy.entity.News;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/Home"},name = "Home")
public class HomeServlet extends HttpServlet {
	protected Map<String, ActionResult> viewMapping = new HashMap<String, ActionResult>();
	private ProductService productService;
	private FacilityService facilityService;

	public void init() throws ServletException {
		productService = new ProductServiceImpl();
		facilityService = new FacilityServiceImpl();
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
		if ("list".endsWith(actionIndicator)) {//显示所有商品
			result = list(req);
		} else if ("product_list".endsWith(actionIndicator)) {//显示某类商品
			result = productList(req);
		}
		toView(req, resp, result);
	}

	private String productList(HttpServletRequest request) {
		prepareCategories(request);
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		String categoryId = request.getParameter("categoryId");
		ProductCategory category = productService.findCategoryById(categoryId);
		long rowCount = productService.getProductRowCount(categoryId);
		String rowPerPage = request.getParameter("rowPerPage");
		Pager pager = new Pager(rowCount, pageNo);
		if (!Validator.isEmpty(rowPerPage))
			pager = new Pager(rowCount, Integer.parseInt(rowPerPage), pageNo);
		List<Product> products = productService.getProductsByCategory(
				categoryId, pager);
		request.setAttribute("products", products);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("category", category);
		return "productListSuccess";
	}

	private String list(HttpServletRequest request) {
		prepareCategories(request);
		prepareNews(request);
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		String categoryId = request.getParameter("categoryId");
		long rowCount = productService.getProductRowCount(categoryId);
		String rowPerPage = request.getParameter("rowPerPage");
		Pager pager = new Pager(rowCount,8, pageNo);
		if (!Validator.isEmpty(rowPerPage))
			pager = new Pager(rowCount, Integer.parseInt(rowPerPage), pageNo);
		List<Product> products = productService.getProductsByCategory(
				categoryId, pager);
		request.setAttribute("products", products);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		return "listSuccess";
	}

	private void prepareCategories(HttpServletRequest request) {
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
	}

	private void prepareNews(HttpServletRequest request) {
		List<News> allNews = facilityService.getAllNews(new Pager(50,10,1));
		request.setAttribute("allNews", allNews);
	}

	protected void createViewMapping() {
		this.addMapping("listSuccess", "index.jsp");
		this.addMapping("productListSuccess", "product-list.jsp");
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
