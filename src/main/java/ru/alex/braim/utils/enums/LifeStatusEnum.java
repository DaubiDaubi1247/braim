package ru.alex.braim.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LifeStatusEnum {
    ALIVE("ALIVE"),
    DEAD("DEAD"),
    ;
    private final String lifeStatus;
}
