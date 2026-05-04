package com.cornerstone.dto;

import java.util.ArrayList;
import java.util.List;

public class ManagerDto {

    private Long id;
    private String name;
    private List<Long> unitIds = new ArrayList<>();
    private List<String> unitNumbers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Long> getUnitIds() {
        return unitIds;
    }

    public List<String> getUnitNumbers() {
        return unitNumbers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitIds(List<Long> unitIds) {
        this.unitIds = unitIds;
    }

    public void setUnitNumbers(List<String> unitNumbers) {
        this.unitNumbers = unitNumbers;
    }
}