package com.rbt.datasearch.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeVacationUsedDaysRequest {
    private Integer year;
    private String startDate;
    private String endDate;
}
