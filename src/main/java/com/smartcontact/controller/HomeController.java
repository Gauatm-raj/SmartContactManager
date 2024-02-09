package com.smartcontact.controller;

import com.smartcontact.helper.Message;
import com.smartcontact.model.User;
import com.smartcontact.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;




@Controller
public class HomeController {

       @Autowired
       UserRepo userRepo;

       @Autowired
       BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title","Home-Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Register-Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String register( @ModelAttribute("user") User user, Model model, HttpSession session){
        try{
            user.setRole("ROLE_Public");
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User result=this.userRepo.save(user);
            System.out.println("USER"+user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("User added success",
                    "alert-success"));
           // session.removeAttribute("message");
            return "signup";
        }catch (Exception e){
           e.printStackTrace();
           model.addAttribute("user",user);

           session.setAttribute("message",new Message("Something Went Wrong"+e.getMessage()
                   ,"alert-danger"));
           // session.removeAttribute("message");
           return "signup";
        }

    }
    

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About-Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("title","Login-Smart Contact Manager");
        return "login";
    }

//    @RequestMapping("/logout")
//    public String logout(){
//        return "logout";
//    }



}
