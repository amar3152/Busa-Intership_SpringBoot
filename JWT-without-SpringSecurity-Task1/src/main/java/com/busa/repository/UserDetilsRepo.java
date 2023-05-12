package com.busa.repository;

import com.busa.jsonoperations.Constants;
import com.busa.jsonoperations.ResponseModel;
import com.busa.model.JwtUtils;
import com.busa.model.LoginDetails;
import com.busa.model.UserDetails;
import com.busa.services.UserDetailsServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class UserDetilsRepo implements UserDetailsServices {

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public ResponseModel addUsers(UserDetails userDetails) throws IOException {
        ResponseModel responseModel = new ResponseModel();

        List<UserDetails> userData = readJsonData();
        UserDetails userpresents = null;

        if (userData!=null) {
            userpresents = userData.parallelStream().filter(u-> u.getId() == userDetails.getId() ).findAny().orElse(null);
        } else {
            userData = new ArrayList<>();
        }

        if (userpresents == null){
            userData.add(userDetails);
            String token = jwtUtils.genratetoken(userDetails);
            Map<String, Object> data = new HashMap<>();
            data.put("accessToke:- ",token);
            responseModel.setData(data);

            boolean status = writeJsonData(userData);

            if (status){
                responseModel.setStatus(Constants.SUCCESS);
                responseModel.setStatus(Constants.ADDED);
            }
        }else {
            responseModel.setStatus(Constants.ALREADY);
        }
        return responseModel;
    }

    @Override
    public ResponseModel fetchAllUsers() {
        ResponseModel responseModel = new ResponseModel();
        List<UserDetails> userData = readJsonData();
        responseModel.setStatus(Constants.SUCCESS);
        responseModel.setData(userData);
        return responseModel;
    }

    @Override
    public ResponseModel fetchUserbyID(int id) {

        ResponseModel responseModel = new ResponseModel();
        List<UserDetails> userdata = readJsonData();

        UserDetails userDetails = userdata.parallelStream().filter(i->i.getId()==id).findAny().orElse(null);

        if (userDetails != null){
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setData(userDetails);
        }else {
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setStatus(Constants.NOTFOUND);
        }
        return responseModel;
    }

    ResponseModel responseModel = new ResponseModel();
    List<UserDetails> userdata = readJsonData();
    //UserDetails userDetails = (UserDetails) userdata;



    @Override
    public ResponseModel findByUsername(LoginDetails loginDetails) {


        UserDetails userDetails = userdata.parallelStream().filter(i->i.getUsername().equals(loginDetails.getUsername()) && i.getPassword().equals(loginDetails.getPassword())).findAny().orElse(null);

       // String token     = tokenCreation(username, password);


        if (userDetails != null){
            String token;
            token = jwtUtils.genratetoken(userDetails);
            responseModel.setData(token);
            responseModel.setData(loginDetails.getUsername());
            System.out.println(loginDetails.getUsername()+" "+loginDetails.getPassword()+ " " + token);




            Map<String,Object> data = new HashMap<>();
            data.put("accestoken" ,token);
           // responseModel.setData(data);

            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setData(userDetails.getMobno());
            responseModel.setData(userDetails.getUsername());
            responseModel.setData(data);
        }else {
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setStatus(Constants.NOTFOUND);
        }



        return responseModel;
    }

    @Override
    public ResponseModel jwtDecode(UserDetails loginDetails) throws IOException {
        return null;
    }

    @Override
    public ResponseModel jwtDecode() {
        UserDetails userDetails = userdata.get(fetchUserbyID());
        String token = jwtUtils.genratetoken(userDetails);
        System.out.println(token);

        String[] spilt_token = token.split("\\.");
        String header = spilt_token[0];
        String body = spilt_token[1];
        String signature = spilt_token[2];


        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new org.apache.tomcat.util.codec.binary.Base64();
        String header1 = new String(base64Url.decode(header));
        System.out.println("JWT Header : " + header1);

        String body1 = new String(base64Url.decode(body));
        System.out.println("JWT Body:- "+ body1);


        responseModel.setData(body1);
        responseModel.setData(userDetails.getUsername());
        responseModel.setData("");
        return responseModel;
    }


    public List<UserDetails> readJsonData(){
        List<UserDetails> userDetails = new ArrayList<>();
        try {
            String json = Files.readString(Path.of("userdetails.json"));
            userDetails = new Gson().fromJson(json, new TypeToken<List<UserDetails>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userDetails;
    }

    public boolean writeJsonData(List<UserDetails> userDetails) throws IOException {
        boolean status = false;
        try (FileWriter file = new FileWriter("userdetails.json")){
            file.write(new Gson().toJson(userDetails));
            file.flush();
            status = true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return status;
    }


}
