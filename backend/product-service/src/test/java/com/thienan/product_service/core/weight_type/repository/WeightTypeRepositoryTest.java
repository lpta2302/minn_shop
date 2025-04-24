package com.thienan.product_service.core.weight_type.repository;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.thienan.product_service.core.weight_type.entity.WeightType;
import static com.thienan.product_service.core.weight_type.enums.WeightTypeStatus.ACTIVE;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class WeightTypeRepositoryTest {

    @Autowired
    private WeightTypeRepository weightTypeRepository;

    private WeightType exampleWeightType;

    @BeforeEach
    public void setUp(){
        exampleWeightType = 
            WeightType.builder()
            .code("WT1")
            .name("SMALL")
            .minWeight(1)
            .maxWeight(20)
            .status(ACTIVE)
            .createdDate(LocalDate.now())
            .build();
    }

    @Test
    public void testSearchByName(){
        var savedWeightType = weightTypeRepository.save(exampleWeightType);
        var pageable = PageRequest.of(0, 10);

        Page<WeightType> result = weightTypeRepository.search(
            pageable, exampleWeightType.getName(), null, null, null);

        assertEquals(1, result.getTotalElements());
        assertEquals(savedWeightType.getId(), result.getContent().get(0).getId());
    }

    @Test
    public void testSearchByCode(){
        var savedWeightType = weightTypeRepository.save(exampleWeightType);
        var pageable = PageRequest.of(0, 10);

        Page<WeightType> result = weightTypeRepository.search(
            pageable, null, exampleWeightType.getCode(), null, null);

        assertEquals(1, result.getTotalElements());
        assertEquals(savedWeightType.getId(), result.getContent().get(0).getId());
    }
}
