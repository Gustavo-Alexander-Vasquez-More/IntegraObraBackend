package com.integraobra.integraApi.repository;

import com.integraobra.integraApi.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {
    // Use default findById from JpaRepository; locking is handled in services using EntityManager.

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Counter c SET c.sequence = c.sequence + 1 WHERE c.id = :id")
    int incrementSequence(@Param("id") Long id);
}
