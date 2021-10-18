package com.julie.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private Integer id;

    @Size(min = 2, message = "The name must be at least two characters")
    private String name;

    @Past  // 미래날짜 쓸수없게
    private Date joinDate;

}
