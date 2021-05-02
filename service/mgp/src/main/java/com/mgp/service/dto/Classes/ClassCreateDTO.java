package com.mgp.service.dto.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassCreateDTO {
    private String name;
    private Integer hours;
    private List<Long> dependencies;

}
