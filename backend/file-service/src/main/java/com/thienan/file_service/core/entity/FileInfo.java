package com.thienan.file_service.core.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thienan.file_service.core.enumeration.FileAccess;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
public class FileInfo{
    @Id
    @GeneratedValue
    @Schema(accessMode=READ_ONLY)
    private Long id;
    
    @Version
    @JsonIgnore
    private int version;

    @CreatedDate
    @Column(updatable = false, nullable=false)
    @Schema(accessMode=READ_ONLY)
    private LocalDate createdDate;

    @LastModifiedDate
    @Schema(accessMode=READ_ONLY)
    private LocalDate modifiedDate;

    private String fileName;
    
    private String key;
    
    @Schema(accessMode=READ_ONLY)
    private Long size;
    
    @Schema(accessMode=READ_ONLY)
    private String mimeType;

    @Schema(accessMode=READ_ONLY)
    private String objectUrl;

    @Schema(accessMode=READ_ONLY)
    private FileAccess fileAccess;
}
