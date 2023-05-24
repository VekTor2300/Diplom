package com.example.individualpr.Controllers;

import com.example.individualpr.Models.ChatMessage;
import com.example.individualpr.Models.Client;
import com.example.individualpr.Models.Employee;
import com.example.individualpr.Repos.ChatMessageRepos;
import com.example.individualpr.Repos.ClientRepos;
import com.example.individualpr.Repos.EmployeeRepos;
import com.example.individualpr.RoleChek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/chatSupport")
public class ChatMessageController {
    @Autowired
    private ChatMessageRepos chatMessageRepos;
    @Autowired
    private ClientRepos clientRepos;
    @Autowired
    private EmployeeRepos employeeRepos;

    @GetMapping
    public String chatList(Model model){
        Iterable<ChatMessage> chatMessages = chatMessageRepos.findAll();
        model.addAttribute("chatMessages",chatMessages);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "mainEmployee";
    }

    @GetMapping("/send")
    public String messageSend(ChatMessage chatMessage, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "OrderStatus/add";
    }

    @PostMapping("/send")
    public String messageSend(@ModelAttribute("chatMessage")@Valid ChatMessage chatMessage,
                            BindingResult bindingResult, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if(bindingResult.hasErrors()){
            Iterable<Client> clients = clientRepos.findAll();
            model.addAttribute("clients", clients);
            Iterable<Employee> employees = employeeRepos.findAll();
            model.addAttribute("employees", employees);
            return "mainEmployee";
        }
        chatMessageRepos.save(chatMessage);
        return "redirect:/chatSupport";
    }

    @GetMapping("/edit/{chatMessage}")
    public String messageEdit(ChatMessage chatMessage, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "mainEmployee";
    }

    @PostMapping("/edit/{chatMessage}")
    public String messageEdit(
            @ModelAttribute("chatMessage") @Valid ChatMessage chatMessage,
            BindingResult bindingResult,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()) {
            Iterable<Client> clients = clientRepos.findAll();
            model.addAttribute("clients", clients);
            Iterable<Employee> employees = employeeRepos.findAll();
            model.addAttribute("employees", employees);
            return "mainEmployee";
        }
        chatMessageRepos.save(chatMessage);
        return "redirect:/chatSupport";
    }

    @GetMapping("/del/{chatMessageRepos}")
    public String messageDelete(
            ChatMessage chatMessage, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        chatMessageRepos.delete(chatMessage);
        return "redirect:/chatSupport";
    }
}
