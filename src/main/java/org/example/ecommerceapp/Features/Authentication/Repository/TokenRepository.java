package org.example.ecommerceapp.Features.Authentication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.example.ecommerceapp.Features.Authentication.DTOs.LoginResponse;
import org.example.ecommerceapp.Features.Authentication.Entity.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, LoginResponse> {


    Optional<TokenEntity> findByAccessToken(String token);
//
//    default List<TokenEntity> findAllAccessTokensByUser(Integer user) {
//        return null;
//    }

    Optional<TokenEntity> findByRefreshToken(String token);
}