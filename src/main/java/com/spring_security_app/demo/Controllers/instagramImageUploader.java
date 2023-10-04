package com.spring_security_app.demo.Controllers;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


//https://youtu.be/Q5kw7vGLqgs?si=w2JGbDpUsHJvLgo4


@RestController
@RequestMapping("/insta")
public class instagramImageUploader {

    @NotNull
    private String getContainerUrl(String accessToken) {
        String imageUrl = "https://akm-img-a-in.tosshub.com/indiatoday/images/story/202002/AP20036193891466.jpeg";
        String caption = "@devwithsmile posted this trough java code,@_shubhda !!";
        String apiUrl = "https://graph.facebook.com/v18.0/17841448038478112/media";


        String containerURL = apiUrl + "?image_url=" + imageUrl + "&caption=" + caption + "&access_token=" + accessToken;
        return containerURL;
    }

    private void setHeaders() {
        // Create a RestTemplate

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Add any other headers as needed

        // Set the request entity, in this case, you might want to send some JSON payload
        // Example JSON payload:
        // String jsonPayload = "{\"key1\":\"value1\", \"key2\":\"value2\"}";
        // HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
    }

    private String getContainerId(ResponseEntity responseEntity) {
        // Check the response
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseData = responseEntity.getBody().toString();
            System.out.println("Response data as JSON: " + responseData);

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(responseData);

            // Extract the "id" from the JSON object
            String id = jsonResponse.getString("id");

            return id;
        } else {
            return "HTTP request failed with response code: " + responseEntity.getStatusCode();
        }
    }

    private String getMainUrl(String creation_id, String access_Token) {
        //https://graph.facebook.com/v18.0/17841448038478112/media_publish?creation_id={creation_id}&access_token={access_token}

        String apiURL = "https://graph.facebook.com/v18.0/17841448038478112/media_publish";
        String mainURL = apiURL + "?creation_id=" + creation_id + "&access_token=" + access_Token;


        return mainURL;
    }

    private String postImage(String mainURL) {
        ResponseEntity<String> responseEntity = null;
        try {

            RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate.exchange(mainURL, HttpMethod.POST, null, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "Image Posted";
        } else {
            return "Image was not posted";
        }


    }


    @GetMapping
    public String postImageToInstagram() {

        setHeaders();

        String access_token = "EAAEraHvCcQQBOyybL7ViaQmsqoiazrb1SbKXZCtq0cT0Iovk8Dps5eBgp7U06QyBVYRYuzApLljjaQUzG2ADjVpvm2VYg1gZBoMIQ9iPPs8JUNwiL1UAEMrLCczz6e7x8OGcNChcGeELOuUJlZArfEvS6SyLChsw04oZAErGda8hOQ5tU5RutIjKZCf0ZBW39ab1ZBvDfRmZCDUNA9FlOVnkqfv3LtAZD";
        /* _______________________get URL to get ContainerID______________________________*/
        String containerURL = getContainerUrl(access_token);

        /* _______________________send post request and get ContainerID___________________________*/
        // Make the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(containerURL, HttpMethod.POST, null, String.class);
        String containerID = getContainerId(responseEntity);

        /* _______________________get MainURL______________________________*/
        String mainURL = getMainUrl(containerID, access_token);

        /* _______________________send post req to main URL to post______________________________*/

        String result = postImage(mainURL);


        return result;
    }

}


