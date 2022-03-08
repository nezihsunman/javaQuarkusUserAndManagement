package org.acme.dto;

public class UserDTO {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public UserDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }

}
