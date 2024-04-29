package com.example.easynotes.repository;

import com.example.easynotes.model.SoftwareSelectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareSelectionHistoryRepository extends JpaRepository<SoftwareSelectionHistory, Long> {
}
