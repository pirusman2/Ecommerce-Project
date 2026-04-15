package com.peerecom.ecommerceApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "user_table")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserRole role = UserRole.COSTUMER;

    // so this is for a relationship between the address and the user so every user will have
    // a specific id and it is differentiated wth the help of user and address id

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id") // this line is linking the
    private Address address;                                    // the user entity to an addressId

    // cascadeTypeAll is for when a user is updated, created, or deleted so the address will also
    // change

    // when a particular user record was created and updated
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
