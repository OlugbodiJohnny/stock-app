package com.example.stockapp2.config;

import com.example.stockapp2.constants.AuthoritiesConstants;
import com.example.stockapp2.enums.UserType;
import com.example.stockapp2.models.Authority;
import com.example.stockapp2.models.User;
import com.example.stockapp2.repositories.AuthorityRepository;
import com.example.stockapp2.repositories.UserRepository;
import com.example.stockapp2.services.impl.RoleAssignment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Seeder implements CommandLineRunner {

    final AuthorityRepository authorityRepository;
    final UserRepository userRepository;
    final RoleAssignment roleAssignment;
    final PasswordEncoder encoder;

    @Value("${spring.admin.email}")
    private String adminEmail;

    @Value("${spring.admin.password}")
    private String adminPassword;

    @Value("${dev.email}")
    private String devEmail;

    @Value("${dev.password}")
    private String devPassword;

    @Override
    public void run(String... args) {

        authorityRepository.findByName(AuthoritiesConstants.USER).ifPresentOrElse(r -> {
            log.info("Authority {} was found in database...", AuthoritiesConstants.USER);
        }, () -> {
            log.info("Seeding {} authority into database...", AuthoritiesConstants.USER);
            Authority merchantAuthority = new Authority();
            merchantAuthority.setName(AuthoritiesConstants.USER);
            authorityRepository.save(merchantAuthority);
        });

        authorityRepository.findByName(AuthoritiesConstants.DEV).ifPresentOrElse(r -> {
            log.info("Authority {} was found in database...", AuthoritiesConstants.DEV);
        }, () -> {
            log.info("Seeding {} authority into database...", AuthoritiesConstants.DEV);
            Authority merchantAuthority = new Authority();
            merchantAuthority.setName(AuthoritiesConstants.DEV);
            authorityRepository.save(merchantAuthority);

            Set<Authority> rolesToBeGranted = roleAssignment.assignRole(
                    new HashSet<>(Collections.singletonList(AuthoritiesConstants.DEV)),
                    authorityRepository
            );

            var adminOptional = userRepository.findByEmail(devEmail);

            if (adminOptional.isEmpty()) {
                User dev = User
                        .builder()
                        .email(devEmail)
                        .password(encoder.encode(devPassword))
                        .activated(true)
                        .activationKey("key")
                        .authorities(rolesToBeGranted)
                        .deleted(false)
                        .emailVerified(true)
                        .userType(UserType.DEV)
                        .build();
                try {
                    userRepository.save(dev);
                } catch (Exception ex) {
                    log.info("DEV_USER_SEEDING_EXCEPTION");
                }
                log.info("Seeding user with {} authority into database...", AuthoritiesConstants.DEV);
            } else {
                log.info("User with {} authority was found in database...", AuthoritiesConstants.DEV);
            }
        });


    }
}
