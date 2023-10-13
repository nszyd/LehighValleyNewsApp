package model;

public class Author {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String company;

    public Author(String firstName, String lastName, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setCompany(String company) { this.company = company; }
    
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