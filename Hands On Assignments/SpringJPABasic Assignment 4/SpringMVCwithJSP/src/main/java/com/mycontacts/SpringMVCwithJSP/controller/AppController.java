package com.mycontacts.SpringMVCwithJSP.controller;

import com.mycontacts.SpringMVCwithJSP.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppController {

//    @RequestMapping("/")
//    public String welcome(){
//        return "index";
//    }

    @RequestMapping("/list_contact")
    public String listContact(Model model){
        ContactBusiness business = new ContactBusiness();
        List<Contact> contactList = business.getContactList();

        model.addAttribute("contacts", contactList);
        return "contact";
    }
}
