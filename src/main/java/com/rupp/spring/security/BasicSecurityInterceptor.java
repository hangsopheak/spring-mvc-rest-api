/***/
package com.rupp.spring.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Sophea <a href='mailto:smak@dminc.com'> sophea </a>
 * @version $id$ - $Revision$
 * @date 2017
 */
public class BasicSecurityInterceptor extends HandlerInterceptorAdapter   {
    private static final Logger LOG = LoggerFactory.getLogger(BasicSecurityInterceptor.class);
    private static final String BASIC = "BASIC ";
    
    public BasicSecurityInterceptor() {
        LOG.info("========init BasicSecurityInterceptor=======");
        USERS.put("admin", "admin123");
        USERS.put("test", "test123");
        
    }
    //username as key and value is password
    private static final Map<String,String> USERS = new HashMap<>();
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        final String requestUri = request.getRequestURI();
        
         LOG.debug(">> Request method {} - URI : {}",request.getMethod(), requestUri);
        
        final String authorization = request.getHeader("Authorization");
        if (authorization == null) { // no auth
            doErrorJsonFormat(response);
            return false;
        }
        if (!authorization.toUpperCase().startsWith(BASIC)) {
            doErrorJsonFormat(response);
            return false;
        }
        // Get encoded user and password, comes after "BASIC "
        // Decode it, using any base 64 decoder
        String authValue = new String(Base64.decodeBase64(authorization.substring(BASIC.length())));
        String username = getClientUsername(authValue);
        String secret = getClientPassword(authValue);

        LOG.debug(String.format(">> Client's IP address: %s, username: %s, password: %s", request.getRemoteAddr(), username,
                secret));

        // check username / password
        final String realPassword = USERS.get(username);
        if (realPassword == null || secret == null || !secret.equals(realPassword)) {
            doErrorJsonFormat(response);
            return false;
        }
        return super.preHandle(request, response, handler);
    }
    
    private void doErrorJsonFormat(HttpServletResponse response) throws IOException {
        LOG.error("username and password is invalid");
        // send error as json format
        // Set response content type
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print("{\"errorMessage\":\"username and password is invalid\",\"statusCode\":401}");
    }
    
    private String getClientUsername(final String authValue) {
        String username = authValue;
        final int endIndex = authValue.indexOf(':');
        if (-1 < endIndex) {
            username = authValue.substring(0, endIndex);
        }
        return username;
    }

    private String getClientPassword(final String authValue) {
        String password = authValue;
        final int beginIndex = authValue.indexOf(':');
        if (-1 < beginIndex) {
            password = authValue.substring(beginIndex + 1);
        }
        return password;
    }    
    

}
