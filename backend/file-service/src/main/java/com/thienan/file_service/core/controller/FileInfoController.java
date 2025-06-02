package com.thienan.file_service.core.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.file_service.core.dto.FileInfoRequest;
import com.thienan.file_service.core.entity.FileInfo;
import com.thienan.file_service.core.service.FileInfoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/file-infos")
@RequiredArgsConstructor
public class FileInfoController {
    private final FileInfoService fileInfoService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid FileInfoRequest fileInfo) {
        return ResponseEntity.ok(fileInfoService.createAndSave(fileInfo));
    }

    @GetMapping("/{fileInfoId}")
    public ResponseEntity<FileInfo> findById(
        @PathVariable Long fileInfoId) {
        return ResponseEntity.ok(fileInfoService.findById(fileInfoId));
    }

    @GetMapping("/id")
    public ResponseEntity<List<FileInfo>> findAllById(
        @RequestBody Set<Long> fileInfoIds) {
        return ResponseEntity.ok(fileInfoService.findAllById(fileInfoIds));
    }
    
}
