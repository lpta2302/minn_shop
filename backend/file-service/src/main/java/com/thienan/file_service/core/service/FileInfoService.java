package com.thienan.file_service.core.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.thienan.file_service.core.dto.FileInfoRequest;
import com.thienan.file_service.core.entity.FileInfo;
import com.thienan.file_service.core.enumeration.FileAccess;
import com.thienan.file_service.core.enumeration.ObjectTypes;
import com.thienan.file_service.core.repository.FileInfoRepository;
import com.thienan.file_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.file_service.kafka.producer.FileInfoProducer;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

@Service
@RequiredArgsConstructor
public class FileInfoService {

    private final AWSObjectService awsObjectService;
    private final FileInfoRepository fileInfoRepository;
    private final FileInfoProducer fileInfoProducer;

    public Long createAndSave(FileInfoRequest fileInfoRequest){
        ObjectTypes objectType = fileInfoRequest.objectType();
        String key = fileInfoRequest.key();
        HeadObjectResponse headObject = awsObjectService.getHeadObject(key);
        FileAccess fileAccess = awsObjectService.getFileAccess(key);

        FileInfo fileInfo = 
            FileInfo
            .builder()
            .fileName(key)
            .key(key)
            .mimeType(headObject.contentType())
            .size(headObject.contentLength())
            .fileAccess(fileAccess)
            .build();

        if (!FileAccess.PRIVATE.equals(fileAccess)) {
            String objectUrl = awsObjectService.createUrl(key);
            fileInfo.setObjectUrl(objectUrl);
        }

        fileInfoRepository.save(fileInfo);
        fileInfoProducer.sendCreatedFileInfoMessage(fileInfo, objectType);
        return fileInfo.getId();
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
