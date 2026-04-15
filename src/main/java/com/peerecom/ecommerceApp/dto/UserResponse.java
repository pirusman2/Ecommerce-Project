package com.peerecom.ecommerceApp.dto;

import com.peerecom.ecommerceApp.model.UserRole;
import lombok.Data;


// this is the only data we will show it our users or clients
@Data
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserRole role;

    private AddressDTO address;
}
