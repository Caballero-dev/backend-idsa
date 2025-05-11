package com.api.idsa.security.config;

import com.api.idsa.domain.personnel.model.PersonEntity;
import com.api.idsa.domain.personnel.model.RoleEntity;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IRoleRepository;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.api.idsa.security.provider.EmailTokenProvider;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class DataInitializationConfig {

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.name}")
    private String adminName;

    @Value("${app.admin.firstSurname}")
    private String adminFirstSurname;

    @Value("${app.admin.secondSurname}")
    private String adminSecondSurname;

    @Value("${app.admin.phoneNumber}")
    private String adminPhoneNumber;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private EmailTokenProvider emailTokenProvider;

    @Autowired
    private MailService mailService;

    @PostConstruct
    public void initData() {
        if (!userRepository.existsByEmail(adminEmail)) {
            RoleEntity adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        RoleEntity role = new RoleEntity();
                        role.setRoleName("ROLE_ADMIN");
                        return roleRepository.save(role);
                    });

            UserEntity adminUser = new UserEntity();
            adminUser.setEmail(adminEmail);
            adminUser.setRole(adminRole);
            adminUser.setCreatedAt(ZonedDateTime.now());
            adminUser.setIsActive(false);
            adminUser.setIsVerifiedEmail(false);

            PersonEntity adminPerson = new PersonEntity();
            adminPerson.setName(adminName);
            adminPerson.setFirstSurname(adminFirstSurname);
            adminPerson.setSecondSurname(adminSecondSurname);
            adminPerson.setPhoneNumber(adminPhoneNumber);
            adminPerson.setUser(adminUser);
            adminUser.setPerson(adminPerson);

            userRepository.save(adminUser);

            String token = emailTokenProvider.generateVerificationToken(adminEmail, "EMAIL_VERIFICATION");
            mailService.sendVerificationEmail(adminEmail, token);
        }
    }

}
