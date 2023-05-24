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
@RequestMapping("/orderSotr")
public class RequestSupportController {
    @Autowired
    private AppStatusRepos orderStatusRepos;
    @Autowired
    private RequestSupportRepos orderRepos;
    @Autowired
    private ReportService service;

    @GetMapping()
    public String orderList(Model model){
        model.addAttribute("orders", orderRepos.findAll());
        model.addAttribute("orderStatus", orderStatusRepos.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "Order/main";
    }

    @GetMapping("/PersonalData")
    public String LawList(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "Order/FederalLaw";
    }
    @GetMapping("/add")
    public String requestAdd(RequestSupport requestSupport, Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        Iterable<AppStatus> orderStatuses = orderStatusRepos.findAll();
        model.addAttribute("orderStatuses", orderStatuses);
        return "Order/add";
    }

    @PostMapping("/add")
    public String requestAdd(@ModelAttribute("requestSupport") @Valid RequestSupport requestSupport,
                             BindingResult bindingResult,
                             Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if(bindingResult.hasErrors()){
            model.addAttribute("orderStatuses", orderStatusRepos.findAll());
            return "Order/add";

        }
        Long code = ThreadLocalRandom.current().nextLong(11, 999 + 1);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a"));
        requestSupport.setDateApp(time);
        requestSupport.setCodeApp(code);
        orderRepos.save(requestSupport);
        return "redirect:/";
    }

    @GetMapping("/edit/{requestSupport}")
    public String orderEdit(RequestSupport requestSupport, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        Iterable<AppStatus> orderStatuses = orderStatusRepos.findAll();
        model.addAttribute("orderStatuses", orderStatuses);
        return "Order/edit";
    }

    @PostMapping("/edit/{requestSupport}")
    public String orderPostEdit(
            @ModelAttribute("requestSupport") @Valid RequestSupport requestSupport,
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
            model.addAttribute("orderStatuses", orderStatusRepos.findAll());
            return "Order/edit";
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a"));
        requestSupport.setDateApp(time);
        orderRepos.save(requestSupport);
        return "redirect:../";
    }


    @GetMapping("/del/{requestSupport}")
    public String orderDel(
            RequestSupport requestSupport, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        orderRepos.delete(requestSupport);
        return "redirect:../";
    }


    @GetMapping("/delSoft/{requestSupport}")
    public String orderDelSoft(
            RequestSupport requestSupport, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        requestSupport.setDeleted(Boolean.TRUE);
        orderRepos.save(requestSupport);
        return "redirect:../";
    }
    @GetMapping("/export/excelOrd")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report_order" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<RequestSupport> listOrder = service.listAll();

        ReportExcelExporter excelExporter = new ReportExcelExporter(listOrder);

        excelExporter.export(response);
    }

}
