package com.webfm.tennis;

import java.time.LocalDate;

public record Player(String firstName, String lastName, LocalDate birthDate, Rank rank) {



}
