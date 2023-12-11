package com.teamseven.cafeplatform.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoLocalApiHelper {
    private final RestTemplate restTemplate;
    @Value("${kakao_api_key}")
    private String apiKey;
    private final String baseUrl = "https://dapi.kakao.com/v2/local";

    public DirectionDTO getDirectionByKeyword(String keyword) throws Exception{
        String apiUrl = "/search/keyword.json";
        String query = "?query="+ URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + apiKey);

        URI url = URI.create(baseUrl+apiUrl+query);
        RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<String> re = restTemplate.exchange(rq, String.class);
        return parseDirectionFromResponse(re.getBody());
    }

    public DirectionDTO parseDirectionFromResponse(String response) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode firstDocument = root.path("documents").get(0);
        double longitude = firstDocument.get("x").asDouble();
        double latitude = firstDocument.get("y").asDouble();

        return DirectionDTO.builder().longitude(longitude).latitude(latitude).build();
    }

    public String getRegionByCoords(double longitude, double latitude) throws Exception{
        String apiUrl = "/geo/coord2regioncode.json";
        String query = "?x="+ URLEncoder.encode(String.valueOf(longitude), StandardCharsets.UTF_8)+"&y="+ URLEncoder.encode(String.valueOf(latitude), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + apiKey);

        URI url = URI.create(baseUrl+apiUrl+query);
        RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<String> re = restTemplate.exchange(rq, String.class);
        return parseRegionFromResponse(re.getBody());
    }

    public String parseRegionFromResponse(String response) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode firstDocument = root.path("documents").get(0);
        return firstDocument.get("region_2depth_name").toString();
    }

}
