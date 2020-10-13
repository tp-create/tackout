package cn.htzb.easybuy.util;

public class ActionResult {
	private String viewName;//中转的页面
	private boolean redirect;//是否带有数据

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public ActionResult(String viewName, boolean redirect) {
		super();
		this.viewName = viewName;
		this.redirect = redirect;
	}

	public ActionResult() {
		this.viewName = "success";
		this.redirect = false;
	}

	public ActionResult(String viewName) {
		this.viewName = viewName;
	}
}
