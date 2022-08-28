
package com.rbt.datasearch.controller;

import com.rbt.datasearch.entity.VacationEntity;
import com.rbt.datasearch.model.EmployeeUsedVacationResponse;
import com.rbt.datasearch.model.EmployeeVacationResponse;
import com.rbt.datasearch.model.EmployeeVacationUsedDaysRequest;
import com.rbt.datasearch.model.EmployeeVacationUsedForPeriodRequest;
import com.rbt.datasearch.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/search/{email}")
    public ResponseEntity<EmployeeVacationResponse> getEmployeeVacationInfo(@PathVariable String email) {
        EmployeeVacationResponse employeeVacationInfo = employeeService.getEmployeeVacationInfo(email);
        return new ResponseEntity<>(employeeVacationInfo, HttpStatus.OK);
    }

    @PostMapping("/add-used-days")
    public ResponseEntity<?> sendUsedVacationDays(@RequestBody EmployeeVacationUsedDaysRequest employeeVacationUsedDaysRequest) throws ParseException {
        employeeService.addUsedDaysForEmployee(employeeVacationUsedDaysRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search-from-to-date")
    public ResponseEntity<?> getNumberOfDaysUsedForPeriod(@RequestBody EmployeeVacationUsedForPeriodRequest employeeVacationUsedForPeriodRequest) throws ParseException {
        EmployeeUsedVacationResponse employeeUsedVacationResponse = employeeService.getEmployeeVacationUsedPeriod(employeeVacationUsedForPeriodRequest);
        return new ResponseEntity<>(employeeUsedVacationResponse, HttpStatus.OK);
    }
}
