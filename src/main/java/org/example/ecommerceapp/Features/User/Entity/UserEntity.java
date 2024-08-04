package org.example.ecommerceapp.Features.User.Entity;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.example.ecommerceapp.Features.Address.Entity.Address;
import org.example.ecommerceapp.Features.Authentication.Entity.TokenEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    @NotNull
    @Pattern(regexp = "[a-z]{6,12}", message = "Username must be between 6 to 12 characters. Must only contain lowercase characters.")
    private String userName;

    @Column(name = "user_password")
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]{6,12}", message = "Password must contain between 6 to 12 characters. Must be alphanumeric with both Upper and lowercase characters.")
    private String userPassword;

    @NotNull
    @Pattern(regexp = "[a-z]{3,12}", message = "First Name must not contains numbers or special characters")
    private String firstName;

    @NotNull
    @Pattern(regexp = "[a-z]{3,12}", message = "Last Name must not contains numbers or special characters")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must have 10 digits")
    private String mobileNumber;

    @Email
    @NotNull
    @Column(name = "email")
    private String email;
    @JsonIgnoreProperties("user")
    @OneToOne(cascade = CascadeType.ALL)
    private TokenEntity login;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }
}