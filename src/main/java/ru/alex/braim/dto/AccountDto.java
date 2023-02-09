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

    @NotBlank(message = "фамилия не должна быть пустым")
    private String firstName;

    @NotBlank(message = "имя не должно быть пустым")
    private String lastName;

    @Email(message = "почта не соотвествует шаблону")
    private String email;
}
