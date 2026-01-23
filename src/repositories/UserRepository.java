package repositories;

import Entities.User;

import java.util.Optional;

public interface UserRepository extends  {
    Optional<User> findById(int id);
}