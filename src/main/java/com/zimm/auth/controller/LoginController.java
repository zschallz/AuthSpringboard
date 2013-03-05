package com.zimm.auth.controller;

import com.zimm.auth.services.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class LoginController {
 
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
            CustomUserDetails user = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String name = user.getUsername();

            model.addAttribute("username", name);
            model.addAttribute("message", "You're now logged in. Yay!");
            return "welcome";
 	}
 
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String login(ModelMap model) {
            return "login";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
            model.addAttribute("error", "true");
            return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
            return "login";
	}
        
//        @RequestMapping(value="/register", method = RequestMethod.GET)
//	public String register(ModelMap model) {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            
//            if( auth.getPrincipal().equals("anonymousUser") ) {
//                return "register";
//            }
//            else {
//                if(auth.getPrincipal() instanceof CustomUserDetails)
//                {
//                    model.addAttribute("username", ((CustomUserDetails)auth.getPrincipal()).getUsername());
//                    model.addAttribute("message", "You're already logged in, so that means you're already registered!");
//                }
//                return "welcome";
//            }
// 
//	}
//        
//        @RequestMapping(value="/add", method = RequestMethod.PUT)
//	public String newUser(ModelMap model) {
//            
//            return "welcome";
//        }
	
}