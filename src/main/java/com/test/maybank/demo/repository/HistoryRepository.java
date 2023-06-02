package com.test.maybank.demo.repository;

import com.test.maybank.demo.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity,Long> {
    HistoryEntity findById(long idHistory);
}
