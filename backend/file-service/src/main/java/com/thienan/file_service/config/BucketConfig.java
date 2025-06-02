package com.thienan.file_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.thienan.file_service.core.enumeration.ObjectTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BucketConfig {
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Builder.Default
    private Map<ObjectTypes, String> foldersMap = new HashMap<>();

    public String getFolderName(ObjectTypes folderObject){
        return foldersMap.get(folderObject);
    }

    public void addFolder(ObjectTypes folderObject, String folderName){
        foldersMap.put(folderObject, folderName);
    }

    public void addFolders(Map<ObjectTypes, String> newFoldersMap){
        foldersMap.putAll(foldersMap);
    }
}
