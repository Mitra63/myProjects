package com.sadad.ib.security;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static com.sadad.ib.security.SecurityConstants.HEADER_STRING;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(authManager);
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        Optional<String> header = Optional.ofNullable(req.getHeader(HEADER_STRING));

        if (!header.isPresent()) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        String decodedToken = null;
        if (!StringUtils.isEmpty(token)) {
            try {
                decodedToken = new String(Base64.getDecoder().decode(token));
            } catch (Exception e) {
                log.error("Spring Security Authorization filter Exception: Token is invalid");
                return null;
            }
            String username = decodedToken.substring(0, decodedToken.indexOf("|"));
            String password = decodedToken.substring(decodedToken.indexOf("|") + 1);
            if (!StringUtils.isEmpty(username)) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (!bCryptPasswordEncoder.matches(password,userDetails.getPassword())) {
                    log.error("Spring Security Authorization filter Exception: Token is invalid");
                    return null;
                }
                return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword()
                        , userDetails.getAuthorities());
            }
        }
        return null;
    }

}
