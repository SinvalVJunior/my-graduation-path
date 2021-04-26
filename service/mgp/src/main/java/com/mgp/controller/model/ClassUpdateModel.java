package com.mgp.controller.model;

import com.mgp.constants.ParametersConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUpdateModel {
    @Size(
            min = ParametersConstants.Classes.MIN_NAMES_SIZE,
            max = ParametersConstants.Classes.MAX_NAMES_SIZE,
            message = "Name accepts at least " + ParametersConstants.Classes.MIN_NAMES_SIZE
                    + " characters and at most " + ParametersConstants.Classes.MAX_NAMES_SIZE + " character(s)."
    )
    private String name;
}
