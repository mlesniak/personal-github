package com.mlesniak;

import com.mlesniak.authentication.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class IndexController {
    @Resource
    private User user;
    private final Authentication authentication;

    @Autowired
    public IndexController(Authentication authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                        Model model) {
        model.addAttribute("name", name);
        model.addAttribute("user", user.toString());

        System.out.println(user);
        if (!user.isAuthenticated()) {
            return "redirect:" + authentication.getAuthenticationUrl();
        }

        return "greeting";
    }

//    @GetMapping("/callback")
//    public String githubCallback(@RequestParam(name = "code") String code) {
//        authentication.retrieveToken(code);
//        return "redirect:/";
//    }
//
//    @GetMapping("/logout")
//    public String logout() {
//        authentication.revokeToken();
//        return "redirect:/";
//    }
}
