package core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import core.dto.request.LoginRequest;
import core.dto.response.ApiResponse;
import core.dto.response.TokenResponse;
import core.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(path = "/api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        ApiResponse<TokenResponse> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("Login success");
        response.setData(tokenResponse);
        
        return response;
    }
}
