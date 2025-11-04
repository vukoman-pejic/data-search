package com.rbt.datasearch.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeVacationUsedDaysRequest {
    private Integer year;          // Year of vacation
    private String startDate;      // Start date in "MMM d, yyyy" format
    private String endDate;        // End date in "MMM d, yyyy" format
    private String reason;         // Optional: reason for vacation
    private String email;          // Employee email
    private Long departmentId;     // Optional: employee's department
}
