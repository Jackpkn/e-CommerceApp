package org.example.ecommerceapp.Features.User.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.example.ecommerceapp.Features.User.Entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String username);
}