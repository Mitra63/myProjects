package com.sadad.ib.service;

import com.sadad.ib.entity.RestUser;
import com.sadad.ib.entity.Role;
import com.sadad.ib.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<RestUser> optionalUser = usersRepository.findByUsername(userName);
        if(optionalUser.isPresent()) {
            RestUser restUser = optionalUser.get();

            List<String> roleList = new ArrayList<>();
            for(Role role:restUser.getRoles()) {
                roleList.add(role.getRoleName());
            }

            return  User.builder()
                    .username(restUser.getUsername())
                    .password(restUser.getPassword())
                    .disabled(restUser.isDisabled())
                    .roles(roleList.toArray(new String[0]))
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
    }
}
