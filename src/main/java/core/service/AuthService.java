package core.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import core.dto.request.LoginRequest;
import core.dto.response.TokenResponse;
import core.entity.User;
import core.helpers.BCrypt;
import core.helpers.TimeHelper;
import core.helpers.ValidationHelper;
import core.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private TimeHelper timeHelper;

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        validationHelper.validate(loginRequest);

        User user = userRepository.findOneByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password is incorrect");
        }

        if (BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(timeHelper.next30Day());

            userRepository.save(user);

            return TokenResponse.builder().token(user.getToken()).tokenExpiredAt(user.getTokenExpiredAt()).build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password is incorrect");
        }
    }
}
