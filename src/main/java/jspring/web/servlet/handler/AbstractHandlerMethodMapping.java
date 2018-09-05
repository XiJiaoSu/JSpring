package jspring.web.servlet.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractHandlerMethodMapping<T> extends AbstractHandlerMapping {

	private final MappingRegistry mappingRegistry = new MappingRegistry();
	
	@Override
	protected Object getHandlerInternal(HttpServletRequest request) throws Exception {

		String lookupPath = "";// to be done
		HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request);

		return handlerMethod;
	}

	/**
	 * to be done
	 * @param lookupPath
	 * @param request
	 * @return
	 */
	protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) {
		List<Match> matches = new ArrayList<Match>();
//		List<T> directPathMatches = this.mappingRegistry.getMappingsByUrl(lookupPath);
		List<T> directPathMatches = new ArrayList<T>();
		addMatchingMappings(directPathMatches,matches,request);
		Match bestMatch = matches.get(0);
		return bestMatch.handlerMethod;
	}

	private void addMatchingMappings(Collection<T> mappings, List<Match> matches, HttpServletRequest request) {
//		for (T mapping : mappings) {
//			T match = getMatchingMapping(mapping, request);
//			if (match != null) {
//				matches.add(new Match(match, this.mappingRegistry.getMappings().get(mapping)));
//			}
//		}
		T match = getMatchingMapping(null, request);
		if (match != null) {
			matches.add(new Match(match, this.mappingRegistry.getMappings().get(match)));
		}
	}

	/**
	 * Check if a mapping matches the current request and return a (potentially
	 * new) mapping with conditions relevant to the current request.
	 * 
	 * @param mapping
	 *            the mapping to get a match for
	 * @param request
	 *            the current HTTP servlet request
	 * @return the match, or {@code null} if the mapping doesn't match
	 */
	protected abstract T getMatchingMapping(T mapping, HttpServletRequest request);
	
	
	/**
	 * Extract and return the URL paths contained in a mapping.
	 */
	protected abstract Set<String> getMappingPathPatterns(T mapping);
	
	/**
	 * Create the HandlerMethod instance.
	 * @param handler either a bean name or an actual handler instance
	 * @param method the target method
	 * @return the created HandlerMethod
	 */
	protected HandlerMethod createHandlerMethod(Object handler, Method method) {
		HandlerMethod handlerMethod=new HandlerMethod(handler, method);
//		if (handler instanceof String) {
//			String beanName = (String) handler;
//			handlerMethod = new HandlerMethod(beanName,
//					obtainApplicationContext().getAutowireCapableBeanFactory(), method);
//		}
//		else {
//			handlerMethod = new HandlerMethod(handler, method);
//		}
		return handlerMethod;
	}
	
	class MappingRegistry {

//		private final Map<T, MappingRegistration<T>> registry = new HashMap<>();
		// 保存 key-value : T - HandlerMethod
		private final Map<T, HandlerMethod> mappingLookup = new LinkedHashMap<>();
		//添加锁
		private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		//url mapping
		private final Map<String, T> urlLookup = new LinkedHashMap<String,T>();
		
		/**
		 * Return all mappings and handler methods. Not thread-safe.
		 * 
		 * @see #acquireReadLock()
		 */
		public Map<T, HandlerMethod> getMappings() {
			return this.mappingLookup;
		}
		
		/**
		 * 注册映射关系，K-HandlerMethod,String-K
		 * @param mapping
		 * @param handler
		 * @param method
		 */
		public void register(T mapping, Object handler, Method method) {
			this.readWriteLock.writeLock().lock();
			try{
			HandlerMethod handlerMethod=createHandlerMethod(handler,method);
			this.mappingLookup.put(mapping, handlerMethod);
			List<String> directUrls = getDirectUrls(mapping);
			for(String url:directUrls){
				this.urlLookup.put(url,mapping);
			}
			}finally{
				this.readWriteLock.writeLock().unlock();
			}
		}

		private List<String> getDirectUrls(T mapping) {
			List<String> urls = new ArrayList<String>(1);
			for (String path : getMappingPathPatterns(mapping)) {
				urls.add(path);
			}
			return urls;
		}
		
	}

	
	private static class MappingRegistration<T> {

		private final T mapping;

		private final HandlerMethod handlerMethod;

		private final List<String> directUrls;

		private final String mappingName;

		public MappingRegistration(T mapping, HandlerMethod handlerMethod,
				List<String> directUrls,String mappingName) {

			this.mapping = mapping;
			this.handlerMethod = handlerMethod;
			this.directUrls = (directUrls != null ? directUrls : Collections.emptyList());
			this.mappingName = mappingName;
		}

		public T getMapping() {
			return this.mapping;
		}

		public HandlerMethod getHandlerMethod() {
			return this.handlerMethod;
		}

		public List<String> getDirectUrls() {
			return this.directUrls;
		}

		
		public String getMappingName() {
			return this.mappingName;
		}
	}
	/**
	 * A thin wrapper around a matched HandlerMethod and its mapping, for the
	 * purpose of comparing the best match with a comparator in the context of
	 * the current request.
	 */
	private class Match {

		private final T mapping;

		private final HandlerMethod handlerMethod;

		public Match(T mapping, HandlerMethod handlerMethod) {
			this.mapping = mapping;
			this.handlerMethod = handlerMethod;
		}

		@Override
		public String toString() {
			return this.mapping.toString();
		}
	}
}
