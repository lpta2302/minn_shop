package com.thienan.file_service.core.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.thienan.file_service.core.entity.FileInfo;
import com.thienan.file_service.core.repository.FileInfoRepository;
import com.thienan.file_service.handler.exceptions.common.EntityNotFoundByIDException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileInfoService {
    private final FileInfoRepository fileInfoRepository;

    public Long createAndSave(FileInfo fileInfo){
        return fileInfoRepository.save(fileInfo).getId();
    }

    public FileInfo findById(Long fileInfoId){
        return fileInfoRepository.findById(fileInfoId)
            .orElseThrow(()->new EntityNotFoundByIDException("File info", fileInfoId.toString()));
    }

    public List<FileInfo> findAllById(Set<Long> fileInfoIds){
        var fileInfos = fileInfoRepository.findAllById(fileInfoIds);
        if (fileInfoIds.size() != fileInfos.size()) {
            var ids = fileInfos.stream().map(FileInfo::getId).toList();
            fileInfoIds.forEach(
                id->{
                    if (!ids.contains(id)) {
                        throw new EntityNotFoundByIDException("File info", id.toString());
                    }
                }
            );
        }

        return fileInfos;
    }
}
