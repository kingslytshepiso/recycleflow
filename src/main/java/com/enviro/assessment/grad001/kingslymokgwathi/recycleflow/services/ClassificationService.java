package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services;

import static com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.helpers.IsUUID.isUUID;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.exceptions.RecordNotFoundException;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.repositories.ClassificationRepository;

@Service
public class ClassificationService {

    private final ClassificationRepository classificationRepository;

    public ClassificationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    public Page<Classification> getClassification(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return classificationRepository.findAll(pageable);
    }

    public Classification getClassificationByIdOrName(String idOrName) {
        if (isUUID(idOrName)) {
            return classificationRepository.findById(UUID.fromString(idOrName))
                    .orElseThrow(() -> new RecordNotFoundException("Classification not found with UUID: " + idOrName));
        } else {
            return classificationRepository.findByName(idOrName)
                    .orElseThrow(() -> new RecordNotFoundException("Classification not found with Name: " + idOrName));
        }

    }

    public Page<Classification> searchClassification(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return classificationRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    public Classification createClassification(Classification classification) {
        Boolean exists = classificationRepository.existsByName(classification.getName());
        if (exists) {
            throw new RecordNotFoundException(
                    "Classification with name " + classification.getName() + " already exists");
        }

        return classificationRepository.save(classification);
    }

    public void deleteAllClassifications() {
        classificationRepository.deleteAll();
    }
}
