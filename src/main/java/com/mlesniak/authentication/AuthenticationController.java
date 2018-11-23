package com.mlesniak.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class AuthenticationController {
    @Resource
    private final Authentication authentication;

    @Autowired
    public AuthenticationController(Authentication authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/callback")
    public String githubCallback(@RequestParam(name = "code") String code) {
        authentication.retrieveToken(code);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        authentication.revokeToken();
        return "redirect:/";
    }
}
