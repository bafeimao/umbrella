package net.bafeimao.umbrella.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityCheckingHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login?returl=" + request.getPathInfo());
			return false;
		}

		return super.preHandle(request, response, handler);
	}
}
