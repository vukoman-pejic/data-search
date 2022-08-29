package com.rbt.datasearch.model;

import com.rbt.datasearch.entity.EmployeeEntity;
import com.rbt.datasearch.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitAdmin implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

            EmployeeEntity u = employeeService.save(EmployeeEntity.builder()
                    .email("admin@rbt.rs")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                    .build());
            employeeService.save(u);
    }
}
