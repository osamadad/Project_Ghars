package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOout.GreenHouseLearningDTOOut;
import com.tuwaiq.project_ghars.DTOout.RecommendedEventDTOOut;
import com.tuwaiq.project_ghars.DTOout.WaterPlantingLearningDTOOut;
import com.tuwaiq.project_ghars.Model.Event;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Repository.EventRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository ;
    private final FarmerRepository farmerRepository ;
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
                Map.of("role", "user", "content", prompt)));

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

    public String soilAndSeeds(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("التربة والبذور", farmer.getLevel(), farmer.getExperience());
        return askAI(prompt);
    }

    public String homeGardening(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("الزراعة المنزلية", farmer.getLevel(), farmer.getExperience());
        return askAI(prompt);
    }

    public String wateringAndFertilizing(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("الري والتسميد", farmer.getLevel(), farmer.getExperience());
        return askAI(prompt);
    }

    public String plantCare(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("العناية بالنباتات", farmer.getLevel(), farmer.getExperience());
        return askAI(prompt);
    }

    public String plantProblems(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("مشاكل النباتات", farmer.getLevel(), farmer.getExperience());
        return askAI(prompt);
    }

    private String buildPrompt(String topic, String level, String experience) {

        String normalizedLevel = (level == null) ? "BEGINNER" : level.trim().toUpperCase();

        return """
        أنت مساعد زراعي لمنصة "غرس".
        اشرح موضوع: %s
        بناءً على مستوى المزارع: %s
        وبناءً على خبرته (كما كتبها): %s

        المطلوب:
        - اكتب بالعربي وبأسلوب بسيط وواضح مناسب للمستوى.
        - أعطني:
          1) مقدمة سطرين
          2) خطوات عملية مرقمة (5 إلى 8 خطوات)
          3) أخطاء شائعة (3 إلى 5)
          4) نصائح سريعة (3 إلى 5)
          5) "وش أسوي الحين؟" خطوة واحدة قابلة للتنفيذ اليوم
        - لا تكتب كلام عام، خليها عملية ومباشرة.
        """.formatted(topic, normalizedLevel, (experience == null ? "غير محددة" : experience));
    }

    public RecommendedEventDTOOut recommendBestEvent(Integer farmerId) {

        Farmer farmer = farmerRepository.findFarmerById(farmerId);

        List<Event> events = eventRepository.findAllByDateGreaterThanEqual(LocalDate.now());
        if (events == null || events.isEmpty())
            throw new ApiException("No upcoming events found");

        String eventsText = buildEventsText(events);

        String prompt = """
    أنت مساعد لمنصة "غرس".
    اختر أفضل فعالية واحدة فقط من القائمة التالية للمزارع بناءً على:
    - مستوى المزارع: %s
    - خبرته: %s

    قائمة الإيفنتات (اختر ID واحد فقط من القائمة):
    %s

    المطلوب:
    - اختر Event واحدة فقط من القائمة (لا تختر شيء خارجها).
    - أرجع الرد بصيغة JSON فقط بدون أي نص إضافي:
    {
      "eventId": 1,
      "reason": "سبب الاختيار في 2-3 أسطر",
      "whatToPrepare": "3 نقاط تجهيز قبل الحضور"
    }
    """.formatted(
                farmer.getLevel(),
                farmer.getExperience(),
                eventsText
        );

        String aiResult = askAI(prompt);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> json = mapper.readValue(aiResult, Map.class);

            Integer eventId = (Integer) json.get("eventId");
            String reason = String.valueOf(json.get("reason"));
            String whatToPrepare = String.valueOf(json.get("whatToPrepare"));

            Event chosen = eventRepository.findEventById(eventId);
            if (chosen == null)
                throw new ApiException("AI returned invalid eventId");

            return new RecommendedEventDTOOut(
                    chosen.getId(),
                    chosen.getTitle(),
                    chosen.getDescription(),
                    chosen.getLocation(),
                    chosen.getDate().toString(),
                    chosen.getStartTime().toString(),
                    chosen.getEndTime().toString(),
                    reason,
                    whatToPrepare
            );

        } catch (Exception e) {
            throw new ApiException("Failed to parse AI response: " + e.getMessage());
        }
    }

    private String buildEventsText(List<Event> events) {
        StringBuilder sb = new StringBuilder();
        for (Event e : events) {
            sb.append("- ID: ").append(e.getId())
                    .append(" | Title: ").append(e.getTitle())
                    .append(" | Location: ").append(e.getLocation())
                    .append(" | Date: ").append(e.getDate())
                    .append(" | Time: ").append(e.getStartTime()).append("-").append(e.getEndTime())
                    .append(" | Description: ").append(e.getDescription())
                    .append("\n");
        }
        return sb.toString();
    }
}