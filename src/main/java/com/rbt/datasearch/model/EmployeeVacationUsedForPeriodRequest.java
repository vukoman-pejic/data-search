package com.rbt.datasearch.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeVacationUsedForPeriodRequest {
    private String startDate;
    private String endDate;
}
