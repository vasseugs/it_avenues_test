package com.itavenues.repository;

import com.itavenues.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByEmail(String email);
}
