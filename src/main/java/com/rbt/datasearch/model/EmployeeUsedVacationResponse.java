package com.rbt.datasearch.model;

import com.rbt.datasearch.entity.UsedVacationEntity;
import com.rbt.datasearch.entity.VacationEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class EmployeeUsedVacationResponse {
    private List<UsedVacationEntity> usedVacations = new ArrayList<>();
}
