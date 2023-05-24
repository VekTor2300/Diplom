package com.example.individualpr.Controllers;

import com.example.individualpr.Models.AppStatus;
import com.example.individualpr.Repos.AppStatusRepos;
import com.example.individualpr.RoleChek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/adminOrderStatus")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class AppStausController {
    @Autowired
    private AppStatusRepos orderStatusRepos;

    @GetMapping
    public String orderSList(Model model){
        Iterable<AppStatus> orderStatuses = orderStatusRepos.findAll();
        model.addAttribute("status",orderStatuses);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "OrderStatus/main";
    }

    @GetMapping("/add")
    public String orderSAdd(AppStatus appStatus, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "OrderStatus/add";
    }

    @PostMapping("/add")
    public String orderSAdd(@ModelAttribute("appStatus")@Valid AppStatus appStatus,
                          BindingResult bindingResult, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if(bindingResult.hasErrors()){
            return "OrderStatus/add";
        }
        orderStatusRepos.save(appStatus);
        return "redirect:/adminOrderStatus";
    }

    @GetMapping("/edit/{appStatus}")
    public String orderSEdit(AppStatus appStatus, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "OrderStatus/edit";
    }

    @PostMapping("/edit/{appStatus}")
    public String orderSPostEdit(@ModelAttribute("appStatus") @Valid AppStatus appStatus, BindingResult bindingResult, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("isUser", new RoleChek().userCheck(auth));
    model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
    model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
    if (bindingResult.hasErrors()) {
    return "OrderStatus/edit";
        }
    orderStatusRepos.save(appStatus);
    return "redirect:../";
    }

    @GetMapping("/del/{appStatus}")
    public String orderSDel(
            AppStatus appStatus, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        orderStatusRepos.delete(appStatus);
        return "redirect:../";
    }
}
