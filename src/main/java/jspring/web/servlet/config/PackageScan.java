package jspring.web.servlet.config;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * package scan
 */
public class PackageScan {

    private Set<String> basePackages;

    public PackageScan(){
        this.basePackages=new LinkedHashSet<String>();
    }
    public PackageScan(String [] basepackages){
    	this.basePackages=new LinkedHashSet<String>();
        for(String basepackage:basepackages){
        	this.basePackages.add(basepackage);
        }
    }
    /**
     * 获取默认包扫描根路径
     * @return
     */
    private String getInitPath(){
        return getInitPath("");
    }

    /**
     * 根据指定包，获取需要扫面的根路径
     * @param pkname
     * @return
     */
    private String getInitPath(String pkname){
        String rootpath="./";
        
        if(pkname!=null||!"".equals(pkname)){
        	String resource=pkname.indexOf(".")!=-1?pkname.replaceAll("\\.","/"):pkname;
            rootpath=Thread.currentThread().getContextClassLoader().getResource(resource).getFile();
        }
        return rootpath;
    }

    /**
     * 查找当前路径下的全部注解类
     * @param path
     * @return
     */
    private Set<Class<?>> findAllAnnotationClassByPath(String path,String basepackage){
        Set<Class<?>> clazzs=new LinkedHashSet<Class<?>>();
        Queue<String> dirs=new LinkedList<String>();
        dirs.add(path);
        File file=null;
        while (!dirs.isEmpty()){
            file=new File(dirs.poll());
            if (file.isFile()&&file.getName().endsWith(".class")){
                try {
                    String absolutePath=file.getAbsolutePath();
                    String className=absolutePath.substring(path.length()-1,absolutePath.length()-6).replaceAll(Matcher.quoteReplacement(File.separator),"\\.");
                    Class<?> clazz=Class.forName(basepackage+className);
                    if (clazz.getDeclaredAnnotations().length>0) {
                        clazzs.add(clazz);
                    }
                } catch (Exception e) {
                    //当前class找不到异常
                    e.printStackTrace();
                }
            }else if(file.isDirectory()){
                for (File item:file.listFiles()) {
                    dirs.add(item.getAbsolutePath());
                }
            }
        }
        return clazzs;
    }

    /**
     * 查找指定包路径下的所有的注解class，可以去重
     * @return
     */
    public Set<Class<?>> loadAllAnnotationClass(){
    	Set<Class<?>> clazzs=new LinkedHashSet<Class<?>>();
        if (basePackages==null||basePackages.size()==0){//没有指定包路径，默认进行当前工程下全部查找
            clazzs.addAll(findAllAnnotationClassByPath(getInitPath(),""));
        }else{
            for (String pkname:basePackages) {//根据指定的多个包路径进行查找
            	clazzs.addAll(findAllAnnotationClassByPath(getInitPath(pkname),pkname));
            }
        }
        return clazzs;
    }
    
    public void  addPackage(String packages){
    	basePackages.add(packages);
    }
    
    public void  addPackages(Set<String> packages){
    	basePackages.addAll(packages);
    }
    
    public void empty(){
    	basePackages.clear();
    }
    
    public boolean isEmpty(){
    	return basePackages.isEmpty();
    }

    
    
    public static void main(String[] args) {
    	PackageScan pkScan=new PackageScan();
		Set<Class<?>> clazzs = pkScan.loadAllAnnotationClass();
		for(Class<?> entry:clazzs){
			System.out.println(entry.getName());
		}
		System.out.println("---------------------");
		pkScan.addPackage("jspring.home");
		clazzs.clear();
		clazzs = pkScan.loadAllAnnotationClass();
		for(Class<?> entry:clazzs){
			System.out.println(entry.getName());
		}
	}
    
}
