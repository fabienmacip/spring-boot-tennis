package com.webfm.tennis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record PlayerToSave(
        @NotBlank(message = "First name is mandatory") String firstName,
        @NotBlank(message = "Las name is mandatory") String lastName,
        @NotNull(message = "Birth date is mandatory") @PastOrPresent(message = "Birth date must be past or present")
        LocalDate birthDate,
        @PositiveOrZero(message = "Points must be equal to zero or more than zero") int points
        ) {
}
