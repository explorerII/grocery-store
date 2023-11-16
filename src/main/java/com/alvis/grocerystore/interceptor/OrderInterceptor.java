package com.alvis.grocerystore.interceptor;

import com.alvis.grocerystore.model.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * intercept request for checking if current login user is same as request user
 */

@Component
public class OrderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final Map<String, String> pathVariables = (Map<String, String>)request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        final Integer userId = Integer.valueOf(pathVariables.get("userId"));
        final Integer currentUserId = ((MyUserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUserId();

        if (userId.equals(currentUserId)) {
            return true;
        } else {
            response.setStatus(400);
            return false;
        }
    }
}