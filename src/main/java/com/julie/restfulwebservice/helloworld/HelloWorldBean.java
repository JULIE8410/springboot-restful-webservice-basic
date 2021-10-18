package com.julie.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // setter, getter, constructor, toString
@AllArgsConstructor // <-> @NoArgsConstructor
public class HelloWorldBean {

    private String message;

}
