package jspring.web.servlet.context.support;

import jspring.web.servlet.context.ConfigurableWebApplicationContext;

/**
 * abstract web application context
 * @author WILLS 
 *
 */
public abstract class AbstractWebApplicationContext extends AbstractApplicationContext 
		implements ConfigurableWebApplicationContext{

  
  //配置问价路径
  private String[] configLocations;
  
  
  /**
   * 配置文件的路径
   * @param configLocations
   */
  public void setConfigLocations(String... locations) {
	  if (locations != null) {
			this.configLocations = new String[locations.length];
			for (int i = 0; i < locations.length; i++) {
				this.configLocations[i] = resolvePath(locations[i]).trim();
			}
		}
		else {
			this.configLocations = null;
		}
  }

  protected String[] getConfigLocations() {
		return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
	}
  
  protected abstract String[] getDefaultConfigLocations();

/**
   * 解析路径
   * @param path
   * @return
   */
  protected String resolvePath(String path) {
		return path.split(":")[1];
	}
  
}
