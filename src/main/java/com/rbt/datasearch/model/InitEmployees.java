package com.rbt.datasearch.model;

import com.rbt.datasearch.entity.EmployeeEntity;
import com.rbt.datasearch.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Set;

//@Component
@RequiredArgsConstructor
public class InitEmployees implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

            EmployeeEntity u = employeeService.save(EmployeeEntity.builder()
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("test123"))
                    .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                    .build());
//            u.setEnabled(true);
            employeeService.save(u);

        EmployeeEntity u2 = employeeService.save(EmployeeEntity.builder()
                .email("admin1@test.com")
                .password(passwordEncoder.encode("test123"))
                .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                .build());
//            u.setEnabled(true);
        employeeService.save(u2);

            EmployeeEntity u1 = employeeService.save(EmployeeEntity.builder()
                    .email("user@test.com")
                    .password(passwordEncoder.encode("test123"))
                    .role(Set.of(Role.ROLE_USER))
                    .build());
//            u.setEnabled(true);
            employeeService.save(u1);

    }

}
