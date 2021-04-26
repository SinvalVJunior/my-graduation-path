package com.mgp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassGetDTO {
    private Long id;
    private String name;
    private Integer hours;
    private Boolean done;
    private Boolean added;

}