package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlantNetService {

    @Value("${plantnet.api-key}")
    private String apiKey;

    private final String apiUrlIdentify="https://my-api.plantnet.org/v2/identify/";

    private final String apiUrlDiseases="https://my-api.plantnet.org/v2/diseases/";

    private final RestTemplate restTemplate = new RestTemplate();

    public String identifyPlant(MultipartFile image, String organ) throws IOException {
        if (image.isEmpty()) {
            throw new ApiException("Image file is required");
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("images", new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        });
        body.add("organs", organ);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        String url = apiUrlIdentify + "?api-key=" + apiKey;

        return restTemplate
                .postForEntity(url, request, String.class)
                .getBody();
    }

    public String identifyPlantDiseases(MultipartFile image, String organ) throws IOException {
        if (image.isEmpty()) {
            throw new ApiException("Image file is required");
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("images", new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        });
        body.add("organs", organ);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        String url = apiUrlDiseases + "?api-key=" + apiKey;

        return restTemplate
                .postForEntity(url, request, String.class)
                .getBody();
    }
}



