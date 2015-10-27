package net.bafeimao.umbrella.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PerformanceMonitorInterceptor extends HandlerInterceptorAdapter {
	private NamedThreadLocal<Long> startTime = new NamedThreadLocal<Long>("ThreadLocal-StartTime");

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		startTime.set(System.currentTimeMillis());
		return true;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long elapsed = System.currentTimeMillis() - startTime.get();
		if (elapsed > 500) {
			System.out.println(String.format("[SLOW] %s consumes %d millis", request.getRequestURI(), elapsed));
		}
	}
}