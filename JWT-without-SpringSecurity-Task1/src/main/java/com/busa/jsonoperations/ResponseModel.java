package com.busa.jsonoperations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {

    private  String status =   Constants.FAIL;
    private  Object data;

}
