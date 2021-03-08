package dev.lansdon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.lansdon.delegates.AuthorDelegate;
import dev.lansdon.delegates.EditorDelegate;
import dev.lansdon.delegates.LoginDelegate;
import dev.lansdon.delegates.FrontControllerDelegate;

public class RequestHandler {
	private Map<String, FrontControllerDelegate> delegateMap;
	
	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();
		
		delegateMap.put("user", new LoginDelegate());
		delegateMap.put("author", new AuthorDelegate());
		delegateMap.put("editor", new EditorDelegate());
	}
	
	public FrontControllerDelegate handle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if ("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {};
		}
		
		StringBuilder uriString = new StringBuilder(req.getRequestURI());
		uriString.replace(0, req.getContextPath().length()+1, "");
		
		if (uriString.indexOf("/") != -1) {
			req.setAttribute("path", uriString.substring(uriString.indexOf("/")+1));
			uriString.replace(uriString.indexOf("/"), uriString.length(), "");
		}
		
		return delegateMap.get(uriString.toString());
	}
}
