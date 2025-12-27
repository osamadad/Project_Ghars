package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOout.GreenHouseLearningDTOOut;
import com.tuwaiq.project_ghars.DTOout.WaterPlantingLearningDTOOut;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ObjectMapper objectMapper;
    @Value("${openai.api-key}")
    private String openAiApiKey;

    /**
     * Core AI method
     * You will reuse this later in any endpoint you want
     */
    public String askAI(String prompt) {

        if (prompt == null || prompt.isBlank())
            throw new ApiException("Prompt cannot be empty");

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");
        body.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null)
            throw new ApiException("Empty AI response");

        List choices = (List) responseBody.get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");

        return message.get("content").toString();
    }

    public GreenHouseLearningDTOOut greenHouseLearningAI() {

        String prompt = """
                You are an AI plant education expert specialized in teaching beginners about greenhouses.
                
                Teach greenhouse concepts in an educational-first way.
                Do NOT give step-by-step construction instructions.
                Do NOT include tools, materials, measurements, or costs.
                Focus on understanding, benefits, limitations, and decision-making.
                
                Return ONLY valid JSON in this exact format:
                
                {
                  "definition": "",
                  "benefits": [],
                  "limitations": [],
                  "greenhouseTypes": [],
                  "effectsOnPlants": [],
                  "whenToUseAGreenhouse": [],
                  "basicComponents": [],
                  "commonMisconceptions": [],
                  "aiSummary": ""
                }
                
                """;

        String response = askAI(prompt);

        GreenHouseLearningDTOOut dto = objectMapper.readValue(response, GreenHouseLearningDTOOut.class);

        return dto;
    }

    public WaterPlantingLearningDTOOut waterPlantingLearningAI() {

        String prompt = """
                You are an AI plant education expert specialized in teaching beginners about water planting (growing plants in water).
                
                Teach water planting concepts in an educational-first way.
                Do NOT give step-by-step setup instructions.
                Do NOT include tools, equipment, measurements, nutrient ratios, or costs.
                Focus on understanding, benefits, limitations, and decision-making.
                
                Return ONLY valid JSON in this exact format:
                
                {
                  "definition": "",
                  "benefits": [],
                  "limitations": [],
                  "waterPlantingTypes": [],
                  "effectsOnPlants": [],
                  "whenToUseWaterPlanting": [],
                  "plantSuitability": [],
                  "commonMisconceptions": [],
                  "aiSummary": ""
                }
                
                """;


        String response = askAI(prompt);

        WaterPlantingLearningDTOOut dto = objectMapper.readValue(response, WaterPlantingLearningDTOOut.class);

        return dto;
    }
}
