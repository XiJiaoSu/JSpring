package jspring.web.servlet.context;

import java.util.Date;

public abstract class ConfigurableApplicationContext implements ApplicationContext {
	
	private String applicationId;
	private String applicationName;
	private Date startUpDate;
	
	public String getId() {
		return applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public long getStartupDate() {
		return startUpDate.getTime();
	}

	public ApplicationContext getParent() {
		
		return null;
	}
	
	protected abstract void setId(String id);

	
	

	public void load() throws Exception {
		onCreate();
		
	}

	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onStart() throws Exception {
	
		
	}

	public void onStop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onRefresh() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onDestory() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
