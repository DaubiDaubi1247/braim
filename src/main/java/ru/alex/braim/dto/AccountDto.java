package ru.alex.braim.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {
    private Long id;

    @NotBlank(message = "firstName cant be empty")
    private String firstName;

    @NotBlank(message = "lastName cant be empty")
    private String lastName;

    @Email(message = "email doesnt matches pattern")
    private String email;

    @NotBlank(message = "password cant be empty")
    private String password;
}
