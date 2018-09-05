package jspring.web.servlet.handler;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jspring.web.servlet.RequestMappingInfo;

public class RequestMappingInfoHandlerMapping extends AbstractHandlerMethodMapping<RequestMappingInfo>{

	/**
	 * to be done
	 */
	@Override
	protected RequestMappingInfo getMatchingMapping(RequestMappingInfo mapping, HttpServletRequest request) {
		return RequestMappingInfo.getMatchingCondition(request);
	}

	@Override
	protected Set<String> getMappingPathPatterns(RequestMappingInfo mapping) {
		return mapping.getPatterns();
	}

}
