package com.mgp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUpdateDTO {
    private String name;
    private Integer hours;
    private Boolean done;
    private Boolean added;
}
