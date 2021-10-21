package com.julie.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"})  // 필터하고 싶은 프로퍼티 명 기재
@JsonFilter("UserInfo") // 부여한 UserInfo 값은 컨트롤러나 서비스클래스에서 사용될것임
public class User {

    private Integer id;

    @Size(min = 2, message = "The name must be at least two characters")
    private String name;

    @Past  // 미래날짜 쓸수없게
    private Date joinDate;

//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String ssn;
}
