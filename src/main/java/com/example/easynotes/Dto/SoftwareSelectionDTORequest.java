package com.example.easynotes.Dto;

public class SoftwareSelectionDTORequest {

    private String problem;
    private String solution;

    private String[] requirements;

    public SoftwareSelectionDTORequest(String problem, String solution, String[] requirements) {
        this.problem = problem;
        this.solution = solution;
        this.requirements = requirements;
    }

    public String getProblem() {
        return problem;
    }

    public String getSolution() {
        return solution;
    }

    public String[] getRequirements() {
        return requirements;
    }
}
