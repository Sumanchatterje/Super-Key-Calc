package com.example.super_keys.model;

import java.util.Set;

public class FunctionalDependency {
    private Set<String> leftSide;
    private Set<String> rightSide;

    public Set<String> getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(Set<String> leftSide) {
        this.leftSide = leftSide;
    }

    public Set<String> getRightSide() {
        return rightSide;
    }

    public void setRightSide(Set<String> rightSide) {
        this.rightSide = rightSide;
    }
}
