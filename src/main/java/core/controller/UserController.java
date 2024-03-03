package core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import core.dto.request.RegisterRequest;
import core.dto.response.ApiResponse;
import core.dto.response.UserResponse;
import core.entity.User;
import core.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



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

    @GetMapping(path = "/api/users/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserResponse> show(User user) {
        UserResponse current = userService.getCurrent(user);

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("User retrieved");
        response.setData(current);

        return response; 
    }
}
