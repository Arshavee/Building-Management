package com.emse.spring.faircorp;


import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class doFilter {
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) {
        // do something before the rest of the application
        //chain.doFilter(request, response); // invoke the rest of the application
        // do something after the rest of the application
    }
}