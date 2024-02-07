package com.smartcontact.config;


import com.smartcontact.model.User;
import com.smartcontact.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.getUserByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("User Not Found");
        }
        UserDetailImpl userDetail=new UserDetailImpl(user);
        return userDetail;
    }
}
