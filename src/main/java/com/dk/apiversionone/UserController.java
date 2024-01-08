package com.dk.apiversionone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//https://console.aiven.io/account/a4863a7baf4b/project/dk-springboot/services
@RestController
public class UserController {

    @Autowired
    private  UserRepository userRepository;


    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }


    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(@RequestParam String name, @RequestParam String email){

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);

        return user;
    }

    @GetMapping(path = "/all")
    public  @ResponseBody Iterable<User> getAllUser(){
        return userRepository.findAll();
    }

}
