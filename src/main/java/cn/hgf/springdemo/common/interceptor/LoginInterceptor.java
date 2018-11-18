package cn.hgf.springdemo.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @Author: FanYing
 * @Date: 2018-05-01 15:51
 * @Desciption: 拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {


   /* @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") == null){
            //用户未登录

            request.setAttribute("message","还未登录");

            request.getRequestDispatcher("/login.html").forward(request,response);
            return  false;
        }else{
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }*/
}
