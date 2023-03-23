package engine.service;

import engine.model.User;
import engine.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User registerUser(User user) {
        if(userRepository.findById(user.getEmail()).isEmpty()) {
            return userRepository.save(new User(
                    user.getEmail(),
                    encoder.encode(user.getPassword()),
                    "ROLE_USER"
            ));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
