package com.hr.config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class PreMatchingServerRequestFilter implements ContainerRequestFilter{
	
//	@Inject
//	Logger logger;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
//		logger.log(Level.INFO, "Original Method of Request "+requestContext.getMethod());
		//x-http-method-override == Delete
		String httpMethod = requestContext.getHeaderString("X-Http-Method-Override");
		if(httpMethod != null && !httpMethod.isEmpty()) {
			requestContext.setMethod(httpMethod);
//			logger.log(Level.INFO, "Method has been overriden to "+httpMethod);
		}
		// TODO Auto-generated method stub
		
	}
	

}
