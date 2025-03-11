package com.example.super_keys.service;

import com.example.super_keys.model.FunctionalDependency;
import com.example.super_keys.model.SuperKeyRequest;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SuperKeysService {

    public List<Set<String>> findAllSuperkeys(SuperKeyRequest request) {
        Set<String> allAttributes = new HashSet<>(request.getAttributes());
        List<FunctionalDependency> fds = request.getFunctionalDependencies();
        List<String> candidateKeys = request.getCandidateKeys();

        // If candidate keys are not provided, derive them from functional dependencies
        if (candidateKeys == null || candidateKeys.isEmpty()) {
            candidateKeys = findCandidateKeys(allAttributes, fds);
        }

        List<Set<String>> superkeys = new ArrayList<>();
        for (String candidateKey : candidateKeys) {
            Set<String> keySet = new HashSet<>(Arrays.asList(candidateKey.split(",")));
            superkeys.add(keySet);
            findSupersets(keySet, allAttributes, superkeys);
        }

        return new ArrayList<>(new HashSet<>(superkeys)); // Remove duplicates
    }

    private List<String> findCandidateKeys(Set<String> attributes, List<FunctionalDependency> fds) {
        List<Set<String>> powerSet = generatePowerSet(attributes);
        List<String> candidateKeys = new ArrayList<>();

        for (Set<String> subset : powerSet) {
            Set<String> closure = computeClosure(subset, fds);
            if (closure.equals(attributes) && isMinimal(candidateKeys, subset)) {
                candidateKeys.add(String.join(",", subset));
            }
        }

        return candidateKeys;
    }

    private void findSupersets(Set<String> baseSet, Set<String> allAttributes, List<Set<String>> results) {
        for (String attr : allAttributes) {
            if (!baseSet.contains(attr)) {
                Set<String> superset = new HashSet<>(baseSet);
                superset.add(attr);
                if (!results.contains(superset)) {
                    results.add(superset);
                    findSupersets(superset, allAttributes, results);
                }
            }
        }
    }

    private Set<String> computeClosure(Set<String> attributes, List<FunctionalDependency> fds) {
        Set<String> closure = new HashSet<>(attributes);
        boolean changed;
        do {
            changed = false;
            for (FunctionalDependency fd : fds) {
                if (closure.containsAll(fd.getLeftSide()) && !closure.containsAll(fd.getRightSide())) {
                    closure.addAll(fd.getRightSide());
                    changed = true;
                }
            }
        } while (changed);
        return closure;
    }

    private List<Set<String>> generatePowerSet(Set<String> originalSet) {
        List<Set<String>> powerSet = new ArrayList<>();
        List<String> list = new ArrayList<>(originalSet);
        int n = list.size();

        for (int i = 0; i < (1 << n); i++) {
            Set<String> subset = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    subset.add(list.get(j));
                }
            }
            powerSet.add(subset);
        }

        return powerSet;
    }

    private boolean isMinimal(List<String> candidateKeys, Set<String> subset) {
        for (String key : candidateKeys) {
            Set<String> keySet = new HashSet<>(Arrays.asList(key.split(",")));
            if (subset.containsAll(keySet)) {
                return false;
            }
        }
        return true;
    }
}
