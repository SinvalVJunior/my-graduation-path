package com.mgp.controller.model;

import com.mgp.constants.ParametersConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUpdateModel {
    @Size(
            min = ParametersConstants.Classes.NAMES_MIN_SIZE,
            max = ParametersConstants.Classes.NAMES_MAX_SIZE,
            message = "Name accepts at least " + ParametersConstants.Classes.NAMES_MIN_SIZE
                    + " characters and at most " + ParametersConstants.Classes.NAMES_MAX_SIZE + " character(s)."
    )
    private String name;

    @Min(value = ParametersConstants.Classes.HOURS_MIN_SIZE, message = "Hours must be at least " + ParametersConstants.Classes.HOURS_MIN_SIZE)
    @Max(value = ParametersConstants.Classes.HOURS_MAX_SIZE, message = "Hours must be at most " + ParametersConstants.Classes.HOURS_MAX_SIZE)
    private Integer hours;

    private Boolean done;

    private Boolean added;
}
