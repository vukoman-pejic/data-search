package com.rbt.datasearch.repository;

import com.rbt.datasearch.entity.UsedVacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedVacationDaysRepository extends JpaRepository<UsedVacationEntity, Long> {
}
