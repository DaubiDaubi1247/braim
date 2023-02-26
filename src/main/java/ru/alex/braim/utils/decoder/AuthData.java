package ru.alex.braim.utils.decoder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthData {
    private String email;
    private String password;
}
