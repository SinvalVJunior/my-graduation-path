package com.mgp.service.dto.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassCreateDTO {
    private String name;
    private Integer hours;
    private Boolean done;
    private Boolean added;
}
