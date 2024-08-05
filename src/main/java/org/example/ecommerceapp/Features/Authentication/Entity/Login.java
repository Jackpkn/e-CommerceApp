package org.example.ecommerceapp.Features.Authentication.Entity;

import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Login {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int loginId;

	private String apiKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);

	private LocalDateTime keyExpiryDate = LocalDateTime.now().plusHours(4);

	private LoginStatus status = LoginStatus.LOGGED_IN;

	@JsonIgnoreProperties("login")
	@OneToOne(cascade = CascadeType.ALL)
	private UserEntity user;

	public void newLogin() {
		this.apiKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
		this.keyExpiryDate = LocalDateTime.now().plusHours(4);
		this.status = LoginStatus.LOGGED_IN;
	}

	public void revokeLogin() {
		this.apiKey = null;
		this.keyExpiryDate = null;
		this.status = LoginStatus.LOGGED_OUT;
	}

}
