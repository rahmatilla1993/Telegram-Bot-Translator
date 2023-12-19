package org.example.service;

import com.google.gson.Gson;
import org.example.pojo.ApiResponse;
import org.example.utils.PropertiesUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.Version.HTTP_2;

public class ApiService {
    private static final String apiKey = PropertiesUtil.get("API_KEY");
    private static final String baseUrl = PropertiesUtil.get("BASE_URL");
    private final HttpClient httpClient;

    public ApiService() {
        this.httpClient = HttpClient.newBuilder()
                .version(HTTP_2)
                .build();
    }

    public String getTranslate(String sentence, String fromLang, String toLang) {
        Gson gson = new Gson();
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("%s/?key=%s", baseUrl, apiKey)))
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                          "q": "%s",
                          "source": "%s",
                          "target": "%s",
                          "format": "text"
                        }
                        """.formatted(sentence, fromLang, toLang)))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient
                    .send(
                            httpRequest,
                            HttpResponse.BodyHandlers.ofString()
                    );
            ApiResponse apiResponse = gson.fromJson(httpResponse.body(), ApiResponse.class);
            return apiResponse
                    .getData()
                    .getTranslations()
                    .get(0)
                    .getTranslatedText();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
