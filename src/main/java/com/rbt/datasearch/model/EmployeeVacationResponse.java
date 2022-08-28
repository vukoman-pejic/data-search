package com.rbt.datasearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rbt.datasearch.entity.VacationEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class EmployeeVacationResponse {
    private List<VacationEntity> vacations;
}
