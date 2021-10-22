package com.julie.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
//@JsonFilter("UserInfo") // 부여한 UserInfo 값은 컨트롤러나 서비스클래스에서 사용될것임
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    private Integer id;

    @Size(min = 2, message = "The name must be at least two characters")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요")
    private String name;

    @Past  // 미래날짜 쓸수없게
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요")
    private Date joinDate;

//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요")
    private String password;

//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요")
    private String ssn;
}
