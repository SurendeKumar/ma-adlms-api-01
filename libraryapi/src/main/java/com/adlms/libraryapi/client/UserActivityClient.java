package com.adlms.libraryapi.client;

import com.adlms.libraryapi.dto.ExternalBorrowRecordDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class UserActivityClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserActivityClient(RestTemplate restTemplate,
                              @Value("${user.activity.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<ExternalBorrowRecordDTO> getBorrowRecordsByDocumentId(Long documentId) {
        String url = baseUrl + "/documents/" + documentId + "/borrow-records";

        try {
            ResponseEntity<List<ExternalBorrowRecordDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ExternalBorrowRecordDTO>>() {}
            );

            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}