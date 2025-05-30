package com.thienan.file_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

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

    @Builder.Default
    private Map<String, String> foldersMap = new HashMap<>();

    public String getFolderName(String folderObject){
        return foldersMap.get(folderObject);
    }

    public void addFolder(String folderObject, String folderName){
        foldersMap.put(folderObject, folderName);
    }

    public void addFolders(Map<String, String> newFoldersMap){
        foldersMap.putAll(foldersMap);
    }
}
