package com.avi.minio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hi Minio";
    }
}
