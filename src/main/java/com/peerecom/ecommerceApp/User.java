package com.peerecom.ecommerceApp;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "user_table")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


}
