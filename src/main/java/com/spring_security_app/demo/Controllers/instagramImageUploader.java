package com.spring_security_app.demo.Controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/home")
public class instagramImageUploader {

    @GetMapping
    public static String mainPost() {

        String accessToken = "EAAEraHvCcQQBOZCqJXeiA86R8wxv20nvZAMAxOgGj9EcBNwPIeqZA3qTqItZAkaYgIxdMTZBb6HcrktNlKnA5ewN6wUB6PnmTNaZCjJVv2ioOONGfYuzPIlbwGx6umlD6NWCvmDhyWZAPfWB2syLY7LVIeZBw8I7ZBWMCUzq16nahWAbEcT4Jt0ZBFI5OAa9PmMW6mNyg3CKbt63tjaUefJwZDZD"; // Replace with your Instagram access token

        String imageUrl = "https://akm-img-a-in.tosshub.com/indiatoday/images/story/202002/AP20036193891466.jpeg";

        String caption = "Iyer, Shreyas Iyer !!";

        try {
            postImageToInstagram(accessToken, imageUrl, caption);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "done posting";
    }

    public static void postImageToInstagram(String accessToken, String imageUrl, String caption) throws IOException {
        String apiUrl = "https://graph.instagram.com/v18.0/17841448038478112/media";

        ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();

        // Create a RestTemplate with the OkHttp3ClientHttpRequestFactory
        RestTemplate restTemplate = new RestTemplate(factory);

        // Create headers with the access token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(accessToken);

        // Download the image from the URL and store it as a ByteArrayResource
        URL url = new URL(imageUrl);
        Path tempFile = Files.createTempFile("temp", ".png");
        Files.copy(url.openStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        ByteArrayResource imageResource = new ByteArrayResource(Files.readAllBytes(tempFile));

        // Create the request entity with headers and the image file
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("caption", caption);
        body.add("image", imageResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Media uploaded successfully. Response: " + responseEntity.getBody());
        } else {
            System.out.println("Failed to upload media. Response: " + responseEntity.getBody());
        }

        // Clean up the temporary file
        Files.deleteIfExists(tempFile);
    }
}
