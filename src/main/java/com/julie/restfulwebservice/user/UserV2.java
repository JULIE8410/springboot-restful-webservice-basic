package com.julie.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"})  // 필터하고 싶은 프로퍼티 명 기재
@JsonFilter("UserInfoV2") // 부여한 UserInfo 값은 컨트롤러나 서비스클래스에서 사용될것임
public class UserV2 extends User {

    private String grade;

}
