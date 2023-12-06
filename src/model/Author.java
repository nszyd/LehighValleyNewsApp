package model;

import java.io.Serializable;

//Represents an Author entity, extending Entity and implementing Serializable.
public class Author extends Entity   implements Serializable {
    private String firstName; // Author's first name
    private String middleName; // Author's middle name
    private String lastName; // Author's last name
    private String email; // Author's email address
    private String company; // Company with which the author is associated

//Constructor to initialize an Author with their basic information.
    public Author(String firstName, String lastName, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
    }

    // Getter for retrieving author details
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }

    // Setters for setting author details
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setCompany(String company) { this.company = company; }



    // Override the description method from Entity
    @Override
    public String description() {
        return "This is an author named " + fullName();
    }
    
    //Additional Methods
    //Get full name
    public String fullName() {
        return this.firstName + " " + (this.middleName != null ? this.middleName + " " : "") + this.lastName;
    }
    //Check if the author works for a certain company
    public boolean isFromCompany(String company) {
        return this.company.equals(company);
    }
    //Get the initials of the Author
    public String getInitials() {
        String initials = "";
        if (firstName != null && !firstName.isEmpty()) {
            initials += firstName.charAt(0);
        }
        if (middleName != null && !middleName.isEmpty()) {
            initials += middleName.charAt(0);
        }
        if (lastName != null && !lastName.isEmpty()) {
            initials += lastName.charAt(0);
        }
        return initials.toUpperCase();
    }
    
}