package com.cornerstone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column(name = "full_name")
    private String fullName;

    private String role;
    private Boolean enabled;

    public Long getId() { return id; }
    public AppUserEntity setId(Long id) { this.id = id; return this; }

    public String getUsername() { return username; }
    public AppUserEntity setUsername(String username) { this.username = username; return this; }

    public String getPassword() { return password; }
    public AppUserEntity setPassword(String password) { this.password = password; return this; }

    public String getFullName() { return fullName; }
    public AppUserEntity setFullName(String fullName) { this.fullName = fullName; return this; }

    public String getRole() { return role; }
    public AppUserEntity setRole(String role) { this.role = role; return this; }

    public Boolean getEnabled() { return enabled; }
    public AppUserEntity setEnabled(Boolean enabled) { this.enabled = enabled; return this; }
}