package com.mgp.service.dto.Colleges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeUpdateDTO {
    private Long id;
    private String name;
    private String shortName;
    private String state;
    private String city;
    private List<Long> courses;
}
