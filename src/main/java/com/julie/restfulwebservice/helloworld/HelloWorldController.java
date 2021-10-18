package com.julie.restfulwebservice.helloworld;

import com.julie.restfulwebservice.helloworld.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired private MessageSource messageSource;

    /*
           /hello-world (endpoint)
            클라이언트의 요청정보를 서버에서 처리해서 적절한 결과 메시지 HttpStatus 코드 만들어 클라이언트에게 전달
     */
//    @GetMapping(path = "/hello-world")
//    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping("/hello-world")
    public String hello()   {
        return "Hello";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world-bean/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello, %s", name));
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        // required false 해놓고 설정되어서 헤더에 날라오는 거 없으면 자동으로 canada 해놓은 디폴트 사용

        return messageSource.getMessage("greeting.message", null, locale);

    }
}
