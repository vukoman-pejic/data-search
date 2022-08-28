package com.rbt.datasearch.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeVacationUsedForPeriodRequest {
    private String email;
    private String startDate;
    private String endDate;
}
