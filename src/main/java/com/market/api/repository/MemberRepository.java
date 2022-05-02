package com.market.api.repository;

import com.market.api.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmailAndDeletedTimeIsNull(String email);
    Optional<MemberEntity> findByIdAndDeletedTimeIsNull(Long id);
}
