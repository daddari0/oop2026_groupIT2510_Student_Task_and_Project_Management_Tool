package repositories;

import Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository{
        User create(User user);
        Optional<User> findById(Long id);
        List<User> findAll();
        Optional<User> findByEmail(String email);
    }
