package com.mgp.controller.model.Classes;

import com.mgp.constants.ModelsConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUpdateModel {
    @Size(
            min = ModelsConstants.Classes.NAMES_MIN_SIZE,
            max = ModelsConstants.Classes.NAMES_MAX_SIZE,
            message = "Name accepts at least " + ModelsConstants.Classes.NAMES_MIN_SIZE
                    + " characters and at most " + ModelsConstants.Classes.NAMES_MAX_SIZE + " character(s)."
    )
    private String name;

    @Min(value = ModelsConstants.Classes.HOURS_MIN_SIZE, message = "Hours must be at least " + ModelsConstants.Classes.HOURS_MIN_SIZE)
    @Max(value = ModelsConstants.Classes.HOURS_MAX_SIZE, message = "Hours must be at most " + ModelsConstants.Classes.HOURS_MAX_SIZE)
    private Integer hours;

    private List<Long> dependencies;

}
