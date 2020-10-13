package cn.htzb.easybuy.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.entity.UniqueConstraintException;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/manage/News","/News"},name = "News")
public class NewsServlet extends HttpServlet {
	private FacilityService facilityService;
	private ProductService productService;

	public void init() throws ServletException {
		this.facilityService = new FacilityServiceImpl();
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
		} else if ("view".endsWith(actionIndicator)) {//显示分类
			result = view(req, validator);
		} else if ("create".endsWith(actionIndicator)) {//添加
			result = create(req, validator);
		} else if ("list".endsWith(actionIndicator)) {//显示所有
			result = list(req, validator);
		} else if ("delete".endsWith(actionIndicator)) {//删除
			result = delete(req, validator);
		} else if ("save".endsWith(actionIndicator)) {//保存
			boolean isEdit = true;
			String editIndicator = req.getParameter("entityId");
			if (Validator.isEmpty(editIndicator))
				isEdit = false;
			result = save(req, validator, isEdit);
		}

		if (!validator.hasErrors() && result.isRedirect()) {
			resp.sendRedirect(result.getViewName());
		} else {
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher(result.getViewName()).forward(req, resp);
		}
	}

	public ActionResult read(HttpServletRequest request, Validator validator) {
		News news = facilityService.findNewsById(request
				.getParameter("entityId"));
		pupulateRequest(request, news);
		return new ActionResult("news-modify.jsp");
	}

	public ActionResult view(HttpServletRequest request, Validator validator) {
		News news = facilityService.findNewsById(request
				.getParameter("entityId"));
		pupulateRequest(request, news);
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
		return new ActionResult("news-view.jsp");
	}

	public ActionResult save(HttpServletRequest request, Validator validator,
			boolean isEdit) {
		checkInputErrors(request, validator);
		saveToDatabase(request, validator, isEdit);
		if (!validator.hasErrors())
			return new ActionResult("News", true);
		else {
			pupulateRequest(request);
			return new ActionResult("news-modify.jsp");
		}
	}

	public ActionResult create(HttpServletRequest req, Validator validator) {
		return new ActionResult("news-modify.jsp");
	}

	public ActionResult delete(HttpServletRequest request, Validator validator) {
		facilityService.deleteNews(request.getParameter("entityId"));
		return new ActionResult("News", true);
	}

	public ActionResult list(HttpServletRequest request, Validator validator) {
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		long rowCount = facilityService.getNewsRowCount();
		Pager pager = new Pager(rowCount, pageNo);
		List<News> allNews = facilityService.getAllNews(pager);
		request.setAttribute("allNews", allNews);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		return new ActionResult("news.jsp");
	}

	private void pupulateRequest(HttpServletRequest request, News news) {
		request.setAttribute("entityId", Long.toString(news.getId()));
		request.setAttribute("title", news.getTitle());
		request.setAttribute("content", news.getContent());
	}

	private void pupulateRequest(HttpServletRequest request) {
		request.setAttribute("entityId", request.getParameter("entityId"));
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("content", request.getParameter("content"));
	}

	private void saveToDatabase(HttpServletRequest request,
			Validator validator, boolean isEdit) {
		if (!validator.hasErrors()) {
			try {
				News news;
				if (!isEdit) {
					news = new News();
					news.setCreateTime(new Date());
					populateEntity(request, news);
					facilityService.saveNews(news);
				} else {
					news = facilityService.findNewsById(request
							.getParameter("entityId"));
					news.setCreateTime(new Date());
					populateEntity(request, news);
					facilityService.updateNews(news);
				}
			} catch (UniqueConstraintException e) {
				validator.addError("title", "标题已经存在");
			}
		}
	}

	private void checkInputErrors(HttpServletRequest request,
			Validator validator) {
		validator.checkRequiredError(new String[] { "title", "content" });
	}

	private void populateEntity(HttpServletRequest request, News news) {
		news.setTitle(request.getParameter("title"));
		news.setContent(request.getParameter("content"));
	}
}
