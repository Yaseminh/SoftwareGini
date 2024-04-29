package com.example.easynotes.controller;

import com.example.easynotes.Dto.SoftwareSelectionDTORequest;
import com.example.easynotes.Dto.SoftwareSelectionDTOResponse;
import com.example.easynotes.exception.NotAuthorizedException;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Roles;
import com.example.easynotes.model.SoftwareSelectionHistory;
import com.example.easynotes.model.SoftwareSolution;
import com.example.easynotes.repository.SoftwareSelectionHistoryRepository;
import com.example.easynotes.repository.SoftwareSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class SoftwareSolutionController {
    @Autowired
    SoftwareSolutionRepository softwareSolutionRepository;

    @Autowired
    SoftwareSelectionHistoryRepository softwareSelectionHistoryRepository;
    @Autowired
    public EntityManager entityManager;


    @GetMapping("/softwaresolutions")
    public List<SoftwareSolution> getAllSoftwareSolutions() {

        return softwareSolutionRepository.findAll();
    }

    @PostMapping("/softwaresolutions")
    public SoftwareSolution createSoftwareSolution(@Valid @RequestBody SoftwareSolution softwareSolution) {
        return softwareSolutionRepository.save(softwareSolution);
    }

    @GetMapping("/softwaresolutions/{id}")
    public SoftwareSolution getSoftwareSolutionById(@PathVariable(value = "id") Long noteId) {
        return softwareSolutionRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Software Solution", "id", noteId));
    }

    @PutMapping("/softwaresolutions/{id}/{role}/{username}")
    public SoftwareSolution updateSoftwareSolution(@PathVariable(value = "id") Long noteId,
                                                   @PathVariable(value = "role") Roles role,
                                                   @PathVariable(value = "username") String username,
                                                   @Valid @RequestBody SoftwareSolution softwareSolutionDetails) throws NotAuthorizedException {

        if (role.equals(Roles.USER)) {
            throw new NotAuthorizedException(role.toString(), " id", noteId);
        }
        SoftwareSolution softwareSolution = softwareSolutionRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Software Solution", "id", noteId));

        if (role.equals(Roles.PROVIDER) && !softwareSolution.getName().equals(username)) {
            throw new NotAuthorizedException(role.toString(), " id", noteId);
        }

        softwareSolution.setDescription(softwareSolutionDetails.getDescription());
        softwareSolution.setName(softwareSolutionDetails.getName());
        softwareSolution.setProviderAnswer(softwareSolutionDetails.getProviderAnswer());
        softwareSolution.setProviderExplanation(softwareSolutionDetails.getProviderExplanation());
        softwareSolution.setWebsite(softwareSolutionDetails.getWebsite());

        SoftwareSolution updatedSoftwareSolution = softwareSolutionRepository.save(softwareSolution);
        return updatedSoftwareSolution;
    }

    @DeleteMapping("/softwaresolutions/{id}")
    public ResponseEntity<?> deleteSoftwareSolution(@PathVariable(value = "id") Long softwareSolutionId) {
        SoftwareSolution softwareSolution = softwareSolutionRepository.findById(softwareSolutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Software Solution", "id", softwareSolutionId));

        softwareSolutionRepository.delete(softwareSolution);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/softwareselection")
    public List<SoftwareSelectionDTOResponse> getSoftwareSelection(@Valid @RequestBody SoftwareSelectionDTORequest softwareSelectionDTORequest) throws SQLException, ExecutionException, InterruptedException {
        List<SoftwareSelectionDTOResponse> query = new ArrayList<>();

        SoftwareSelectionHistory softwareSelectionHistory = new SoftwareSelectionHistory();
        softwareSelectionHistory.setSolution(softwareSelectionDTORequest.getSolution());
        softwareSelectionHistory.setProblem(softwareSelectionDTORequest.getProblem());
        softwareSelectionHistory.setRequirements(Arrays.toString(softwareSelectionDTORequest.getRequirements()));

        softwareSelectionHistoryRepository.save(softwareSelectionHistory);

        for (String item : softwareSelectionDTORequest.getRequirements()) {
            query.addAll(
                    entityManager.createQuery("SELECT new com.example.easynotes.Dto.SoftwareSelectionDTOResponse(s.id , s.requirement,s.providerAnswer,s.providerExplanation ) FROM SoftwareSolution s WHERE s.requirement = :requirement", SoftwareSelectionDTOResponse.class)
                            .setParameter("requirement", item).getResultList());
        }
        return query;
    }
}
