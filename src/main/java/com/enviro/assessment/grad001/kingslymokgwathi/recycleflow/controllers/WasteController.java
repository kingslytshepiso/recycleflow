package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.WasteService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/waste")
public class WasteController {
    private final WasteService wasteService;

    public WasteController(WasteService wasteService) {
        this.wasteService = wasteService;
    }

    @GetMapping
    public ResponseEntity<Page<Waste>> getWaste(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Waste> waste = wasteService.getWaste(page, size);
        return ResponseEntity.ok(waste);
    }

    @GetMapping("/{idOrName}")
    public ResponseEntity<Waste> getWasteById(@PathVariable String idOrName) {
        return ResponseEntity.ok(wasteService.getWasteByIdOrName(idOrName));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Waste>> searchWaste(@RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Waste> waste = wasteService.searchWaste(query, page, size);
        return ResponseEntity.ok(waste);
    }

    @PostMapping
    public ResponseEntity<Void> createWaste(@Valid @RequestBody Waste entity, UriComponentsBuilder ucb) {
        Waste savedWaste = wasteService.createWaste(entity);
        URI wasteLocation = ucb.path("waste/{id}").buildAndExpand(savedWaste.getId()).toUri();
        return ResponseEntity.created(wasteLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWaste(@PathVariable UUID id, @RequestBody Waste entity) {
        wasteService.updateWaste(id, entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaste(@PathVariable UUID id) {
        wasteService.deleteWasteById(id);
        return ResponseEntity.noContent().build();
    }

}
