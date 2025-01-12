package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classification {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private Set<String> importantNotes;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "waste_classification", joinColumns = @JoinColumn(name = "classification_id"), inverseJoinColumns = @JoinColumn(name = "waste_id"))
    Set<Waste> waste = new HashSet<Waste>();
}
