package com.smartcontact.controller;

import com.smartcontact.model.Contact;
import com.smartcontact.model.User;
import com.smartcontact.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    //Contact data
    @PostMapping("/contact-data")
    public String contactData(@ModelAttribute Contact contact, @RequestParam("image") MultipartFile file, Principal p){
        try {
            String name = p.getName();
            User user = this.userRepo.getUserByUsername(name);

            if(file.isEmpty()){

            }else{
                contact.setImage(file);
                File savefile=new ClassPathResource("/static/image").getFile();
                Path path=Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }
            contact.setUser(user);
            user.getContact().add(contact);
            this.userRepo.save(user);
            System.out.println("DATA" + contact);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "add-contact";
    }
}
