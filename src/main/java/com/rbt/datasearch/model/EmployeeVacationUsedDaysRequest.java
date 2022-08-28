package com.rbt.datasearch.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeVacationUsedDaysRequest {
    private String email;
    private Integer year;
    private String startDate;
    private String endDate;
}
