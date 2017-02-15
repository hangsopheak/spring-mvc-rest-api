Spring REST-API : it is used with maven build.

//start jetty server with maven plugin
>>mvn clean jetty:run

Open browsers :
http://localhost:8080/api/countries/v1/all


-CRUD REST-APIs :

GET http://localhost:8080/api/categories/v1/all
GET http://localhost:8080/api/cagetoires/v1/{id}

POST http://localhost:8080/api/cagetoires/v1/{id}
DELETE http://localhost:8080/api/cagetoires/v1/{id}
PUT http://localhost:8080/api/cagetoires/v1/{id}


Filter Security with Basic Authentication Header
=================

curl command:

curl 'http://localhost:8080/api/categories/v1/all'  --user 'admin:admin123'
curl 'http://localhost:8080/api/categories/v1/all'  --user 'test:test123'


web.xml

  <!--  security filter --> 
  <filter>  
     <filter-name>SecurityFilter</filter-name>  
     <filter-class>com.rupp.spring.security.SecurityFilter</filter-class>  
</filter> 
   
<filter-mapping>  
    <filter-name>SecurityFilter</filter-name>  
    <url-pattern>/api/*</url-pattern>  
</filter-mapping>  


/***/
/***/
package com.rupp.spring.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sophea <a href='mailto:smak@dminc.com'> sophea </a>
 * @version $id$ - $Revision$
 * @date 2017
 */
public class SecurityFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String BASIC = "BASIC ";
    //username as key and value is password
    private static final Map<String,String> USERS = new HashMap<>();
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOG.info("init Security filter");
        USERS.put("admin", "admin123");
        USERS.put("test", "test123");
        
    }
    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        final String requestUri = request.getRequestURI();
        
        LOG.debug(">> Request method {} - URI : {}",request.getMethod(), requestUri);
        
        final String authorization = request.getHeader("Authorization");
        if (authorization == null) { // no auth
            doErrorJsonFormat(response);
            return;
        }
        if (!authorization.toUpperCase().startsWith(BASIC)) {
            doErrorJsonFormat(response);
            return;
        }
        // Get encoded user and password, comes after "BASIC "
        // Decode it, using any base 64 decoder
        String authValue = new String(Base64.decodeBase64(authorization.substring(BASIC.length())));
        String username = getClientUsername(authValue);
        String secret = getClientPassword(authValue);
        
        
        LOG.debug(String.format(">> Client's IP address: %s, username: %s, password: %s", request.getRemoteAddr(), username, secret));
        
        //check username / password
        final String realPassword = USERS.get(username); 
        if (realPassword == null || secret == null || !secret.equals(realPassword)) {
            doErrorJsonFormat(response);
            return;
        }
        
        chain.doFilter(req, resp);
    }
    
    private void doErrorJsonFormat(HttpServletResponse response) throws IOException {
        LOG.error("username and password is invalid");
        // send error as json format
        // Set response content type
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print("{\"errorMessage\":\"username and password is invalid\"}");
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
    
    @Override
    public void destroy() {

    }
}

}

