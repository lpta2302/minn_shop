package com.thienan.product_service.core.weight_type.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.weight_type.entity.WeightType;
import static com.thienan.product_service.core.weight_type.enums.WeightTypeStatus.DELETED;
import com.thienan.product_service.core.weight_type.repository.WeightTypeRepository;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundException;
import com.thienan.product_service.handler.exceptions.weight_type.InvalidWeightRangeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeightTypeService {
    private final WeightTypeRepository weightTypeRepository;

    public Long createAndSave(WeightType weightType){
        if (weightType.getMin() > weightType.getMax()) {
            throw new InvalidWeightRangeException(weightType.getMin(), weightType.getMax());
        }
        return weightTypeRepository.save(weightType).getId();
    }

    public Long updateAndSave(Long id, WeightType weightType) {
        var updatingWeightType = findById(id);
        if (weightType.getMin() > weightType.getMax()) {
            throw new InvalidWeightRangeException(weightType.getMin(), weightType.getMax());
        }

        if (!weightType.getCode().equals(updatingWeightType.getCode())) {
            updatingWeightType.setCode(weightType.getCode());
        }

        updatingWeightType.setMin(weightType.getMin());
        updatingWeightType.setMax(weightType.getMax());
        updatingWeightType.setName(weightType.getName());
        updatingWeightType.setStatus(weightType.getStatus());

        return weightTypeRepository.save(weightType).getId();
    }

    public Long softDeleteById(Long id){
        var updatingWeightType = findById(id);
        updatingWeightType.setStatus(DELETED);

        return weightTypeRepository.save(updatingWeightType).getId();
    }

    public void deleteById(Long id){
        weightTypeRepository.deleteById(id);
    }

    public WeightType findById(Long id){
        return weightTypeRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Weight type", id));
    }

    public PageResponse<WeightType> findAll(Pageable pageable){
        var pageResult = weightTypeRepository.findAll(pageable);
        return PageResponse.fromPage(pageResult);
    }

    public PageResponse<WeightType> search(
        Pageable pageable, 
        String name, 
        String code,
        Integer min, 
        Integer max){
        var pageResult = weightTypeRepository.search(pageable, name, code, min, max);
        return PageResponse.fromPage(pageResult);
    }
}
