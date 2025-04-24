package com.thienan.product_service.common;

import java.time.LocalDate;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@SoftDelete(columnName="deleted", converter=TrueFalseConverter.class)
public abstract class BaseEntity {
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

    @Column(name = "deleted", insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;
}
