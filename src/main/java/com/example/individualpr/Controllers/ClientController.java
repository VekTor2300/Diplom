package com.example.individualpr.Controllers;

import com.example.individualpr.Models.Client;
import com.example.individualpr.Models.Employee;
import com.example.individualpr.Models.Post;
import com.example.individualpr.Models.User;
import com.example.individualpr.Repos.ClientRepos;
import com.example.individualpr.Repos.EmployeeRepos;
import com.example.individualpr.Repos.PostRepos;
import com.example.individualpr.Repos.UserRepos;
import com.example.individualpr.RoleChek;
import com.lowagie.text.pdf.codec.Base64;
import org.bouncycastle.pqc.crypto.MessageEncryptor;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.jasypt.digest.StandardStringDigester;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/userClient")
public class ClientController {
    @Autowired
    private ClientRepos clientRepository;

    @Autowired
    private UserRepos userRepository;

    @GetMapping()
    public String clientList(Client client, Model model){
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("users", userRepository.findActive());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "Client/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String clientAdd(Client client, Model model){
        List<User> users =  userRepository.findActive();
        model.addAttribute("users", userRepository.findActive());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "Client/add";

    }

    @PostMapping("/add")
    public String clientAdd(@ModelAttribute("client") @Valid Client client,
                              BindingResult bindingResult, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if(bindingResult.hasErrors()){
            model.addAttribute("users", userRepository.findActive());
            return "Client/add";
        }
        clientRepository.save(client);
        return "redirect:/personalAccount/add";
    }

    @GetMapping("/addAdmin")
    @PreAuthorize("isAuthenticated()")
    public String clientAddAdmin(Client client, Model model){
        List<User> users =  userRepository.findActive();
        model.addAttribute("users", userRepository.findActive());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "Client/add";

    }

    @PostMapping("/addAdmin")
    public String clientAddAdmin(@ModelAttribute("client") @Valid Client client,
                              BindingResult bindingResult, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if(bindingResult.hasErrors()){
            model.addAttribute("users", userRepository.findActive());
            return "Client/add";
        }
        clientRepository.save(client);
        return "redirect:/userClient";
    }

    @GetMapping("/edit/{client}")
    public String clientEdit(Client client, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("users", userRepository.findActive());
        return "Client/edit";

    }

    @PostMapping("/edit/{client}")
    public String clientPostEdit(
            @ModelAttribute("client") @Valid Client client,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userRepository.findActive());
            return "Client/edit";
        }
        clientRepository.save(client);
        return "redirect:/userClient";
    }

    @GetMapping("/show/{client}")
    public String clientShow(
            Client client, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        model.addAttribute("users", userRepository.findActive());
        return "Client/detail";
    }

    @GetMapping("/del/{client}")
    @PreAuthorize("isAuthenticated()")
    public String clientDel(
            Client client, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        clientRepository.delete(client);
        return "redirect:../";
    }
}
