package com.example.individualpr.Controllers;

import com.example.individualpr.Models.Employee;
import com.example.individualpr.Models.Report;
import com.example.individualpr.Repos.EmployeeRepos;
import com.example.individualpr.Repos.ReportRepos;
import com.example.individualpr.RoleChek;
import com.example.individualpr.Service.ReportService;
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

import javax.validation.Valid;

@Controller
//@RequestMapping("/report")
@PreAuthorize("hasAnyAuthority('SALESDEPARTMENTEMPLOYEE')")
public class ReportController {

    @Autowired
    private ReportRepos reportRepos;
    @Autowired
    private EmployeeRepos employeeRepos;
    @Autowired
    private ReportService service;

    @GetMapping()
    public String reportList(Model model){
        Iterable<Report> reports = reportRepos.findAll();
        model.addAttribute("reports",reports);
//        Iterable<Employee> employees = employeeRepos.findAll();
//        model.addAttribute("employees", employees);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "Report/main";
    }

    @GetMapping("/add")
    public String reportAdd(Report report, Model model){
        Iterable<Employee> employees = employeeRepos.findAll();
        model.addAttribute("employees", employees);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "Report/add";
    }

    @PostMapping("/add")
    public String reportAdd(@ModelAttribute("report")@Valid Report report,
                           BindingResult bindingResult, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if(bindingResult.hasErrors()){
            Iterable<Employee> employees = employeeRepos.findAll();
            model.addAttribute("employees", employees);
            return "Report/add";
        }
        reportRepos.save(report);
        return "redirect:";
    }

    @GetMapping("/edit/{report}")
    public String reportEdit(Report report, Model model) {
        Iterable<Employee> employees = employeeRepos.findAll();
        model.addAttribute("employees", employees);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "Report/edit";
    }

    @PostMapping("/edit/{report}")
    public String reportPostEdit(
            @ModelAttribute("report") @Valid Report report,
            BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        if (bindingResult.hasErrors()) {
            Iterable<Employee> employees = employeeRepos.findAll();
            model.addAttribute("employees", employees);
            return "Report/edit";
        }
        reportRepos.save(report);
        return "redirect:../";
    }

    @GetMapping("/del/{report}")
    public String reportDel(
            Report report, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        reportRepos.delete(report);
        return "redirect:../";
    }


}
