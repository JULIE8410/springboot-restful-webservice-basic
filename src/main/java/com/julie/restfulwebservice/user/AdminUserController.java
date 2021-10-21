package com.julie.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")  // /admin/users
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findAll();

        // 빈의 프로퍼티 제어할 수 있는 필터 생성, 포함시키고자 하는 필터값 선언
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // 필터를 사용할 수 있는 형태로 변경
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 유저데이터를 잭슨형태로
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);



        return mapping;
    }

//    @GetMapping("/v1/users/{id}") // /admin/users/{id}  => URI Versioning
//    @GetMapping(value = "/users/{id}/", params = "version=1")   //http://localhost:8088/admin/users/1/?version=1 => Request Parameter versioning
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")    => Media type versioning
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")  // http://localhost:8088/admin/users/1  (KEY: Accept, VALUE:application/vnd.company.appv2+json)  => (Custom) header versioning
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFountException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                                            .filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;
    }

//    @GetMapping("/v2/users/{id}") // /admin/users/{id}
//    @GetMapping(value = "/users/{id}/", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")  // 버전을 달리하여 API 호출 가능 (KEY: x-api-version, VALUE:1 또는 2로 구분)
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFountException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn",  "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);
        return mapping;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()  // 현재 요청되어진 request 값을 사용
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null){
            throw new UserNotFountException(String.format("ID[%s] not found", id));

        }
    }
}
