package com.webfm.tennis;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Rank(
        @Positive(message = "Position must be a positive number") int position,
        @PositiveOrZero(message = "Points must be positive or equal to zero.") int points) {
}
