package com.rbt.datasearch.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vacation")
public class VacationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer year;
    private Integer days;
    private Integer usedDays;
    private Integer freeDays;

   @ManyToOne
   @JsonBackReference
   private EmployeeEntity employee;

   @OneToMany(fetch = FetchType.EAGER)
   @JsonManagedReference
   private List<UsedVacationEntity> usedVacationEntities;
}
