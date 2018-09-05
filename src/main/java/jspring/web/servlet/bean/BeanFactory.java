package jspring.web.servlet.bean;

/**
 * bean factory interface
 * @author WILLS
 *
 */
public interface BeanFactory {
	
	
	/**
	 * get bean by name
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Object getBean(String name) throws Exception;
	
}
