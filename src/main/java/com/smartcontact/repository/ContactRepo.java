package com.smartcontact.repository;

import com.smartcontact.model.Contact;
import com.smartcontact.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contact,Integer> {

    @Query(value = "select * from contact as c where c.user_id =:userid",nativeQuery = true)
    public List<Contact> getContactByUserId(@Param("userid") Integer userid);
}
