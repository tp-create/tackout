package cn.htzb.easybuy.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.biz.impl.ProductServiceImpl;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/manage/ProductUpload"},name = "ProductUpload")
public class ProductUploadServlet extends HttpServlet {
	private static final String TMP_DIR_PATH = "c:\\tmp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH = "/files";
	private File destinationDir;
	private ProductService productService;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if (!tmpDir.exists()) {//如果目录不存在，则新建目录
			tmpDir.mkdirs();
		}		
		String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		destinationDir = new File(realPath);
		destinationDir.mkdirs();
		if (!destinationDir.isDirectory()) {
			throw new ServletException(DESTINATION_DIR_PATH
					+ " is not a directory");
		}
		productService = new ProductServiceImpl();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
		fileItemFactory.setRepository(tmpDir);
		String fileName = null;
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		uploadHandler.setHeaderEncoding("utf-8");
		try {
			List items = uploadHandler.parseRequest(req);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					params.put(item.getFieldName(), item.getString("utf-8"));
				}else {
					if (item.getSize() > 0) {//修改图片	
						fileName=item.getName().substring(
								item.getName().lastIndexOf("."));
						File file = new File(destinationDir,fileName );
						Validator validator = new Validator(params);
						checkInputErrors(validator);						
						Long id=saveToDatabase(params, fileName, validator);//保存至数据库，返回ID
						fileName = Long.toString(id)
								+ item.getName().substring(
										item.getName().lastIndexOf("."));
						file = new File(destinationDir, fileName);//图片名与商品ID一致
						item.write(file);//保存商品图片
						if (validator.hasErrors()) {
							List<ProductCategory> categories = productService
									.getProductCategories(null);
							req.setAttribute("categories", categories);
							req.setAttribute("errors", validator.getErrors());
							pupulateRequest(req,params);
							req.getRequestDispatcher("product-modify.jsp").forward(req, resp);
						} else
							resp.sendRedirect("Product");
					}else{//不修改图片	
						Validator validator = new Validator(params);
						checkInputErrors(validator);	
						Long id=saveToDatabase(params, "", validator);						
						resp.sendRedirect("Product");
					}
				}
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}

	//向数据库保存
	private Long saveToDatabase(Map<String, String> params, String fileName,
			 Validator validator) {
		if (!validator.hasErrors()) {
			Product product;
			if (!Validator.isEmpty(params.get("entityId"))) {//更新商品
				product = productService.findById(params.get("entityId"));
				populateEntity(product, params);
				if (fileName != null)
					product.setFileName(fileName);
				productService.update(product);
				return product.getId();//返回商品ID命名商品图片
			} else {//新增商品
				product = new Product();
				populateEntity(product, params);
				if (fileName != null)
					product.setFileName(fileName);
				Long id=productService.save(product);
				return id;
			}			
		}
		return null;
	}

	private void checkInputErrors(Validator validator) {
		validator.checkRequiredError(new String[] { "name", "price", "stock" });
		if (!validator.hasErrors()) {
			validator.checkFloatFormatError("price", true);
			validator.checkLongFormatError("stock", true);
		}
	}

	private void populateEntity(Product product, Map<String, String> params) {
		product.setName(params.get("name"));
		product.setDescription(params.get("description"));
		product.setPrice(Validator.convertToFloat(params.get("price")));
		product.setStock(Validator.convertToLong(params.get("stock")));
		String categoryId = params.get("parentId");
		ProductCategory category = productService.findCategoryById(categoryId);
		if (category.getId().equals(category.getParentId())) {
			product.setCategoryId(category.getId());
			product.setChildCategoryId(null);
		} else {
			product.setCategoryId(category.getParentId());
			product.setChildCategoryId(category.getId());
		}
	}
	
	private void pupulateRequest(HttpServletRequest request,Map<String, String> params) {
		request.setAttribute("entityId", params.get("entityId"));
		request.setAttribute("name", params.get("name"));
		request.setAttribute("description", params.get("description"));
		request.setAttribute("price", params.get("price"));
		request.setAttribute("stock", params.get("stock"));
		request.setAttribute("parentId", params.get("parentId"));
		request.setAttribute("fileName", params.get("fileName"));
	}

}
