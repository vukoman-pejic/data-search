package com.rbt.datasearch.service;

import com.rbt.datasearch.entity.EmployeeEntity;
import com.rbt.datasearch.entity.UsedVacationEntity;
import com.rbt.datasearch.entity.VacationEntity;
import com.rbt.datasearch.model.EmployeeUsedVacationResponse;
import com.rbt.datasearch.model.EmployeeVacationResponse;
import com.rbt.datasearch.model.EmployeeVacationUsedDaysRequest;
import com.rbt.datasearch.model.EmployeeVacationUsedForPeriodRequest;
import com.rbt.datasearch.repository.EmployeeRepository;
import com.rbt.datasearch.repository.UsedVacationDaysRepository;
import com.rbt.datasearch.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UsedVacationDaysRepository usedVacationDaysRepository;
    private final VacationRepository vacationRepository;

    public EmployeeEntity save(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeEntity findJwtUserByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found."));
    }

    public EmployeeVacationResponse getEmployeeVacationInfo() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String email = principal.getName();
        EmployeeEntity employee = employeeRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(""));
        return EmployeeVacationResponse.builder()
                .vacations(employee.getVacations())
                .build();
    }

    public void addUsedDaysForEmployee(EmployeeVacationUsedDaysRequest employeeVacationUsedDaysRequest) throws ParseException {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String email = principal.getName();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        Date startDate = sdf.parse(employeeVacationUsedDaysRequest.getStartDate());
        Date endDate = sdf.parse(employeeVacationUsedDaysRequest.getEndDate());

        Long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        Long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        EmployeeEntity employeeEntity = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found."));

        List<VacationEntity> vacations = employeeEntity.getVacations();
        VacationEntity currentVacation = null;
        for(VacationEntity v: vacations) {
            if(v.getYear().equals(employeeVacationUsedDaysRequest.getYear())) {
                currentVacation = v;
            }
        }
        if (currentVacation == null) {
            currentVacation = VacationEntity.builder()
                    .year(employeeVacationUsedDaysRequest.getYear())
                    .days(0)
                    .freeDays(0)
                    .usedDays(0)
                    .employee(employeeEntity)
                    .usedVacationEntities(new ArrayList<>())
                    .build();
            vacationRepository.save(currentVacation);
            employeeEntity.getVacations().add(currentVacation);
        }

        UsedVacationEntity usedVacationEntity = UsedVacationEntity
                .builder()
                .startDate(startDate)
                .endDate(endDate)
                .year(employeeVacationUsedDaysRequest.getYear())
                .usedDays(days.intValue())
                .vacation(currentVacation)
                .build();
        usedVacationDaysRepository.save(usedVacationEntity);

        currentVacation.getUsedVacationEntities().add(usedVacationEntity);
        vacationRepository.save(currentVacation);
    }

    public EmployeeUsedVacationResponse getEmployeeVacationUsedPeriod(EmployeeVacationUsedForPeriodRequest employeeVacationUsedForPeriodRequest) throws ParseException {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String email = principal.getName();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        Date startDate = sdf.parse(employeeVacationUsedForPeriodRequest.getStartDate());
        Date endDate = sdf.parse(employeeVacationUsedForPeriodRequest.getEndDate());

        EmployeeEntity employeeEntity = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found."));

        LocalDate startLocalDate = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endLocalDate = endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        List<Integer> listOfYears = new ArrayList<>();
        for (int i = startLocalDate.getYear(); i <= endLocalDate.getYear(); i++) {
            listOfYears.add(i);
        }

        List<VacationEntity> vacations = employeeEntity.getVacations();
        List<UsedVacationEntity> usedVacations = new ArrayList<>();
        for(VacationEntity v:vacations) {
            if (listOfYears.contains(v.getYear())) {
                for (UsedVacationEntity uv: v.getUsedVacationEntities()) {
                    usedVacations.add(uv);
                }
            }
        }
        assert usedVacations != null;
        EmployeeUsedVacationResponse usedVacationResponse = new EmployeeUsedVacationResponse();
        for(UsedVacationEntity uv: usedVacations) {
            if(startDate.before(uv.getStartDate()) && endDate.after(uv.getEndDate())) {
                usedVacationResponse.getUsedVacations().add(uv);
            }
        }
        return usedVacationResponse;
    }
}
