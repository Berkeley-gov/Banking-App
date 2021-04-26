package com.revature.models;

import java.util.Objects;

public class User {
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role userRole;

    // ******** Constructors ********
    /**
     * Default No-args constructor
     */
    public User() {

    }

    /**
     * Parameterized constructor for User objects
     * @param id
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param userRole
     */
    public User(int id, String userName, String password, String firstName, String lastName, String email, Role userRole) {
        super();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
    }

    /**
     * Parameterized constructor for User objects that don't require an id for instantiation
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param userRole
     */
    public User(String userName, String password, String firstName, String lastName, String email, Role userRole) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
    }

    // ******** Getters and Setters for User data members ********
    /**
     * Retrieves the user's primary key from user table
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets a primary key to a User object
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the User's username
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the a username to a User Object
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the User's password
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a password to a User object
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the User's first name
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a first name to a User object
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the User's last name
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a last name to a User object
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the User's email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets an email to a User object
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves a User's role to the company
     * Example: Admin, Employee, or Customer
     * @return Role
     */
    public Role getUserRole() {
        return userRole;
    }

    /**
     * Sets a Role to a User object
     * @param userRole
     */
    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    // ******** Overridden Object methods for User functionality ********
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getUserName().equals(user.getUserName()) && getPassword().equals(user.getPassword()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getEmail().equals(user.getEmail()) && getUserRole().equals(user.getUserRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getPassword(), getFirstName(), getLastName(), getEmail(), getUserRole());
    }

    @Override
    public String toString() {
        return "\nUser {" +
                "id = " + id +
                ", userName = '" + userName + '\'' +
                ", password = '" + password + '\'' +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", email = '" + email + '\'' +
                ", userRole = " + userRole +
                '}';
    }
}
