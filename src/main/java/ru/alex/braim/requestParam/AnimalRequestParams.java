package ru.alex.braim.requestParam;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.alex.braim.annotation.EnumValue;
import ru.alex.braim.utils.enums.GenderEnum;
import ru.alex.braim.utils.enums.LifeStatusEnum;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class AnimalRequestParams extends DateRequestParams {
    @Nullable
    @EnumValue(enumClass = GenderEnum.class, message = "gender not contains in genders")
    private String gender;

    @Nullable
    @EnumValue(enumClass = LifeStatusEnum.class, message = "life status not contains in lifeStatus")
    private String lifeStatus;

    @Min(1)
    private Integer chipperId;

    @Min(1)
    private Long chippingLocationId;

}
