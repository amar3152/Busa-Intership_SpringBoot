package com.busa.services;

import com.busa.jsonoperations.ResponseModel;
import com.busa.model.LoginDetails;
import com.busa.model.UserDetails;

import java.io.IOException;

public interface UserDetailsServices {
    ResponseModel addUsers(UserDetails userDetails) throws IOException;

    ResponseModel fetchAllUsers();
    ResponseModel fetchUserbyID(int id);


    ResponseModel findByUsername(LoginDetails loginDetails);

    public ResponseModel jwtDecode(UserDetails loginDetails) throws IOException;


    public ResponseModel jwtDecode();
}
