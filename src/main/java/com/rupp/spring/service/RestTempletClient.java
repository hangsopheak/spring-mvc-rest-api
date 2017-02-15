/***/
package com.rupp.spring.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupp.spring.domain.DCategory;

/**
 * @author Sophea <a href='mailto:smak@dminc.com'> sophea </a>
 * @version $id$ - $Revision$
 * @date 2017
 */
public class RestTempletClient {
    public static final String REST_SERVICE_URI = "http://localhost:8080/api";
    private static ObjectMapper mapper = new ObjectMapper();
    
    /* Add HTTP Authorization header, using Basic-Authentication to send user-credentials.*/
    private static HttpHeaders getHeaders(){
        String plainCredentials="admin:admin123";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
         
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
     
    /** Send a GET request to get list of all users.*/
    private static void listAllCategories() throws Exception {
        System.out.println("\nTesting listAllCategories API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI+"/categories/v1/all", HttpMethod.GET, request, String.class);
        
        System.out.println(response.getBody());
        //convert as java object
            final List<DCategory> categories = Arrays.asList(mapper.readValue(response.getBody(), DCategory[].class));
            for (DCategory dCategory : categories) {
                System.out.println(String.format("id : %s, name %s", dCategory.getId(), dCategory.getName()));
            }
        
    }
    public static void main(String[] args) throws Exception {
        listAllCategories();
    }
}
