package core.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import core.dto.request.RegisterRequest;
import core.dto.response.UserResponse;
import core.entity.User;
import core.helpers.BCrypt;
import core.helpers.ValidationHelper;
import core.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationHelper validationHelper;
    
    @Transactional
    public User create(RegisterRequest registerRequest) {
        validationHelper.validate(registerRequest);

        User userTemp = userRepository.findOneByEmail(registerRequest.getEmail());

        if (userTemp != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);
        return user;
    }

    public UserResponse getCurrent(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
