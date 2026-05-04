package com.cornerstone.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "managers")
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "manager_units",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    private List<UnitEntity> units = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public ManagerEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ManagerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<UnitEntity> getUnits() {
        return units;
    }

    public ManagerEntity setUnits(List<UnitEntity> units) {
        this.units = units;
        return this;
    }

    // getters and setters
}