package com.g2.bank.Controller;

import com.g2.bank.exception.ResourceNotFoundException;
import com.g2.bank.model.Dto.Account;
import com.g2.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/account/{id}")
    public String getAccount(Model model, Principal principal, @PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        model.addAttribute("accountDetails", accountService.findById(id));
        return "account";
    }

    @RequestMapping("/account/create")
    public String createAccount(Model model, Principal principal, @RequestBody Account account) throws ResourceNotFoundException {
        model.addAttribute("accountDetails", accountService.createAccount(account));
        return "account";
    }

}
