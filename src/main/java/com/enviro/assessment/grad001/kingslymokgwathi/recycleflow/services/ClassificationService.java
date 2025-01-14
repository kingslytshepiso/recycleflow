package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services;

import static com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.helpers.IsUUID.isUUID;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.dtos.GetClassificationDto;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.exceptions.DuplicateRecordException;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.exceptions.RecordNotFoundException;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.repositories.ClassificationRepository;

@Service
public class ClassificationService {

    private final ClassificationRepository classificationRepository;

    public ClassificationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    public Page<GetClassificationDto> getClassification(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Order order = Sort.Order.asc(sortParams[0]);
        if ("desc".equalsIgnoreCase(sortParams[1])) {
            order = Sort.Order.desc(sortParams[0]);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return classificationRepository.findAll(pageable).map(classification -> {
            return new GetClassificationDto(classification.getId(), classification.getName(),
                    classification.getDescription(),
                    classification.getImportantNotes(), classification.getWaste());
        });
    }

    public GetClassificationDto getClassificationByIdOrName(String idOrName) {
        if (isUUID(idOrName)) {
            Classification classification = classificationRepository.findById(UUID.fromString(idOrName))
                    .orElseThrow(() -> new RecordNotFoundException("Classification not found with UUID: " + idOrName));
            return new GetClassificationDto(classification.getId(), classification.getName(),
                    classification.getDescription(),
                    classification.getImportantNotes(), classification.getWaste());
        } else {
            Classification classification = classificationRepository.findByName(idOrName)
                    .orElseThrow(() -> new RecordNotFoundException("Classification not found with Name: " + idOrName));
            return new GetClassificationDto(classification.getId(), classification.getName(),
                    classification.getDescription(),
                    classification.getImportantNotes(), classification.getWaste());
        }

    }

    public Page<GetClassificationDto> searchClassification(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return classificationRepository.findByNameContainingIgnoreCase(query, pageable).map(classification -> {
            return new GetClassificationDto(classification.getId(), classification.getName(),
                    classification.getDescription(),
                    classification.getImportantNotes(), classification.getWaste());
        });
    }

    public Classification createClassification(Classification classification) {
        Boolean exists = classificationRepository.existsByName(classification.getName());
        if (exists) {
            throw new DuplicateRecordException(
                    "Classification with name " + classification.getName() + " already exists");
        }
        if (classification.getWaste() != null) {
            classification.getWaste().forEach(waste -> {
                waste.setClassification(classification);
            });
        }

        return classificationRepository.save(classification);
    }

    public void updateClassification(UUID id, Classification classification) {
        classificationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Classification with id " + id + " not found"));
        if (classification.getWaste() != null) {
            classification.getWaste().forEach(waste -> {
                waste.setClassification(classification);
            });
        }
        classificationRepository.save(classification);
    }

    public void deleteClassificationById(UUID id) {
        classificationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Classification with id " + id + " not found"));
        classificationRepository.deleteById(id);
    }

    public void deleteAllClassifications() {
        classificationRepository.deleteAll();
    }
}
