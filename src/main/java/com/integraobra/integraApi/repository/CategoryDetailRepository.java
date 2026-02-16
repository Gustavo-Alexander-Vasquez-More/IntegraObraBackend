package com.integraobra.integraApi.repository;

import com.integraobra.integraApi.model.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
    void deleteByCategoryId(Long categoryId);
    boolean existsByCategoryIdAndProductId(Long categoryId, Long productId);
}
