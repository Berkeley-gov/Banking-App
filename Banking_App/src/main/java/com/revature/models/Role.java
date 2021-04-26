package com.revature.models;

import java.util.Objects;

public class Role {
    private int id;
    private String roleName;

    // ******** Constructors ********
    /**
     * Default No-args constructor for Role object
     */
    public Role() {

    }

    /**
     * Parameterized constructor for Role object
     * @param id
     * @param roleName
     */
    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    // ******** Getters and Setter methods for Role data members ********
    /**
     * Retrieves the Role table primary key
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id for Role object
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the user's role in the company.
     * For example, The user could be an Admin, Customer, or Bank Employee
     * @return String
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set the User's role in the company/database
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // ******** Overridden Object super class functions ********
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId() == role.getId() && getRoleName().equals(role.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleName());
    }

    @Override
    public String toString() {
        return "\nRole {" +
                "id = " + id +
                ", roleName = '" + roleName + '\'' +
                '}';
    }
}
