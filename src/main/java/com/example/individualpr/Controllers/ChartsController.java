package com.example.individualpr.Controllers;

import com.example.individualpr.Repos.ChequeRepos;
import com.example.individualpr.Repos.RequestSupportRepos;
import com.example.individualpr.RoleChek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChartsController {

    @Autowired
    private RequestSupportRepos orderRepos;

    @Autowired
    private ChequeRepos chequeRepos;

    @GetMapping("/barChart")
    public String showChart(Model model) {
        model.addAttribute("cheques", ((List) chequeRepos.findAll()).stream().count());

        model.addAttribute("orders", ((List) orderRepos.findAll()).stream().count());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();//получение пользователя,который в момент авторизован
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "barChart";
    }

}
