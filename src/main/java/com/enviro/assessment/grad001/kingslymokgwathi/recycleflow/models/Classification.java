package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Classification {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private Set<String> importantNotes;
    @OneToMany(mappedBy = "classification")
    Set<WasteClassification> waste;
}
