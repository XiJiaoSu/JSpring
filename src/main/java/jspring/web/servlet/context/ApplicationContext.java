package jspring.web.servlet.context;

public interface ApplicationContext extends LifeCycle{
	
	void load() throws Exception;
	
	/**
	 * 获取唯一的id
	 * @return
	 */
	String getId();

	/**
	 * 当前容器的名称
	 * @return
	 */
	String getApplicationName();
	

	/**
	 * 获取启动的时间
	 * @return
	 */
	long getStartupDate();


	/**
	 * 父级容器，待扩展
	 * @return
	 */
	ApplicationContext getParent();
	
}	
