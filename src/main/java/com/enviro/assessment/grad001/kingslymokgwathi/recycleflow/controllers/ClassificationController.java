package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.ClassificationService;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/classifications")
public class ClassificationController {

    private final ClassificationService classificationService;

    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @GetMapping
    public ResponseEntity<Page<Classification>> getClassifications(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(classificationService.getClassification(page, size));
    }

    @PostMapping
    public ResponseEntity<Void> createClassification(@RequestBody Classification entity, UriComponentsBuilder ucb) {
        Classification saveClass = classificationService.createClassification(entity);
        URI location = ucb.path("classifications/{id}").buildAndExpand(saveClass.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
