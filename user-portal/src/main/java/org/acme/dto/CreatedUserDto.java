package org.acme.dto;

public class CreatedUserDto
{
    public Long userId;
    public String username;

    public CreatedUserDto(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}