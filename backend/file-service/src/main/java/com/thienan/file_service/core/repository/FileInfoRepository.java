package com.thienan.file_service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thienan.file_service.core.entity.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>{
    
}
