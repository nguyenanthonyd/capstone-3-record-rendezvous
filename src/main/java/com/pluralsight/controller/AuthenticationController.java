package com.pluralsight.controller;




import com.pluralsight.model.LoginRequest;
import com.pluralsight.model.LoginResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // prove request binding + response works
        return new LoginResponse(request.getUsername(), "ADMIN", "TEMP_TOKEN");
    }
}
