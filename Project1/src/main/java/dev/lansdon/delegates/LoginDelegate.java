package dev.lansdon.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.lansdon.exceptions.NonUniqueUsernameException;
import dev.lansdon.models.Role;
import dev.lansdon.models.User;
import dev.lansdon.services.*;
import org.apache.log4j.Logger;

/*
 * Endpoints:
 *  /user/login - (POST) log user in
 *  			- (DELETE) log out user
 *  /user - (POST) register new user
 *  /user/:id - (GET) get user by id
 *  		  - (PUT) update user
 *  		  - (DELETE) deletes user
 */

public class LoginDelegate implements FrontControllerDelegate {
	private UserService uServ = new UserServiceImpl();
	private ObjectMapper om = new ObjectMapper();
	Logger log = Logger.getLogger(LoginDelegate.class);
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		
		if (path == null || path.equals("")) {
			if ("POST".equals(req.getMethod())) {
				// register user
				Map m = om.readValue(req.getInputStream(), Map.class);
				User u = new User();
				u.setPoints((Integer) m.get("points"));
				u.setUsername((String) m.get("username"));
				u.setPassword((String) m.get("password"));
				u.setFirstName((String) m.get("firstName"));
				u.setLastName((String) m.get("lastName"));
				Role r = new Role();
				r.setId(4);
				r.setName("Author");
				u.setRole(r);
				log.debug(u);
				//User u = om.readValue(req.getInputStream(), User.class);
				//log.debug(u);
				try {
					u = uServ.addUser(u);
				} catch (NonUniqueUsernameException e) {
					res.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists");
				}
				if (u.getId() == 0) {
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				} else {
					req.getSession().setAttribute("user", u);
					res.getWriter().write(om.writeValueAsString(u));
					res.setStatus(HttpServletResponse.SC_CREATED);
				}
			} else if ("GET".equals(req.getMethod())) {
				User u = (User) req.getSession().getAttribute("user");
				res.getWriter().write(om.writeValueAsString(u));
			} else {
				res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		} else if (path.contains("login")) {
			if ("POST".equals(req.getMethod()))
				logIn(req, res);
			else if ("DELETE".equals(req.getMethod()))
				req.getSession().invalidate();
			else
				res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else {
			res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	private void logIn(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String username = req.getParameter("user");
		String password = req.getParameter("pass");

		User u = uServ.getUserByUsername(username);

		if (u != null) {
			if (u.getPassword().equals(password)) {
				req.getSession().setAttribute("user", u);
				res.getWriter().write(om.writeValueAsString(u));
			} else {
				res.sendError(400, "Incorrect password");
			}
		} else {
			res.sendError(404, "No user found with that username");
		}
	}
}
