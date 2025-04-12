package com.example.backendtaskmanager.Security.Auth;

import com.example.backendtaskmanager.Entity.Role;
import com.example.backendtaskmanager.Entity.User;
import com.example.backendtaskmanager.Repository.UserRepository;
import com.example.backendtaskmanager.Security.Jwt.JwtService;
import com.example.backendtaskmanager.Security.Payload.JwtResponse;
import com.example.backendtaskmanager.Security.Payload.LoginRequest;
import com.example.backendtaskmanager.Security.Payload.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.getToken(user);
        return JwtResponse.builder().token(jwtToken).build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.getToken(user);
        return JwtResponse.builder().token(jwtToken).build();
    }
}
