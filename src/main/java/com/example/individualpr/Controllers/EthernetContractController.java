package com.example.individualpr.Controllers;

import com.example.individualpr.Models.*;
import com.example.individualpr.Repos.*;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/ethContact")
public class EthernetContractController {

    @Autowired
    private EthernetContractRepos ethernetContractRepos;

    @Autowired
    private SubscriberMemoRepos subscriberMemoRepos;
    @Autowired
    private PersonalAccountRepos presonalAccountRepos;

    @Autowired
    private ClientRepos clientRepos;

    @Autowired
    private RateRepos rateRepos;

    @GetMapping()
    public String ethContactList(Model model){
        model.addAttribute("ethContact", ethernetContractRepos.findAll());
        model.addAttribute("subMemo", subscriberMemoRepos.findAll());
        model.addAttribute("psAcc", presonalAccountRepos.findAll());
        model.addAttribute("client", clientRepos.findAll());
        model.addAttribute("rate", rateRepos.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("isAuth", userDetails.getUsername());
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "EthernetContact/main";
    }
    @GetMapping("/add")
    public String ethContactAdd(EthernetContract ethernetContract, Model model){
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
        List<PersonalAccount> personalAccounts = presonalAccountRepos.findAll();
        model.addAttribute("personalAccounts", personalAccounts);
        List<SubscriberMemo> subscriberMemos = subscriberMemoRepos.findAll();
        model.addAttribute("subscriberMemos", subscriberMemos);
        Long code = ThreadLocalRandom.current().nextLong(11, 999999 + 1);
        ethernetContract.setContractId(String.valueOf("Ф-" + code + "ВЛ"));
        return "EthernetContact/add";
    }

    @PostMapping("/add")
    public String ethContactAdd(@ModelAttribute("ethernetContract") @Valid EthernetContract ethernetContract,
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
            model.addAttribute("subscriberMemos", subscriberMemoRepos.findAll());
            model.addAttribute("personalAccounts", presonalAccountRepos.findAll());
            return "EthernetContact/add";

        }
        ethernetContractRepos.save(ethernetContract);
        return "redirect:/ethContact";
    }

    @GetMapping("/edit/{ethernetContract}")
    public String ethContactEdit(EthernetContract ethernetContract, Model model) {
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
        List<PersonalAccount> personalAccounts = presonalAccountRepos.findAll();
        model.addAttribute("personalAccounts", personalAccounts);
        List<SubscriberMemo> subscriberMemos = subscriberMemoRepos.findAll();
        model.addAttribute("subscriberMemos", subscriberMemos);
        return "EthernetContact/edit";
    }

    @PostMapping("/edit/{ethernetContract}")
    public String ethContactPostEdit(
            @ModelAttribute("ethernetContract") @Valid EthernetContract ethernetContract,
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
            model.addAttribute("subscriberMemos", subscriberMemoRepos.findAll());
            model.addAttribute("personalAccounts", presonalAccountRepos.findAll());
            return "EthernetContact/edit";
        }

        ethernetContractRepos.save(ethernetContract);
        return "redirect:/ethContact";
    }


    @GetMapping("/del/{ethernetContract}")
    public String ethContactDel(
            EthernetContract ethernetContract, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        ethernetContractRepos.delete(ethernetContract);
        return "redirect:/ethContact";
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
