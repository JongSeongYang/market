package com.market.api.repository;

import com.market.api.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByIdAndDeletedTimeIsNull(Long id);
    Optional<ProductEntity> findByNameAndDeletedTimeIsNull(String name);

    Page<ProductEntity> findAllByDeletedTimeIsNull(Pageable pageable);
}
