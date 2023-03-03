package ru.alex.braim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.alex.braim.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OldAndNewTypes {

    @Id
    private Long oldType;

    @Id
    private Long newType;
}
