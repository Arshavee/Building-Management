package com.emse.spring.faircorp.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/hello")
@Transactional
public class SecurityController {


    @RestController
    public class securityController {

        @GetMapping("/secure")
        public String findUserName(@AuthenticationPrincipal UserDetails userDetails) {
            return userDetails.getUsername();
        }
    }
}
