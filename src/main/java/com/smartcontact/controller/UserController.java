package com.smartcontact.controller;

import com.smartcontact.model.Contact;
import com.smartcontact.model.User;
import com.smartcontact.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @ModelAttribute
    public void add(Model model,Principal principal){
        String username=principal.getName();

        User user=userRepo.getUserByUsername(username);
        System.out.println(user);
        model.addAttribute("user",user);
    }

    @GetMapping("/index")
    public String user(Model model, Principal principal){

        return "user";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model){
        model.addAttribute("tittle","AddContact");
        model.addAttribute("contact",new Contact());
        return "add-contact";
    }
}
