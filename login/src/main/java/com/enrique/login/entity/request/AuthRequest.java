package com.enrique.login.entity.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}