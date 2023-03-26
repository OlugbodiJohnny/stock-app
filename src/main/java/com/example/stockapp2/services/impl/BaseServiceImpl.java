package com.example.stockapp2.services.impl;

import com.example.stockapp2.exceptions.ApiResourceNotFoundException;
import com.example.stockapp2.models.User;
import com.example.stockapp2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class BaseServiceImpl {

    private final UserRepository userRepository;

    @Value("${spring.admin.email}")
    private String adminEmail;


    public void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    } //0078192839



    public void copyNonNullNonEmptyAndNonZeroProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullEmptyAndNonZeroPropertyNames(src));
    }

    private String[] getNullEmptyAndNonZeroPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || srcValue.equals(0) || srcValue == " ") emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


//    public String getUserPhoneNumber(User user) {
//        List<String> roles = user.getAuthorities()
//                .stream()
//                .map(Authority::getName)
//                .collect(Collectors.toList());
//
//        String phoneNumber = "";
//
//        // admin
//        if (roles.size() > 1) return getAdminByUser(user).getPhoneNumber();
//
//        // other users
//        if (roles.get(0).equals("ROLE_PATIENT")) phoneNumber = getPatientByUser(user).getPhoneNumber();
//        if (roles.get(0).equals("ROLE_DOCTOR")) phoneNumber = getDoctorByUser(user).getPhoneNumber();
//
//        return phoneNumber;
//    }

//    public User saveUser(User user) {
//        try {
//            userRepository.save(user);
//        } catch (Exception e) {
//            log.error("Error saving into the User db " + e.getMessage());
//        }
//        return user;
//    }

    public User getUserByEmailOrPhoneNumber(String emailOrPhoneNumber) {
        User user = userRepository.findByEmail(emailOrPhoneNumber).orElseThrow(()-> new ApiResourceNotFoundException("USER NOT FOUND"));
        return user;
    }
}
