package com.peerecom.ecommerceApp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;



    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

        public void addUser(User user){
        userRepository.save(user);
        }

        public Optional<User> findById(Long id){
        return userRepository.findById(id);
        }

    public boolean updateUser(Long id, User updateduser){
        return  userRepository.findById(id)//userlist.stream(). //gives all users
                                                                     //.filter(user -> user.getId().equals(id)) //keeps only the matching one by
                                                                         // matching with the id that was passed by the cleint
                                                                         // .findFirst() // actually picks that one user and gives it to us
                .map(existingUser-> { //now we have the real user to update
                    existingUser.setFirstName(updateduser.getFirstName());
                    existingUser.setLastName(updateduser.getLastName());
                    userRepository.save(existingUser);
                    return true;

                } ).orElse(false);

    }
}

