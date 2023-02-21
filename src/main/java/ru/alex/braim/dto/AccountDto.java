package ru.alex.braim.dto;

import jakarta.validation.constraints.Email;
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

    private String firstName;

    private String lastName;

    @Email(message = "почта не соотвествует шаблону")
    private String email;
}
