package com.thienan.category_service.core.category.entity;

import static jakarta.persistence.EnumType.STRING;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    
    @Version
    private int version;

    @Size(max = 100, message = "code length can't be more than 100 characters")
    private String code;
    
    @NotBlank(message = "category name can't be null or blank")
    @Size(max = 200, message = "category name length can't be more than 200 characters")
    private String name;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @Default
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategories = new ArrayList<>();

    @Column(updatable = false)
    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @Enumerated(STRING)
    private CategoryStatus status;

    public Category(Long id, String code, String name, Long parentCategoryId, List<Category> subCategories){
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentCategory = Category.builder().id(parentCategoryId).build();
        this.subCategories = subCategories;
    }

    public void setSubCategories(List<Category> categories){
        if (this.subCategories == null) {
            this.subCategories = new ArrayList<>();
        } else{
            this.subCategories.clear();
        }

        categories.forEach(category->
            this.subCategories.add(category));
    }
}
