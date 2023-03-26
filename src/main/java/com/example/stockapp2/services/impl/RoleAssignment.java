package com.example.stockapp2.services.impl;

import com.example.stockapp2.constants.AuthoritiesConstants;
import com.example.stockapp2.exceptions.ApiResourceNotFoundException;
import com.example.stockapp2.models.Authority;
import com.example.stockapp2.repositories.AuthorityRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleAssignment {

    public Set<Authority> assignRole(Set<String> userList, AuthorityRepository roleRepository) {

        Set<Authority> roles = new HashSet<>();

        if (userList == null) {

            Authority user = roleRepository.findByName(AuthoritiesConstants.USER)
                    .orElseThrow(() -> new ApiResourceNotFoundException("No Such Role"));
            roles.add(user);

        } else {
            userList.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "role_user":
                        Authority user = roleRepository.findByName(AuthoritiesConstants.USER)
                                .orElseThrow(() -> new ApiResourceNotFoundException("Error: Role not found"));
                        roles.add(user);
                        break;
                    default:
                        Authority defaultAuthority = roleRepository.findByName(AuthoritiesConstants.USER)
                                .orElseThrow(() -> new ApiResourceNotFoundException("Error: Role not found"));
                        roles.add(defaultAuthority);
                        break;
                }
            });
        }
        return roles;
    }
}
