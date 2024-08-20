package dev.minn_shop.minn_shop;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class TestController {
    @GetMapping("/")
    public String test() {
        return new String("hello world");
    }
    
}
