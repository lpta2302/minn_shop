package com.thienan.file_service.core.validator;

import static java.lang.String.format;

import org.springframework.stereotype.Component;

import com.thienan.file_service.core.repository.FileInfoRepository;
import com.thienan.file_service.handler.exceptions.common.ExistedS3FileKeyException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileInfoValidator {
    private final FileInfoRepository fileInfoRepository;

    public void validateUniqueKey(String key){
        boolean isExisted = fileInfoRepository.existsByKey(key);
        if (isExisted) {
            throw new ExistedS3FileKeyException(format("Existed key: %s", key));
        }
    }
}
