package com.example.individualpr.Controllers;

import com.example.individualpr.Models.*;
import com.example.individualpr.Repos.*;
import com.example.individualpr.RoleChek;
import com.example.individualpr.Service.ReportExcelExporter;
import com.example.individualpr.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/memo")
public class SubscriberMemoController {
    @Autowired
    private SubscriberMemoRepos subscriberMemoRepos;

    @Autowired
    private ClientRepos clientRepos;

    @Autowired
    private UserRepos userRepos;

    @GetMapping()
    public String memoList(Model model){
        model.addAttribute("subMemo", subscriberMemoRepos.findAll());
        model.addAttribute("client", clientRepos.findAll());
        model.addAttribute("user", userRepos.findActive());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "Memo/main";
    }
    @GetMapping("/add")
    public String memoAdd(SubscriberMemo subscriberMemo, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("users", userRepos.findActive());
        return "Memo/add";
    }

    @PostMapping("/add")
    public String memoAdd(@ModelAttribute("subscriberMemo") @Valid SubscriberMemo subscriberMemo,
                             BindingResult bindingResult,
                             Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if(bindingResult.hasErrors()){
            model.addAttribute("clients", clientRepos.findAll());
            model.addAttribute("users", userRepos.findActive());
            return "Memo/add";

        }
        subscriberMemoRepos.save(subscriberMemo);
        return "redirect:/ethContact/add";
    }
    @GetMapping("/addAdmin")
    public String memoAddAdmin(SubscriberMemo subscriberMemo, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("users", userRepos.findActive());
        return "Memo/add";
    }

    @PostMapping("/addAdmin")
    public String memoAddAdmin(@ModelAttribute("subscriberMemo") @Valid SubscriberMemo subscriberMemo,
                             BindingResult bindingResult,
                             Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if(bindingResult.hasErrors()){
            model.addAttribute("clients", clientRepos.findAll());
            model.addAttribute("users", userRepos.findActive());
            return "Memo/add";

        }
        subscriberMemoRepos.save(subscriberMemo);
        return "redirect:/memo";
    }

    @GetMapping("/edit/{subscriberMemo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String memoEdit(SubscriberMemo subscriberMemo, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("users", userRepos.findActive());
        return "Memo/edit";
    }

    @PostMapping("/edit/{subscriberMemo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String memoPostEdit(
            @ModelAttribute("subscriberMemo") @Valid SubscriberMemo subscriberMemo,
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
            model.addAttribute("users", userRepos.findActive());
            return "Memo/edit";
        }

        subscriberMemoRepos.save(subscriberMemo);
        return "redirect:/memo";
    }


    @GetMapping("/del/{subscriberMemo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String memoDel(
            SubscriberMemo subscriberMemo, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        subscriberMemoRepos.delete(subscriberMemo);
        return "redirect:/memo";
    }
//    @GetMapping("/export/excelOrd")
//    public void exportToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=report_order" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//
//        List<RequestSupport> listOrder = service.listAll();
//
//        ReportExcelExporter excelExporter = new ReportExcelExporter(listOrder);
//
//        excelExporter.export(response);
//    }
}
