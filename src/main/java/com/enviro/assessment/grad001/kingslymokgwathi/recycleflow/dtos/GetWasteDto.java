package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.dtos;

import java.util.UUID;

public class GetWasteDto {
    private UUID id;
    private String name;
    private String description;
    private String disposalMethod;
    private String classification;

    public GetWasteDto(UUID id, String name, String description, String disposalMethod, String classification) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.disposalMethod = disposalMethod;
        this.classification = classification;
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

    public String getDisposalMethod() {
        return disposalMethod;
    }

    public String getClassification() {
        return classification;
    }
}
