package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.dtos;

import java.util.Set;
import java.util.UUID;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;

public class GetClassificationDto {
    private UUID id;
    private String name;
    private String description;
    private Set<String> importantNotes;
    private Set<Waste> waste;

    public GetClassificationDto(UUID id, String name, String description, Set<String> importantNotes,
            Set<Waste> waste) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.importantNotes = importantNotes;
        this.waste = waste;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getImportantNotes() {
        return importantNotes;
    }

    public Set<Waste> getWaste() {
        return waste;
    }
}
