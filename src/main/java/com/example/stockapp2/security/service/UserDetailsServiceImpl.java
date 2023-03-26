package com.example.stockapp2.security.service;

import com.example.stockapp2.models.User;
import com.example.stockapp2.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BaseServiceImpl baseServiceImpl;

    @Autowired
    public UserDetailsServiceImpl(BaseServiceImpl baseServiceImpl) {
        this.baseServiceImpl = baseServiceImpl;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
        User user = baseServiceImpl.getUserByEmailOrPhoneNumber(emailOrPhoneNumber);
        return UserDetailsImpl.build(user);
    }

}
