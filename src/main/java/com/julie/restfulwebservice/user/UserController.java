package com.julie.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFountException(String.format("ID[%s] not found", id));
        }

        // HATEOAS : 현재 리소스오 연관된(호출가능한) 자원상태 정보를 제공
        EntityModel<User> entityModel = EntityModel.of(user);

        // linkTo() 컨크롤러 클래스는 가리키는 WebMvcLinkBuilder 객체 반환
        // methodOn() : 타켓메소드(UserController.class).retrieveAllUsers()) 의 가짜 메소드 콜이 있는 컨트롤러 프록시 클래스를 생성, 직접 메소드 객체를 만드는 것보다 유연한 표현 가능
        // 즉, retrieveUser 메소드 실행하면서 HATEOUS 함수들로 인해 retrieveUserAll메소드로 이동하는 URI를 반환하는 기능을 함
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
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
