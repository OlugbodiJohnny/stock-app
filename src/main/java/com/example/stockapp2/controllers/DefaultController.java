package com.example.stockapp2.controllers;

import com.example.stockapp2.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class DefaultController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${swagger-url}")
    private String swaggerUrl;

    @GetMapping("/")
    public String loginBasePath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("error");
        return "login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("error");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String handlePostRequest(String username, String password, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        Authentication authentication;
        HttpSession session = request.getSession();

        try {
            authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            session.setAttribute("error", "BAD CREDENTIALS");
            return "login";
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        session.setAttribute("USER", jwt);
        response.sendRedirect(swaggerUrl);

        return "403";
    }


}