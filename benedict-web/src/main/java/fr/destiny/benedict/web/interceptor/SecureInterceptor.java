package fr.destiny.benedict.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecureInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        if ("http".equals(request.getHeader("x-forwarded-proto"))) {
            response.sendRedirect(request.getRequestURL().toString().replace("http://", "https://"));
            return false;
        }
        return true;
    }
}
