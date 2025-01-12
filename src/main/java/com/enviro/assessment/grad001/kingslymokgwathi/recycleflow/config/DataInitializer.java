package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services.ClassificationService;

@Configuration
public class DataInitializer implements ApplicationRunner {
    private final ClassificationService classificationService;

    public DataInitializer(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    public void run(ApplicationArguments args) {
        Waste waste1 = new Waste(null, "Plastic Bottles",
                "Used plastic beverage containers typically made from PET material.",
                "Rinse the bottles to remove any residue, remove caps, and place them in your blue recycling bin.",
                null);
        Waste waste2 = new Waste(null, "Food Scraps",
                "Leftover food waste from meals, including fruit peels, vegetable scraps, and bread.",
                "Collect food scraps in a compostable bag or bin and deposit them in a green compost bin or home composting system.",
                null);
        Classification recycle = new Classification(null, "Recycle",
                "Different materials (e.g., plastics, glass, metal, paper) are sorted, cleaned, and reprocessed into raw materials for new products.",
                new HashSet<>(Set.of("Contamination (e.g., food residue) can make recyclable materials unusable.",
                        "Certain plastics (like #3 PVC or #7 mixed plastics) might not be accepted in all regions.")),
                new HashSet<>(Set.of(waste1)));
        Classification compost = new Classification(null, "Compost",
                "Organic materials (e.g., food scraps, yard waste) are decomposed into nutrient-rich soil.",
                new HashSet<>(Set.of("Sent to industrial composting facilities or backyard composters.",
                        "Decomposes into nutrient-rich soil, useful for gardening or agriculture.")),
                new HashSet<>(Set.of(waste2)));
        classificationService.createClassification(recycle);
        classificationService.createClassification(compost);
    }
}
