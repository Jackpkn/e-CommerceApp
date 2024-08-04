package org.example.ecommerceapp.Features.Address.Entity;

import org.example.ecommerceapp.Features.User.Entity.UserEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Add this annotation
@Table(name = "addresses") // It's a good practice to specify the table name
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Integer addressId;

    @NotNull(message = "city cannot be null")
    private String city;

    @NotNull(message = "state cannot be null")
    private String state;

    @NotNull(message = "address cannot be null")
    private String address;

    @NotNull
    @Pattern(regexp = "[0-9]{6}", message = "Only Valid for 6 digit indian pincode")
    private String pincode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // Specify the foreign key column
    @JsonIgnoreProperties("addresses")
    @JsonIgnore
    private UserEntity user; // Change this to UserEntity

    @Override
    public String toString() {
        return this.city + ", " + this.state + ", " + this.pincode;
    }
}