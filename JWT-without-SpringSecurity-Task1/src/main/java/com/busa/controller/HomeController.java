package com.busa.controller;

import com.busa.jsonoperations.ResponseModel;
import com.busa.model.JwtUtils;
import com.busa.model.LoginDetails;
import com.busa.model.UserDetails;
import com.busa.services.UserDetailsServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

@RestController
public class HomeController {

    @Autowired
    private UserDetailsServices userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/home")
    public String HomePage(){
        return "Hello";
    }

    @PostMapping("/save")
    public ResponseModel adduserDetails(@RequestBody UserDetails userDetails) throws IOException {
        return userDetailService.addUsers(userDetails);
    }

   @PostMapping(value = {"/login"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel loginUser(@RequestBody LoginDetails loginDetails , ModelAndView view) throws IOException {
       System.out.println(view.addObject("id",loginDetails.getUsername()));
        return userDetailService.findByUsername(loginDetails);
    }
    @PostMapping(value = {"/log"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel loginUser1() throws IOException {
        return userDetailService.jwtDecode();
    }






   @GetMapping("/getuser")
        public ResponseModel fetchAllUser(){
        return userDetailService.fetchAllUsers();
        }

        @GetMapping("/getuser/{id}")
        public ResponseModel fetchUserbyID(@PathVariable int id){
        return userDetailService.fetchUserbyID(id);
        }




}
