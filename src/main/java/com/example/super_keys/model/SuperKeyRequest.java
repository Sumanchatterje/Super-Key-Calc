package com.example.super_keys.model;

import java.util.List;
import java.util.Set;

public class SuperKeyRequest {
    private Set<String> attributes;
    private List<String> candidateKeys;
    private List<FunctionalDependency> functionalDependencies;

    public Set<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<String> attributes) {
        this.attributes = attributes;
    }

    public List<String> getCandidateKeys() {
        return candidateKeys;
    }

    public void setCandidateKeys(List<String> candidateKeys) {
        this.candidateKeys = candidateKeys;
    }

    public List<FunctionalDependency> getFunctionalDependencies() {
        return functionalDependencies;
    }

    public void setFunctionalDependencies(List<FunctionalDependency> functionalDependencies) {
        this.functionalDependencies = functionalDependencies;
    }
}
