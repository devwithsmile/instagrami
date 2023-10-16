package com.spring_security_app.demo.Controllers;

import com.spring_security_app.demo.DTO.ChatGPTRequest;
import com.spring_security_app.demo.DTO.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping()
    public List<String> chat(@RequestParam("prompt") String prompt) {
        int responseCount = 3; // Set the number of responses you want here
        List<String> responses = new ArrayList<>();

        for (int i = 0; i < responseCount; i++) {
            ChatGPTRequest request = new ChatGPTRequest(model, prompt);
            ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
            responses.add(chatGptResponse.getChoices().get(0).getMessage().getContent());
        }

        return responses;
    }
}
