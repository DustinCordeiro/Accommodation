package com.br.accommodation.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDetailsDto {

    private LocalDate date;
    private String description;
    private BigDecimal amount = BigDecimal.ZERO;
}
