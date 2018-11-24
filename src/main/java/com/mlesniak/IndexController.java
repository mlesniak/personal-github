package com.mlesniak;

import com.mlesniak.authentication.AuthenticationService;
import com.mlesniak.authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class IndexController {
    @Resource
    private User user;
    private final AuthenticationService authentication;

    @Autowired
    public IndexController(AuthenticationService authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", user);
        return "index";
    }
}
