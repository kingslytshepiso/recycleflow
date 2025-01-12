package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.ClassificationService;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.WasteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClassificationControllerTests {
    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private WasteService wasteService;

    @Autowired
    private ObjectMapper mapper;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl = "http://localhost:8080/classification";

    @BeforeEach
    public void BeforeEach() {
        Waste waste1 = new Waste(null, "test waste 1", "d", null, new HashSet<>());
        Waste waste2 = new Waste(null, "test waste 2", "d", null, new HashSet<>());
        Classification classification1 = new Classification(null, "test classification 1", "d",
                null, new HashSet<>());
        Classification classification2 = new Classification(null, "test classification 2", "d",
                null, new HashSet<>());

        waste1.getClassifications().add(classification1);
        waste2.getClassifications().add(classification2);
        classification1.getWaste().add(waste1);
        classification2.getWaste().add(waste2);
        classificationService.createClassification(classification1);
        classificationService.createClassification(classification2);
    }

    @AfterEach
    public void AfterEach() {
        classificationService.deleteAllClassifications();
    }

    @Test
    @DisplayName("get all waste")
    void getListOfWaste() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());

        assertThat(json.get("content")).isNotNull();
    }

    @Test
    @DisplayName("create new classification")
    void createClassification() {
        Classification newClass = new Classification(null, "test classification 3", "d", null, null);
        HttpEntity<Classification> request = new HttpEntity<>(newClass);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }
}
