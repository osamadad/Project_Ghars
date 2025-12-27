package com.tuwaiq.project_ghars.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {

    @Value("${WHATSAPP_TOKEN}")
    private String token;

    public void sendWhatsAppMessage(String to, String message) {
        UnirestInstance unirest = Unirest.spawnInstance();
        unirest.config()
                .socketTimeout(0)
                .connectTimeout(0);

        HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/instance153447/messages/chat")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", token)
                .field("to", to)
                .field("body", message)

                .asString();
        System.out.println(response.getBody());
    }
}
