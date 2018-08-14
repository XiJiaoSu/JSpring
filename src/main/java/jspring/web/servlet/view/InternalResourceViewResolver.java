package jspring.web.servlet.view;

public class InternalResourceViewResolver implements ViewResolver {
	
	
	private String prefix;
	private String suffix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public View resolveViewName(String viewName) {
		View view = new JspView(prefix + viewName + suffix);
		return view;
	}
}
