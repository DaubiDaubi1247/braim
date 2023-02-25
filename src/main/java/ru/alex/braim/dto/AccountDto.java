package ru.alex.braim.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class AccountDto {
    private Long id;

    @NotBlank(message = "firstName cant be empty")
    private String firstName;

    @NotBlank(message = "lastName cant be empty")
    private String lastName;

    @Email(message = "email doesnt matches pattern")
    @NotBlank(message = "email cant be empty")
    private String email;

}
