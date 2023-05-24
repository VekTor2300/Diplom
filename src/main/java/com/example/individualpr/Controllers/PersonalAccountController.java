package com.example.individualpr.Controllers;

import com.example.individualpr.Models.Client;
import com.example.individualpr.Models.PersonalAccount;
import com.example.individualpr.Models.Rate;
import com.example.individualpr.Models.SubscriberMemo;
import com.example.individualpr.Repos.*;
import com.example.individualpr.RoleChek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/personalAccount")
public class PersonalAccountController {
    @Autowired
    private PersonalAccountRepos presonalAccountRepos;

    @Autowired
    private ClientRepos clientRepos;

    @Autowired
    private RateRepos rateRepos;

    @GetMapping
    public String personalAccList(Model model){
        model.addAttribute("personalAcc", presonalAccountRepos.findAll());
        model.addAttribute("client", clientRepos.findAll());
        model.addAttribute("rate", rateRepos.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "PersonalAcc/main";
    }
    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public String personalAccAdd(PersonalAccount personalAccount, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        List<Rate> rates = rateRepos.findAll();
        model.addAttribute("rates", rates);
        Long code = ThreadLocalRandom.current().nextLong(11, 999999 + 1);
        Double balic = Double.valueOf(0);
        personalAccount.setNumPersonalAccount(code);
        personalAccount.setBalanceAccount(balic);
        return "PersonalAcc/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public String personalAccAdd(@ModelAttribute("personalAccount") @Valid PersonalAccount personalAccount,
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
            model.addAttribute("rates", rateRepos.findAll());
            return "PersonalAcc/add";

        }
        presonalAccountRepos.save(personalAccount);
        return "redirect:/memo/add";
    }
    @GetMapping("/addAdmin")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public String personalAccAddAdmin(PersonalAccount personalAccount, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        List<Rate> rates = rateRepos.findAll();
        model.addAttribute("rates", rates);
        Long code = ThreadLocalRandom.current().nextLong(11, 999999 + 1);
        Double balic = Double.valueOf(0);
        personalAccount.setNumPersonalAccount(code);
        personalAccount.setBalanceAccount(balic);
        return "PersonalAcc/add";
    }

    @PostMapping("/addAdmin")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public String personalAccAddAdmin(@ModelAttribute("personalAccount") @Valid PersonalAccount personalAccount,
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
            model.addAttribute("rates", rateRepos.findAll());
            return "PersonalAcc/add";

        }
        presonalAccountRepos.save(personalAccount);
        return "redirect:/personalAccount";
    }

    @GetMapping("/edit/{personalAccount}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String personalAccEdit(PersonalAccount personalAccount, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("rates", rateRepos.findAll());
        return "PersonalAcc/edit";
    }

    @PostMapping("/edit/{personalAccount}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String personalAccPostEdit(
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
            return "PersonalAcc/edit";
        }

        presonalAccountRepos.save(personalAccount);
        return "redirect:/personalAccount";
    }

    @GetMapping(value = "/replenishment/{personalAccount}")
    public String personalAccReplenishment(PersonalAccount personalAccount,
                                           Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        List<Client> clients = clientRepos.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("rates", rateRepos.findAll());
        return "PersonalAcc/replenishment";
    }

    @PostMapping("/replenishment/{personalAccount}")
    public String personalAccPostReplenishment(
            @ModelAttribute("personalAccount") @Valid PersonalAccount personalAccount,
            @RequestParam("amount") Double amount,
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
            return "PersonalAcc/replenishment";
        }
        System.out.println("coming in controller    " +amount.toString());
        System.out.println("coming in controller    " +personalAccount.getBalanceAccount().toString());
        personalAccount.setBalanceAccount(personalAccount.getBalanceAccount() + amount);
        presonalAccountRepos.save(personalAccount);
        return "redirect:/personalAccount";
    }


    @GetMapping("/del/{personalAccount}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String personalAccDel(
            PersonalAccount personalAccount, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        presonalAccountRepos.delete(personalAccount);
        return "redirect:/personalAccount";
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
