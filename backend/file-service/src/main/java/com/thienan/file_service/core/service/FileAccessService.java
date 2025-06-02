package com.thienan.file_service.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.thienan.file_service.core.enumeration.FileAccess;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

@Service
@RequiredArgsConstructor
public class FileAccessService {
    private final Map<FileAccess, String> fileAccessMap = new HashMap<>(){{
        put(FileAccess.PRIVATE, ObjectCannedACL.PRIVATE.toString());
        put(FileAccess.WRITE, ObjectCannedACL.PUBLIC_READ_WRITE.toString());
        put(FileAccess.READ, ObjectCannedACL.PUBLIC_READ.toString());
    }};
    
    public String getACL(FileAccess fileAccess){
        return fileAccessMap.getOrDefault(fileAccess, ObjectCannedACL.PRIVATE.toString());
    }
}
