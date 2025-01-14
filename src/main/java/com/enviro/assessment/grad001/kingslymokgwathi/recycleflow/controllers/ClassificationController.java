package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.dtos.GetClassificationDto;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.ClassificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/classifications")
public class ClassificationController {

    private final ClassificationService classificationService;

    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @GetMapping
    public ResponseEntity<Page<GetClassificationDto>> getClassifications(@RequestParam(defaultValue = "0") int page, // Default
                                                                                                                     // page
            @RequestParam(defaultValue = "10") int size, // Default size
            @RequestParam(defaultValue = "name,asc") String sort) {
        return ResponseEntity.ok(classificationService.getClassification(page, size, sort));
    }

    @GetMapping("/{idOrName}")
    public ResponseEntity<GetClassificationDto> getClassificationById(@PathVariable String idOrName) {
        return ResponseEntity.ok(classificationService.getClassificationByIdOrName(idOrName));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GetClassificationDto>> searchClassification(@RequestParam String query,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(classificationService.searchClassification(query, page, size));
    }

    @PostMapping
    public ResponseEntity<Void> createClassification(
            @Valid @RequestBody Classification entity, UriComponentsBuilder ucb) {
        Classification saveClass = classificationService.createClassification(entity);
        URI location = ucb.path("classifications/{id}").buildAndExpand(saveClass.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClassification(@PathVariable UUID id,
            @Valid @RequestBody Classification entity) {
        classificationService.updateClassification(id, entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassification(@PathVariable UUID id) {
        classificationService.deleteClassificationById(id);
        return ResponseEntity.noContent().build();
    }

}
