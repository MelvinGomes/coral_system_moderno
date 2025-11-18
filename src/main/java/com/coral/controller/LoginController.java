package com.coral.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
public String loginPage() {
    return "login.html";  // certo
}


    @PostMapping("/login")
    public ModelAndView validarLogin(@RequestParam String username,
                                     @RequestParam String password,
                                     HttpSession session) {

        if (username.equals("admin") && password.equals("1234")) {
            session.setAttribute("usuarioLogado", true);
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView("redirect:/login?error");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
