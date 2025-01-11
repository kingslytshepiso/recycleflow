package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.WasteService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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

}
