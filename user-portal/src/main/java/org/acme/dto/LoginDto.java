package org.acme.dto;

public class LoginDto {
    public String email;
    public String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
