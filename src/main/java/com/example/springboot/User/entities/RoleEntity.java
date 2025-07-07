package com.example.springboot.User.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<UserEntity> users = new HashSet<>();

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                '}';
    }

    // constructors

    public RoleEntity() {}

    public RoleEntity(String roleName, Set<UserEntity> users) {
        this.roleName = roleName;
        this.users = users;
    }

    public RoleEntity(Long id, String roleName, Set<UserEntity> users) {
        this.id = id;
        this.roleName = roleName;
        this.users = users;
    }

    // getters ans setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

}