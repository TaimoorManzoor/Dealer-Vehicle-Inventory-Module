package com.automobile.DealerAndVehicleInventory.Controller;

import com.automobile.DealerAndVehicleInventory.Entity.User;
import com.automobile.DealerAndVehicleInventory.Service.UserDetailsServicesImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private  UserDetailsServicesImplementation userDetailsServicesImplementation;


    public UserController(UserDetailsServicesImplementation userDetailsServicesImplementation) {
        this.userDetailsServicesImplementation = userDetailsServicesImplementation;
    }

    @PostMapping(value = "/admin/users/create")
    public ResponseEntity<String> RegisterUser(@RequestBody User user){
        if (!userDetailsServicesImplementation.registerUser(user)){
            return new ResponseEntity<>("User cannot be registered because a dealer admin already exists for this tenantId. OR GLOBAL ADMIN or Dealer Admin don't exist with tenant Id", HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>("Register SuccessFully", HttpStatus.CREATED);
        }
    }

//
//    @PostMapping(value = "/users/login")
//    public ResponseEntity<String> LoginUser(@RequestParam  String userName,@RequestParam  String password){
//        if (!userDetailsServicesImplementation.loginUser(userName, password)){
//            return new ResponseEntity<>("Login Credentials Invalid ",HttpStatus.BAD_REQUEST);
//        }
//        else{
//            return new ResponseEntity<>("Login SuccessFully", HttpStatus.CREATED);
//        }
//    }


}
