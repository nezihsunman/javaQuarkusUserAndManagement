package org.acme.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LoginDto {
    public String email;
    public String password;
    public LoginDto() {

    }
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
