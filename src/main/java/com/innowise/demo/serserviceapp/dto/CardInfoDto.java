package com.innowise.demo.serserviceapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoDto {
    private Long id;

    @NotNull(message = "User is required")
    private UserDto user;

    @NotBlank(message = "Number is required")
    private String number;

    @NotBlank(message = "Holder is required")
    private String holder;

    @NotNull(message = "Expiration date is required")
    private LocalDate expirationDate;
}
