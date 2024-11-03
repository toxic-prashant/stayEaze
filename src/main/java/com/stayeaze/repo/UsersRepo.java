package com.stayeaze.repo;

import com.stayeaze.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Long> {

  Optional<Users> findByEmail(String email);

  Users findByUsername(String username);
}
