package com.company.alpicoapi.api;

import com.company.alpicoapi.dto.AlpicoRequestDTO;
import com.company.alpicoapi.dto.AlpicoResponseDTO;
import com.company.alpicoapi.exceptions.ApiException;
import com.company.alpicoapi.model.MagicItem;
import com.company.alpicoapi.utils.JsonUtils;
import com.company.alpicoapi.utils.Stopwatch;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AlpicoApi {
    private final RestTemplate restTemplate;

    public AlpicoApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] initialize(String magic) {
        ResponseEntity<String[]> response = restTemplate.getForEntity("https://europe-west1-uh-protected.cloudfunctions.net/aipico/" + magic, String[].class);
        return response.getBody();
    }

    public AlpicoResponseDTO getPart(String magic, HttpEntity<AlpicoRequestDTO> dto) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        try {
            ResponseEntity<AlpicoResponseDTO> postResponse = restTemplate.exchange("https://europe-west1-uh-protected.cloudfunctions.net/aipico/" + magic, HttpMethod.POST, dto, AlpicoResponseDTO.class);
            AlpicoResponseDTO body = postResponse.getBody();
            return new AlpicoResponseDTO()
                    .setSuccess(true)
                    .setPayload(Objects.requireNonNull(body).getPayload());
        } catch (HttpStatusCodeException e) {
            return parseError(e.getResponseBodyAsString());
        }
    }

    public static HttpEntity<AlpicoRequestDTO> createRequest(String token, List<MagicItem> responses) {
        List<MagicItem> copy = new ArrayList<>(responses);
        copy.sort(Comparator.comparingInt(MagicItem::getIndex));

        Map<Integer, String> tokenResultMap = new HashMap<>();
        for (int i = 0; i < copy.size(); i++) {
            MagicItem magicThing = copy.get(i);
            if (magicThing.getResult() != null) {
                tokenResultMap.put(i, magicThing.getResult());
            }
        }

        AlpicoRequestDTO request = new AlpicoRequestDTO()
                .setPayload(token)
                .setResponses(tokenResultMap);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(request, headers);
    }

    private static AlpicoResponseDTO parseError(String responseString) {
        int startIndex = responseString.indexOf("{");
        int endIndex = responseString.lastIndexOf("}");

        if (startIndex >= 0 && endIndex >= 0) {
            String json = responseString.substring(startIndex, endIndex + 1);
            return JsonUtils.fromJson(json, AlpicoResponseDTO.class);
        } else {
            throw ApiException.internalError("err.alpico.response");
        }
    }
}
