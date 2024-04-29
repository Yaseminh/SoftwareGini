package com.example.easynotes.Dto;

public class SoftwareSelectionDTOResponse {
    private Long softwareSolutionId;
    private String requirement;
    private Boolean providerAnswer;
    private String providerExplanation;

    public SoftwareSelectionDTOResponse() {
    }

    public SoftwareSelectionDTOResponse(Long softwareSolutionId, String requirement, Boolean providerAnswer, String providerExplanation) {
        this.softwareSolutionId = softwareSolutionId;
        this.requirement = requirement;
        this.providerAnswer = providerAnswer;
        this.providerExplanation = providerExplanation;
    }

    public Long getSoftwareSolutionId() {
        return softwareSolutionId;
    }

    public void setSoftwareSolutionId(Long softwareSolutionId) {
        this.softwareSolutionId = softwareSolutionId;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Boolean getProviderAnswer() {
        return providerAnswer;
    }

    public void setProviderAnswer(Boolean providerAnswer) {
        this.providerAnswer = providerAnswer;
    }

    public String getProviderExplanation() {
        return providerExplanation;
    }

    public void setProviderExplanation(String providerExplanation) {
        this.providerExplanation = providerExplanation;
    }
}
