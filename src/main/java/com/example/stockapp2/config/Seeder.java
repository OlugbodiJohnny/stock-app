package com.example.stockapp2.config;

import com.example.stockapp2.constants.AuthoritiesConstants;
import com.example.stockapp2.models.Authority;
import com.example.stockapp2.repositories.AuthorityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Seeder implements CommandLineRunner {

    final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) {

        authorityRepository.findByName(AuthoritiesConstants.USER).ifPresentOrElse(r -> {
            log.info("Authority {} was found in database...",AuthoritiesConstants.USER);
        }, () -> {
            log.info("Seeding {} authority into database...",AuthoritiesConstants.USER);
            Authority merchantAuthority = new Authority();
            merchantAuthority.setName(AuthoritiesConstants.USER);
            authorityRepository.save(merchantAuthority);
        });
    }
}
