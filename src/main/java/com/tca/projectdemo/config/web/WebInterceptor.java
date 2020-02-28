package com.tca.projectdemo.config.web;

import com.tca.cache.CacheHelper;
import com.tca.projectdemo.constant.Constants;
import com.tca.projectdemo.exception.ProjectException;
import com.tca.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class WebInterceptor implements HandlerInterceptor{

	@Autowired
	private CacheHelper cacheHelper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从请求头或请求参数中获取token
		String token = ValidateUtils.isEmpty(request.getHeader("token"))? request.getParameter("token"):
				request.getHeader("token");
		// 用户未登陆
		if (ValidateUtils.isEmpty(token)) {
			throw new ProjectException("S4000", "用户未登录");
		}
		// 用户token失效
		if (ValidateUtils.isEmpty(cacheHelper.get(token))) {
			throw new ProjectException("S4001", "用户token失效,请重新登录");
		}
        cacheHelper.set(token, cacheHelper.get(token), Constants.TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
