package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.WasteService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WasteControllerTests {

    @Autowired
    private WasteService wasteService;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl = "http://localhost:8080/waste";

    @BeforeAll
    public void BeforeAll() {
        Waste waste1 = new Waste(null, "test waste 1", "d", null);
        Waste waste2 = new Waste(null, "test waste 2", "d", null);
        wasteService.createWaste(waste1);
        wasteService.createWaste(waste2);
    }

    @AfterAll
    public void afterAll() {
    }

    @Test
    @DisplayName("get all waste")
    void getListOfWaste() {
        ResponseEntity<Object> response = restTemplate.getForEntity(baseUrl, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody());
    }
}
