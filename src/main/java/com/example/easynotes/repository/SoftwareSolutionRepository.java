package com.example.easynotes.repository;

import com.example.easynotes.model.SoftwareSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by meteatbas on 12/06/24.
 */

@Repository
public interface SoftwareSolutionRepository extends JpaRepository<SoftwareSolution, Long> {

}
