package com.sadad.ib.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadad.ib.AuthenticationRequest;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            AuthenticationRequest creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthenticationRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        User user = (User) auth.getPrincipal();
        String token=Base64.getEncoder().encodeToString((user.getUsername() + "|" + req.getHeader("password")).getBytes());

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jwtToken", token);
        out.print(jsonObject);
        out.flush();
    }
}