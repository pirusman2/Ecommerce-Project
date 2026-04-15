package com.peerecom.ecommerceApp.service;
import com.peerecom.ecommerceApp.dto.AddressDTO;
import com.peerecom.ecommerceApp.dto.UserRequest;
import com.peerecom.ecommerceApp.dto.UserResponse;
import com.peerecom.ecommerceApp.model.Address;
import com.peerecom.ecommerceApp.model.User;
import com.peerecom.ecommerceApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;



    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserresponse)
                .collect(Collectors.toList());
    }

        public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepository.save(user);
        }

    public Optional<UserResponse> findById(Long id){
        return userRepository.findById(id)
                .map(this::mapToUserresponse);
        }

    public boolean updateUser(Long id, UserRequest updatedUserRequest){
        return  userRepository.findById(id)//userlist.stream(). //gives all users
                                                                     //.filter(user -> user.getId().equals(id)) //keeps only the matching one by
                                                                         // matching with the id that was passed by the cleint
                                                                         // .findFirst() // actually picks that one user and gives it to us
                .map(existingUser-> { //now we have the real user to update
                   updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                } ).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());
        }
    }


    private UserResponse mapToUserresponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(addressDTO.getStreet());
            addressDTO.setCity(addressDTO.getCity());
            addressDTO.setState(addressDTO.getState());
            addressDTO.setCountry(addressDTO.getCountry());
            addressDTO.setZipCode(addressDTO.getZipCode());

            response.setAddress(addressDTO);
        }
        return response;
    }


}

