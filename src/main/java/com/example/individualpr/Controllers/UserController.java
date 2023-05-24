package com.example.individualpr.Controllers;

import com.example.individualpr.Models.Role;
import com.example.individualpr.Models.User;
import com.example.individualpr.Repos.UserRepos;
import com.example.individualpr.RoleChek;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepos userRepository;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "User/usermain";
    }

    @GetMapping("/add")
    public String userAdd(User user, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("roles", Role.values());

        return "User/useradd";
    }

    @PostMapping("/add")
    public String userAdd(@ModelAttribute("user")@Valid User user,
                          BindingResult bindingResult,
                          Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if (bindingResult.hasErrors()){
            model.addAttribute("roles", Role.values());

            return "User/useradd";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/addClient")
    public String userClientAdd(User user, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("roles", Role.values());


        return "User/useradd";
    }

    @PostMapping("/addClient")
    public String userClientAdd(@ModelAttribute("user")@Valid User user,
                          BindingResult bindingResult,
                          Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if (bindingResult.hasErrors()){
            model.addAttribute("roles", Role.values());

            return "User/useradd";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);

        return "redirect:/userClient/add";
    }

    /**
     *
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/edit/{user}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userEdit(User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("roles", Role.values());
        return "User/useredit";
    }

    /**
     *
     * @param user
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/edit/{user}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userEditSave(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "User/useredit";
        }
        userRepository.save(user);
        return "redirect:../";
    }

    /**
     *
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/del/{user}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userDel(
            User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        user.setActive(false);
        userRepository.save(user);
        return "redirect:../";
    }

}