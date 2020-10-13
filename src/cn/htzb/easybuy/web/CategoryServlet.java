package cn.htzb.easybuy.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.biz.impl.ProductServiceImpl;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;

@WebServlet(urlPatterns = { "/manage/Category" }, name = "Category")
public class CategoryServlet extends HttpServlet {
	private ProductService productService;

	public void init() throws ServletException {
		productService = new ProductServiceImpl();
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
		if ("read".endsWith(actionIndicator)) {//显示某一条
			result = read(req, validator);
		} else if ("list".endsWith(actionIndicator)) {//显示所有
			result = list(req, validator);
		} else if ("create".endsWith(actionIndicator)) {//创建
			result = create(req, validator);
		} else if ("delete".endsWith(actionIndicator)) {//删除一条
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
		ProductCategory category = productService.findCategoryById(request
				.getParameter("entityId"));
		pupulateRequest(request, category);
		List<ProductCategory> categories = productService.getRootCategories();
		request.setAttribute("categories", categories);
		return new ActionResult("productClass-modify.jsp");
	}

	public ActionResult save(HttpServletRequest request, Validator validator,
			boolean isEdit) {
		checkInputErrors(request, validator);
		saveToDatabase(request, validator, isEdit);
		return new ActionResult("Category", true);
	}

	public ActionResult create(HttpServletRequest request, Validator validator) {
		List<ProductCategory> categories = productService.getRootCategories();
		request.setAttribute("categories", categories);
		request.setAttribute("parentId", 0);
		return new ActionResult("productClass-modify.jsp");
	}

	public ActionResult delete(HttpServletRequest request, Validator validator) {
		productService.deleteCategory(request.getParameter("entityId"));
		return new ActionResult("Category", true);
	}

	public ActionResult list(HttpServletRequest request, Validator validator) {
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		categories = this.changeToMenu(categories);		
		request.setAttribute("categories", categories);
		return new ActionResult("productClass.jsp");
	}

	public List<ProductCategory> changeToMenu(List<ProductCategory> input) {
		List<ProductCategory> output = new ArrayList<ProductCategory>();
		ProductCategory parent = new ProductCategory();
		ProductCategory now = null;
		int pos = 0;
		for (int i = 0; i < input.size(); i++) {
			now = input.get(i);
			if (now.getId().equals(now.getParentId())) {//父类
				if (now.getId().equals(parent.getId())) {//已经占位
					output.set(pos, now);
				} else {//尚未占位
					parent.setId(now.getId());
					output.add(now);
				}
			} else {//子类
				if (!now.getParentId().equals(parent.getId())) {//尚未占位
					pos = output.size();
					parent.setId(now.getParentId());
					output.add(parent);
				}
				output.add(now);//添加自己
			}
		}
		return output;
	}

	private void saveToDatabase(HttpServletRequest request,
			Validator validator, boolean isEdit) {
		if (!validator.hasErrors()) {
			ProductCategory productCategory;
			if (!isEdit) {
				productCategory = new ProductCategory();
				populateEntity(request, productCategory);
				productCategory.setParentId(Long.parseLong(request
						.getParameter("parentId")));
				productService.saveCategory(productCategory);
			} else {
				productCategory = productService.findCategoryById(request
						.getParameter("entityId"));
				Long parentId = Long
						.parseLong(request.getParameter("parentId"));
				populateEntity(request, productCategory);

				if (parentId == 0) {
					if (productCategory.getId().equals(
							productCategory.getParentId())) {
						// 说明是一级分类，父分类不能修改，只能改名字
						productService.updateCategoryName(productCategory);
					} else {
						// 二级分类修改为一级分类了,需要额外更新：
						// Product原先属于该二级分类的，全部更新一级为它，二级为空
						productCategory.setParentId(productCategory.getId());
						productService.updateCategory(productCategory,
								"Level2To1");
					}
				} else {
					if (!parentId.equals(productCategory.getParentId())) {
						// 二级分类修改了父分类,需要额外更新：
						// Product原先属于该二级分类的，全部更新一级为新的父分类
						productCategory.setParentId(parentId);
						productService.updateCategory(productCategory,
								"ModifyParent");
					} else {
						// 二级分类修改了名字
						productService.updateCategoryName(productCategory);
					}
				}
			}
		}
	}

	private void pupulateRequest(HttpServletRequest request,
			ProductCategory productCategory) {
		request.setAttribute("entityId", Long.toString(productCategory.getId()));
		request.setAttribute("name", productCategory.getName());
		request.setAttribute(
				"parentId",
				(productCategory.getParentId().equals(productCategory.getId())) ? 0
						: productCategory.getParentId());
	}

	private void checkInputErrors(HttpServletRequest request,
			Validator validator) {
		validator.checkRequiredError(new String[] { "name" });
	}

	private void populateEntity(HttpServletRequest request,
			ProductCategory productCategory) {
		productCategory.setName(request.getParameter("name"));
	}
}
