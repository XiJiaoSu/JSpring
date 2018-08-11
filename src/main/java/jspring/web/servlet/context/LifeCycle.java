package jspring.web.servlet.context;

public interface LifeCycle {
	/**
	 * 创建，用于加载配置文件
	 * @throws Exception
	 */
	public void onCreate()throws Exception;
	
	/**
	 * 容器正式启动
	 * @throws Exception
	 */
	public void onStart()throws Exception;
	
	/**
	 * 容器停止
	 * @throws Exception
	 */
	public void onStop()throws Exception;
	
	/**
	 * 重新刷新
	 * @throws Exception
	 */
	public void onRefresh()throws Exception;
	
	/**
	 * 容器正式销毁
	 * @throws Exception
	 */
	public void onDestory()throws Exception;
	
}
