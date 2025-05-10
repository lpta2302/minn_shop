package com.thienan.product_service.core.weight_type.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.weight_type.entity.WeightType;
import com.thienan.product_service.core.weight_type.repository.WeightTypeRepository;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.product_service.handler.exceptions.weight_type.InvalidWeightRangeException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeightTypeService {
    private final WeightTypeRepository weightTypeRepository;

    public Long createAndSave(WeightType weightType){
        if (weightType.getMinWeight() > weightType.getMaxWeight()) {
            throw new InvalidWeightRangeException(weightType.getMinWeight(), weightType.getMaxWeight());
        }
        return weightTypeRepository.save(weightType).getId();
    }

    public Long updateAndSave(Long id, WeightType weightType) {
        var updatingWeightType = findById(id);
        if (weightType.getMinWeight() > weightType.getMaxWeight()) {
            throw new InvalidWeightRangeException(weightType.getMinWeight(), weightType.getMaxWeight());
        }

        if (!weightType.getCode().equals(updatingWeightType.getCode())) {
            updatingWeightType.setCode(weightType.getCode());
        }

        updatingWeightType.setMinWeight(weightType.getMinWeight());
        updatingWeightType.setMaxWeight(weightType.getMaxWeight());
        updatingWeightType.setName(weightType.getName());
        updatingWeightType.setStatus(weightType.getStatus());

        return weightTypeRepository.save(updatingWeightType).getId();
    }

    public void softDeleteById(Long id){
        weightTypeRepository.deleteById(id);
    }

    public Long recovery(Long id){
        return weightTypeRepository.recovery(id).getId();
    }

    @Transactional
    public void hardDeleteById(Long id){
        weightTypeRepository.hardDeleteById(id);
    }

    public WeightType findById(Long id){
        return weightTypeRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundByIDException("Weight type", id.toString()));
    }

    public List<WeightType> findAllById(List<Long> ids){
        return weightTypeRepository.findAllById(ids);
    }

    public List<WeightType> findAllById(Set<Long> ids){
        return weightTypeRepository.findAllById(ids);
    }

    public PageResponse<WeightType> findAll(Pageable pageable){
        var pageResult = weightTypeRepository.findAll(pageable);
        return PageResponse.fromPage(pageResult);
    }

    public PageResponse<WeightType> findAllDeleted(Pageable pageable){
        var pageResult = weightTypeRepository.findAllDeleted(pageable);
        return PageResponse.fromPage(pageResult);
    }

    public PageResponse<WeightType> search(
        Pageable pageable, 
        String name, 
        String code,
        Integer minWeight, 
        Integer maxWeight){
        var pageResult = weightTypeRepository.search(pageable, name, code, minWeight, maxWeight);
        return PageResponse.fromPage(pageResult);
    }
}
