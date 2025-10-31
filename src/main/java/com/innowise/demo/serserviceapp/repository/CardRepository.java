package com.innowise.demo.serserviceapp.repository;

import com.innowise.demo.serserviceapp.model.CardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<CardInfo, Long> {
    List<CardInfo> findByUserId(Long userId);

    @Query("SELECT c FROM CardInfo c WHERE c.user.id = :userId")
    List<CardInfo> findCardsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM card_info WHERE user_id = :userId", nativeQuery = true)
    List<CardInfo> findCardsByUserIdNative(@Param("userId") Long userId);

    Page<CardInfo> findAll(Pageable pageable);
}
