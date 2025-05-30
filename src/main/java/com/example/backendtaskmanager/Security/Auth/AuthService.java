package com.example.backendtaskmanager.Security.Auth;

import com.example.backendtaskmanager.Security.Payload.JwtResponse;
import com.example.backendtaskmanager.Security.Payload.LoginRequest;
import com.example.backendtaskmanager.Security.Payload.RegisterRequest;

public interface AuthService {

    JwtResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);
}
