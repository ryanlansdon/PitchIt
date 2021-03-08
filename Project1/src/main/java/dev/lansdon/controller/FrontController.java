package dev.lansdon.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import dev.lansdon.delegates.FrontControllerDelegate;

public class FrontController extends DefaultServlet {
	private RequestHandler rh = new RequestHandler();
	
	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		FrontControllerDelegate fcd = rh.handle(req, res);
		
		if (req.getRequestURI().substring(req.getContextPath().length()).startsWith("/static")) {
			super.doGet(req, res);
		} else {
			if (fcd != null) {
				fcd.process(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
}
