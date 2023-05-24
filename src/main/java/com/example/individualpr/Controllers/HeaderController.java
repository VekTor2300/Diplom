package com.example.individualpr.Controllers;

import com.example.individualpr.Repos.UserRepos;
import com.example.individualpr.RoleChek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.extras.springsecurity6.auth.Authorization;

import java.util.Map;

@Controller
public class HeaderController {
    @Autowired
    private UserRepos userRepos;

    @GetMapping("/Header")
    public String Main(Model model, Authentication authentication, IExpressionContext context) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            model.addAttribute("AuthUser",((UserDetails)principal).getUsername());
        } else {
            model.addAttribute("NotAuth", principal.toString());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("usser", userRepos.findActive());
        return "Blocks/Header";

    }

}