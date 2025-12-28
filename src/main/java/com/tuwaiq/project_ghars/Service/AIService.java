package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOout.GreenHouseLearningDTOOut;
import com.tuwaiq.project_ghars.DTOout.RecommendedEventDTOOut;
import com.tuwaiq.project_ghars.DTOout.SeasonPlantDTOOut;
import com.tuwaiq.project_ghars.DTOout.WaterPlantingLearningDTOOut;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.EventRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.PlantTypeRepository;
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
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final ObjectMapper objectMapper;
    private final PlantTypeRepository plantTypeRepository;
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

    public GreenHouseLearningDTOOut greenHouseLearningAI(Integer userId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("Farmer not found");
        }
        String prompt = """
                أنت خبير ذكاء اصطناعي في تعليم النباتات، ومتخصص في تعليم المبتدئين عن البيوت المحمية
                (الصوبات الزراعية).

                علّم مفاهيم البيوت المحمية بأسلوب تعليمي يركّز على الفهم أولًا.
                لا تقدّم تعليمات إنشاء خطوة بخطوة.
                لا تذكر أدوات أو مواد أو قياسات أو تكاليف.
                ركّز على الفهم، والفوائد، والقيود، وكيفية اتخاذ القرار.

                مستوى خبرة المزارع:
                - %s

                أعد ناتجًا بصيغة JSON صالحة فقط وبهذا التنسيق الدقيق:

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

                """.formatted(
                farmer.getFarmerRank()
        );


        String response = askAI(prompt);

        GreenHouseLearningDTOOut dto = objectMapper.readValue(response, GreenHouseLearningDTOOut.class);

        return dto;
    }

    public WaterPlantingLearningDTOOut waterPlantingLearningAI(Integer userId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("Farmer not found");
        }
        String prompt = """
                أنت خبير ذكاء اصطناعي في تعليم النباتات، ومتخصص في تعليم المبتدئين الزراعة المائية
                (زراعة النباتات في الماء).

                علّم مفاهيم الزراعة المائية بأسلوب تعليمي يركّز على الفهم أولًا.
                لا تقدّم إرشادات إعداد خطوة بخطوة.
                لا تذكر أدوات أو معدات أو قياسات أو نسب مغذيات أو تكاليف.
                ركّز على الفهم، والفوائد، والقيود، وكيفية اتخاذ القرار.

                مستوى خبرة المزارع:
                - %s

                أعد ناتجًا بصيغة JSON صالحة فقط وبهذا التنسيق الدقيق:

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

                """.formatted(
                farmer.getFarmerRank()
        );


        String response = askAI(prompt);

        WaterPlantingLearningDTOOut dto = objectMapper.readValue(response, WaterPlantingLearningDTOOut.class);

        return dto;
    }

    public String soilAndSeeds(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("التربة والبذور", farmer.getFarmerRank());
        return askAI(prompt);
    }

    public String homeGardening(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("الزراعة المنزلية", farmer.getFarmerRank());
        return askAI(prompt);
    }

    public String wateringAndFertilizing(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("الري والتسميد", farmer.getFarmerRank());
        return askAI(prompt);
    }

    public String plantCare(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("العناية بالنباتات", farmer.getFarmerRank());
        return askAI(prompt);
    }

    public String plantProblems(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        String prompt = buildPrompt("مشاكل النباتات", farmer.getFarmerRank());
        return askAI(prompt);
    }

    private String buildPrompt(String topic, String farmerRank) {

        String normalizedLevel = (farmerRank == null) ? "BEGINNER" : farmerRank.trim().toUpperCase();

        return """
                أنت مساعد زراعي لمنصة "غرس".
                اشرح موضوع: %s
                بناءً على مستوى المزارع: %s
                
                المطلوب:
                - اكتب بالعربي وبأسلوب بسيط وواضح مناسب للمستوى.
                - أعطني:
                  1) مقدمة سطرين
                  2) خطوات عملية مرقمة (5 إلى 8 خطوات)
                  3) أخطاء شائعة (3 إلى 5)
                  4) نصائح سريعة (3 إلى 5)
                  5) "وش أسوي الحين؟" خطوة واحدة قابلة للتنفيذ اليوم
                - لا تكتب كلام عام، خليها عملية ومباشرة.
                """.formatted(topic, normalizedLevel);
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
                - مستوى خبرة المزارع: %s
                
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
                farmer.getFarmerRank(),
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


    public List<SeasonPlantDTOOut> getSeasonPlants(String season) {

        if (season == null || season.isBlank()) {
            throw new ApiException("Season is required");
        }

        List<PlantType> plants = plantTypeRepository.findPlantTypeBySeasonIgnoreCase(season);

        if (plants.isEmpty()) {
            throw new ApiException("No plants found for this season");
        }
        StringBuilder plantsInfo = new StringBuilder();
        for (PlantType plant : plants) {
            plantsInfo.append("- ")
                    .append(plant.getCommonName())
                    .append(" (")
                    .append(plant.getCategory())
                    .append(", difficulty: ")
                    .append(plant.getDifficultyLevel())
                    .append(")\n");
        }

        String prompt = """
                أنت خبير زراعي.
                هذه قائمة نباتات مناسبة لموسم %s:
                %s
                
                المطلوب:
                - اختر أفضل النباتات للمزارع
                - اكتب سبب قصير لكل نبات (سطر واحد)
                - لا تذكر خطوات زراعة
                - رجع النتيجة بصيغة:
                PlantName: reason
                """.formatted(season, plantsInfo);

        String aiResponse = askAI(prompt);

        return plants.stream()
                .map(plant -> new SeasonPlantDTOOut(
                        plant.getCommonName(),
                        plant.getCategory(),
                        plant.getDifficultyLevel(),
                        aiResponse
                ))
                .toList();
    }


    public String smartIrrigationSchedule(String plant, String season, String location) {

        if (plant == null || plant.isBlank())
            throw new ApiException("Plant is required");

        if (season == null || season.isBlank())
            throw new ApiException("Season is required");

        String prompt = """
                أنت خبير زراعي ذكي.
                
                أعطني جدول ري ذكي للنبات التالي:
                - النبات: %s
                - الموسم: %s
                - الموقع: %s
                
                المطلوب:
                - عدد مرات الري بالأسبوع
                - أفضل وقت للري
                - نصيحة مهمة (تحذير أو ملاحظة)
                - اكتب بالعربي
                - بدون خطوات طويلة
                - بدون كلام عام
                
                رجّع النتيجة كنص واضح وجاهز للعرض.
                """.formatted(
                plant,
                season,
                (location == null ? "غير محدد" : location)
        );
        return askAI(prompt);
    }


    public String recommendBestPlantForMe(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null || user.getFarmer() == null) {
            throw new ApiException("User is not a farmer");
        }

        Farmer farmer = user.getFarmer();

        String prompt = """
                أنت خبير زراعي.
                
                أعطني اقتراح نبات مناسب لهذا المزارع:
                - مستوى الخبرة: %s
                
                المطلوب:
                - اسم النبات
                - سبب مختصر
                - ليش مناسب لمستواه
                - بدون خطوات زراعة
                - بالعربي
                - سطرين فقط
                """.formatted(
                farmer.getFarmerRank()
        );

        return askAI(prompt);
    }

    public String filterPlantsByLocation(String city) {

        String prompt = """
                أنت خبير زراعي.
                المدينة: %s
                
                المطلوب:
                - اقترح نباتات مناسبة للزراعة في هذه المدينة
                - صنّف كل نبات حسب الصعوبة: (سهل / صعب)
                - لا تذكر خطوات زراعة
                - لا تذكر طقس رقمي
                - رجّع النتيجة بهذا الشكل فقط:
                
                🌱 النبات:
                - الاسم: ...
                - المستوى: سهل / صعب
                - السبب: سطر واحد
                
                """.formatted(city);

        return askAI(prompt);
    }

    public VirtualPlot convertPlantToVirtualPlot(PlantType plantType) {

        String prompt = """
                أنت محرّك منطق لعبة حتمي مسؤول عن توليد قيم حالة النبات التي يتحكّم بها النظام.
                لا تستنتج أو تختلق أي بيانات خارج القواعد.
                أعد **JSON صالح فقط** وبنفس التنسيق المحدّد حرفيًا.
                
                تنسيق ناتج JSON:
                {
                  "progress": 0,
                  "health": 0,
                  "status": "",
                  "expectedYield": 0,
                  "actualYield": 0,
                  "experienceGiven": 0,
                  "knowledgeMeter": 0,
                  "waterMeter": 0,
                  "sunMeter": 0,
                  "verificationPic": null,
                  "plantedAt": ""
                }
                
                بيانات النبات:
                - سرعة النمو: %s
                - مستوى الصعوبة: %s
                - احتياج الماء: %s
                - احتياج الشمس: %s
                
                القواعد:
                
                التقدّم (Progress):
                - بطيء   -> الحد الأقصى للتقدّم = 60
                - طبيعي  -> الحد الأقصى للتقدّم = 80
                - سريع   -> الحد الأقصى للتقدّم = 100
                - يجب أن تكون قيمة التقدّم الابتدائية 0
                
                الصحة (Health):
                - دائمًا 100
                
                الحالة (Status):
                - دائمًا "growing"
                
                المحصول المتوقع (يزداد مع الصعوبة):
                - سهل    -> 8
                - متوسط  -> 15
                - صعب    -> 25
                
                المحصول الفعلي (Actual Yield):
                - دائمًا 0
                
                الخبرة المكتسبة (Experience Given):
                - سهل    -> 50
                - متوسط  -> 100
                - صعب    -> 200
                
                مقياس المعرفة (Knowledge Meter):
                - دائمًا 0
                
                مقياس الماء (Water Meter):
                - القيمة الأساسية = 50
                - منخفض  -> الحد الأقصى = 70
                - متوسط  -> الحد الأقصى = 100
                - مرتفع  -> الحد الأقصى = 130
                - القيمة الابتدائية = (50 + الحد الأقصى) / 2
                
                مقياس الشمس (Sun Meter):
                - القيمة الأساسية = 50
                - منخفض  -> الحد الأقصى = 70
                - متوسط  -> الحد الأقصى = 100
                - مرتفع  -> الحد الأقصى = 130
                - القيمة الابتدائية = (50 + الحد الأقصى) / 2
                
                صورة التحقق (Verification Picture):
                - دائمًا null
                
                تاريخ الزراعة (Planted At):
                - التاريخ والوقت الحاليان بصيغة ISO-8601
                """.formatted(
                plantType.getGrowthSpeed(),
                plantType.getDifficultyLevel(),
                plantType.getWaterNeeds(),
                plantType.getSunNeeds()
        );

        String response = askAI(prompt);

        VirtualPlot virtualPlot = objectMapper.readValue(response, VirtualPlot.class);

        return virtualPlot;

    }

}