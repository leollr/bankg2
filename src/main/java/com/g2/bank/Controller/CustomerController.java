package com.g2.bank.Controller;

import com.g2.bank.exception.ResourceNotFoundException;
import com.g2.bank.model.Dto.User;
import com.g2.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public String getAccount(Model model, Principal principal, @PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        model.addAttribute("customerDetails", customerService.findById(id));
        return "customer";
    }

    @RequestMapping("/customer/create")
    public String createCustomer(Model model, Principal principal, @RequestBody User user) {
        model.addAttribute("customerDetails", customerService.create(user));
        return "customer";
    }

}
