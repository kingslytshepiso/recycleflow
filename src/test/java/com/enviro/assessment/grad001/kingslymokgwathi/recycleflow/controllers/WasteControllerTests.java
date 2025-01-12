package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
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

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.WasteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WasteControllerTests {

    @Autowired
    private WasteService wasteService;

    @Autowired
    private ObjectMapper mapper;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl = "http://localhost:8080/waste";

    @BeforeEach
    public void BeforeEach() {
        Waste waste1 = new Waste(null, "test waste 1", "d", null, null);
        Waste waste2 = new Waste(null, "test waste 2", "d", null, null);
        wasteService.createWaste(waste1);
        wasteService.createWaste(waste2);
    }

    @AfterEach
    public void AfterEach() {
        wasteService.deleteWaste();
    }

    @AfterAll
    public void afterAll() {
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
    @DisplayName("create new waste")
    void createWaste() {
        Waste newWaste = new Waste(null, "test waste 3", "d", null, null);
        HttpEntity<Waste> request = new HttpEntity<>(newWaste);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    @DisplayName("create new waste with null name")
    void createWasteWithNullName() throws Exception {
        Waste newWaste = new Waste(null, null, "d", null, null);
        HttpEntity<Waste> request = new HttpEntity<>(newWaste);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("message").asText()).isEqualTo("Validation failed for the request");
    }

    @Test
    @DisplayName("create an already existing waste")
    void createAnExistingWaste() throws Exception {
        Waste newWaste = new Waste(null, "test waste 1", "d", null, null);
        HttpEntity<Waste> request = new HttpEntity<>(newWaste);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("message").asText()).isNotNull();
    }

    @Test
    @DisplayName("update existing waste")
    void updateWaste() throws Exception {
        Waste existingWaste = wasteService.getWaste(0, 10).getContent().get(0);
        existingWaste.setName("updated waste");
        HttpEntity<Waste> request = new HttpEntity<>(existingWaste);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingWaste.getId(), HttpMethod.PUT,
                request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("update a non existing waste")
    void updateNonExistingWaste() throws Exception {
        Waste newWaste = new Waste(UUID.randomUUID(), "test waste 3", "d", null, null);
        newWaste.setName("updated waste");
        HttpEntity<Waste> request = new HttpEntity<>(newWaste);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + newWaste.getId(), HttpMethod.PUT,
                request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("message").asText()).isNotNull();
    }

    @Test
    @DisplayName("delete waste")
    void deleteWaste() throws Exception {
        Waste existingWaste = wasteService.getWaste(0, 10).getContent().get(1);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingWaste.getId(),
                HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate.exchange(baseUrl + "/" + existingWaste.getId(),
                HttpMethod.GET, null, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("delete non existing waste")
    void deleteNonExistingWaste() throws Exception {
        Waste newWaste = new Waste(UUID.randomUUID(), "test waste 1", "d", null, null);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + newWaste.getId(),
                HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("message").asText()).isNotNull();
    }

    @Test
    @DisplayName("get waste by id")
    void getWasteById() throws Exception {
        Waste existingWaste = wasteService.getWaste(0, 10).getContent().get(0);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingWaste.getId(), HttpMethod.GET,
                null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("id").asText()).isEqualTo(existingWaste.getId().toString());
    }

    @Test
    @DisplayName("get waste by name")
    void getWasteByName() throws Exception {
        Waste existingWaste = wasteService.getWaste(0, 10).getContent().get(0);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingWaste.getName(), HttpMethod.GET,
                null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("name").asText()).isEqualTo(existingWaste.getName());
    }

    @Test
    @DisplayName("get waste using non existing name or id")
    void getWasteByInvalidNameOrId() throws Exception {
        Waste newWaste = new Waste(UUID.randomUUID(), "test waste 5555", "d", null, null);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + newWaste.getName(), HttpMethod.GET,
                null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();

        JsonNode json = mapper.readTree(response.getBody());
        assertThat(json.get("message").asText()).isNotNull();
    }
}
