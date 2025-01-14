package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.dtos.GetClassificationDto;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.ClassificationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClassificationControllerTests {
        @Autowired
        private ClassificationService classificationService;

        @Autowired
        private ObjectMapper mapper;

        private TestRestTemplate restTemplate = new TestRestTemplate();
        private String baseUrl = "http://localhost:8080/classifications";

        @BeforeEach
        public void BeforeEach() {
                Waste waste1 = new Waste(null, "test waste 1", "d", null, null);
                Waste waste2 = new Waste(null, "test waste 2", "d", null, null);
                Classification classification1 = new Classification(null, "test classification 1", "d",
                                null, new HashSet<>());
                Classification classification2 = new Classification(null, "test classification 2", "d",
                                null, new HashSet<>());

                waste1.setClassification(classification1);
                waste2.setClassification(classification2);
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
        @DisplayName("get all classifications")
        void getListOfClassifications() throws Exception {
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
                ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request,
                                String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getHeaders().getLocation()).isNotNull();
        }

        @Test
        @DisplayName("create new classification with null name")
        void createClassificationWithNullName() throws Exception {
                Classification newClass = new Classification(null, null, "d", null, null);
                HttpEntity<Classification> request = new HttpEntity<>(newClass);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request,
                                String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("create a classification that already exists")
        void createClassificationThatAlreadyExists() throws Exception {
                Classification newClass = new Classification(null, "test classification 1", "d", null, null);
                HttpEntity<Classification> request = new HttpEntity<>(newClass);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request,
                                String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        }

        @Test
        @DisplayName("get classification by id")
        void getClassificationById() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.GET, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("id")).isNotNull();
        }

        @Test
        @DisplayName("get classification by name")
        void getClassificationByName() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                ResponseEntity<String> response = restTemplate.exchange(
                                baseUrl + "/" + existingClassification.getName(),
                                HttpMethod.GET, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("name")).isNotNull();
                assertThat(json.get("name").asText()).isEqualTo(existingClassification.getName());
        }

        @Test
        @DisplayName("get classification by name or id that does not exist")
        void getClassificationByNameOrIdThatDoesNotExist() throws Exception {
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/-1", HttpMethod.GET, null,
                                String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(response.getBody()).isNotNull();
        }

        @Test
        @DisplayName("search and find classification")
        void searchClassification() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                ResponseEntity<String> response = restTemplate.exchange(
                                baseUrl + "/search?query=" + existingClassification.getName(),
                                HttpMethod.GET, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("content")).isNotNull();
        }

        @Test
        @DisplayName("search and not find classification")
        void searchClassificationAndNotFound() throws Exception {
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/search?query=notfound",
                                HttpMethod.GET, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("content")).isEmpty();
        }

        @Test
        @DisplayName("search classification with null query")
        void searchClassificationWithNullQuery() throws Exception {
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/search",
                                HttpMethod.GET, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("message")).isNotNull();
        }

        @SuppressWarnings("null")
        @Test
        @DisplayName("Update classification name")
        void updateClassification() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                Classification updatedClassification = new Classification(existingClassification.getId(),
                                "updated classification",
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                ResponseEntity<GetClassificationDto> getResponse = restTemplate.exchange(
                                baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.GET, null, GetClassificationDto.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(getResponse.getBody()).isNotNull();
                assertThat(getResponse.getBody().getName()).isEqualTo(updatedClassification.getName());
        }

        @Test
        @DisplayName("Update classification with null name")
        void updateClassificationWithNullName() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                Classification updatedClassification = new Classification(existingClassification.getId(), null,
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("message")).isNotNull();
        }

        @Test
        @DisplayName("Update classification with null id")
        void updateClassificationWithNullId() throws Exception {
                Classification updatedClassification = new Classification(null, "updated classification",
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/-1",
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("message")).isNotNull();
        }

        @SuppressWarnings("null")
        @Test
        @DisplayName("Update classification by adding waste")
        void updateClassificationByAddingWaste() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                Classification updatedClassification = new Classification(existingClassification.getId(),
                                "updated classification",
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                updatedClassification.getWaste().addAll(Set.of(new Waste(null, "test waste 3", "d", null, null),
                                new Waste(null, "test waste 4", "d", null, null)));
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                ResponseEntity<GetClassificationDto> getResponse = restTemplate.exchange(
                                baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.GET, null, GetClassificationDto.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(getResponse.getBody()).isNotNull();
                assertThat(getResponse.getBody().getWaste().size()).isEqualTo(2);
        }

        @SuppressWarnings("null")
        @Test
        @DisplayName("Update classification by removing waste")
        void updateClassificationByRemovingWaste() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                Classification updatedClassification = new Classification(existingClassification.getId(),
                                "updated classification",
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                ResponseEntity<GetClassificationDto> getResponse = restTemplate.exchange(
                                baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.GET, null, GetClassificationDto.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(getResponse.getBody()).isNotNull();
                assertThat(getResponse.getBody().getWaste().size()).isEqualTo(0);
        }

        @Test
        @DisplayName("Update classification that does not exist")
        void updateClassificationThatDoesNotExist() throws Exception {
                Classification updatedClassification = new Classification(null, "updated classification",
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + UUID.randomUUID(),
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(response.getBody()).isNotNull();

                JsonNode json = mapper.readTree(response.getBody());

                assertThat(json.get("message")).isNotNull();

        }

        @SuppressWarnings("null")
        @Test
        @DisplayName("Update classification with null waste")
        void updateClassificationWithNullWaste() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                Classification updatedClassification = new Classification(existingClassification.getId(),
                                "updated classification",
                                "updated description", new HashSet<>(Set.of("method 1", "method 2")), new HashSet<>());
                HttpEntity<Classification> request = new HttpEntity<>(updatedClassification);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.PUT, request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                ResponseEntity<GetClassificationDto> getResponse = restTemplate.exchange(
                                baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.GET, null, GetClassificationDto.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(getResponse.getBody()).isNotNull();
                assertThat(getResponse.getBody().getWaste().size()).isEqualTo(0);
        }

        @Test
        @DisplayName("Delete classification")
        void deleteClassification() throws Exception {
                GetClassificationDto existingClassification = classificationService.getClassification(0, 10, "name,asc")
                                .getContent()
                                .get(0);
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.DELETE, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                ResponseEntity<String> getResponse = restTemplate.exchange(
                                baseUrl + "/" + existingClassification.getId(),
                                HttpMethod.GET, null, String.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("Delete classification that does not exist")
        void deleteClassificationThatDoesNotExist() throws Exception {
                ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/" + UUID.randomUUID(),
                                HttpMethod.DELETE, null, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
}
