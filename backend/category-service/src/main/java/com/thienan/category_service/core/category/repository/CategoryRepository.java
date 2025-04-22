package com.thienan.category_service.core.category.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thienan.category_service.core.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    @Query(
        """
            select c
            from Category c
            left join fetch c.subCategories
            where c.id = :id     
        """
    )
    Optional<Category> findWithSubCategoriesById(Long id);

    @Query("""
           select c
           from Category c
           left join fetch c.subCategories
           where c.status = "ACTIVE"
        """)
    Page<Category> findAllDisplayWithFullDetail(Pageable pageable);

    @Query("""
        select c
        from Category c
        where (:code = null or code like :code%) or 
            (:name is null or name like :name%)
        """)
    Page<Category> search(Pageable pageable, String code, String name);

    @Query("""
        select c
        from Category c
        where c.parentCategory.id = :parentId
        """)
    Page<Category> findAllSubCategories(Pageable pageable, Long parentId);
   
}
