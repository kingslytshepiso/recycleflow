package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WasteClassification {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "waste_id")
    Waste waste;

    @ManyToOne
    @JoinColumn(name = "classification_id")
    Classification classification;

    private String disposalMethod;
}
