package com.ggh.api.presentation.test;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllerApi implements TestApi {

    @Override
    public String test() {
        return "테스트";
    }

}
