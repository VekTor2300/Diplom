package com.example.individualpr.Controllers;

import com.example.individualpr.Models.*;
import com.example.individualpr.Repos.*;
import com.example.individualpr.RoleChek;
import com.example.individualpr.Service.ReportExcelExporter;
import com.example.individualpr.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class TarifController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonalAccountRepos presonalAccountRepos;
    @Autowired
    private EthernetContractRepos ethernetContractRepos;
    @Autowired
    private ClientRepos clientRepos;
    @Autowired
    private RateRepos rateRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private PersonalAccountRepos personalAccountRepos;

    @GetMapping("/tariff")
    public String tarifList(Model model){
        model.addAttribute("personalAcc", presonalAccountRepos.findAll());
        model.addAttribute("client", clientRepos.findAll());
        model.addAttribute("personalss", personalAccountRepos.findAll());
        model.addAttribute("ethc", ethernetContractRepos.findAll());
        model.addAttribute("userss", userRepos.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "tarif/main";
    }

    @GetMapping("/editTarif/{personalAccount}")
    public String tarifEdit(PersonalAccount personalAccount, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("rates", rateRepos.findAll());
        return "tarif/editRate";
    }

    @PostMapping("/editTarif/{personalAccount}")
    public String tarifPostEdit(
            @ModelAttribute("personalAccount") @Valid PersonalAccount personalAccount,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientRepos.findAll());
            model.addAttribute("rates", rateRepos.findAll());
            return "tarif/editRate";
        }
        presonalAccountRepos.save(personalAccount);
        return "redirect:/tariff";
    }

    @GetMapping("/changePass/{user}")
    public String changePass(User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "tarif/ChangePass";
    }

    @PostMapping("/changePass/{user}")
    public String changePassPost(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "tarif/ChangePass";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepos.save(user);
        return "redirect:/tariff";
    }



    @GetMapping("/eth")
    public String ethList(Model model){
        model.addAttribute("ethc", ethernetContractRepos.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "tarif/eth";
    }

    @GetMapping("eth/ethClose/{ethernetContract}")
    public String ethDelSoft(
            EthernetContract ethernetContract, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        ethernetContract.getClients().getUsers().setActive(Boolean.FALSE);
        ethernetContract.setDeleted(Boolean.TRUE);
        ethernetContractRepos.save(ethernetContract);
        return "redirect:../";
    }
}
