package core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import core.dto.request.RegisterRequest;
import core.dto.response.ApiResponse;
import core.entity.User;
import core.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ApiResponse<User> register(@RequestBody RegisterRequest registerRequest) {
        User user = userService.create(registerRequest);

        ApiResponse<User> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("User created");
        response.setData(user);

        return response;
    }
}
