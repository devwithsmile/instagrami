package com.spring_security_app.demo.Controllers.ChatGPT;

import com.spring_security_app.demo.DTO.ChatGPT.ChatGPTRequest;
import com.spring_security_app.demo.DTO.ChatGPT.ChatGptResponse;
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
    public List<String> chat(@RequestParam("photoDescription") String photoDescription) {
        int responseCount = 3; // Set the number of responses you want here
        List<String> responses = new ArrayList<>();

        for (int i = 0; i < responseCount; i++) {
            // Include the instruction along with the user's input
            String prompt = "\"Imagine you're the ultimate Instagram caption maestro. I need you to craft a captivating and fancy caption, sprinkled with emojis, for a photo of around 10 to 12 words. Here is the description of photo  : " + photoDescription;
            ChatGPTRequest request = new ChatGPTRequest(model, prompt);
            ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
            responses.add(chatGptResponse.getChoices().get(0).getMessage().getContent());
        }

        return responses;
    }
}
