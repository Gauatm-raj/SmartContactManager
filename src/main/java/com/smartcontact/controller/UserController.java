package com.smartcontact.controller;

import com.smartcontact.model.Contact;
import com.smartcontact.model.User;
import com.smartcontact.repository.ContactRepo;
import com.smartcontact.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ContactRepo contactRepo;

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
                contact.setImage("contact.png");
            }else{
                contact.setImage(file.getOriginalFilename());
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

    @RequestMapping("/view-contact/{page}")
    public String viewContact(@PathVariable("page") Integer page, Model m,Principal p){
        String username=p.getName();
        User u=this.userRepo.getUserByUsername(username);
        Pageable pageable= PageRequest.of(page,5);
        Page<Contact> contact=this.contactRepo.getContactByUserId(u.getId(),pageable);
        m.addAttribute("currentPage",page);
        m.addAttribute("totalPage",contact.getTotalPages());
        m.addAttribute("contact",contact);
        return "view-contact";
    }

    @RequestMapping("/contact/{id}")
    public String ShowOneContact(@PathVariable("id") Integer id,Model model,Principal p){
        Contact contact=this.contactRepo.findById(id).get();
        String username=p.getName();
        User user=this.userRepo.getUserByUsername(username);

        if(user.getId()==contact.getUser().getId())
          model.addAttribute("contact",contact);

        return "contact-detail";
    }

    @RequestMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cId,Model model,Principal p){
        Contact c=this.contactRepo.findById(cId).get();
        System.out.println("C name"+c.getName());
        System.out.println("contact userId "+c.getUser().getId());

        String username=p.getName();
        User user=this.userRepo.getUserByUsername(username);

        if(user.getId()==c.getUser().getId()){
            c.setUser(null);
            this.contactRepo.deleteById(c.getcId());
        }



        return "redirect:/user/view-contact/0";
    }
}
