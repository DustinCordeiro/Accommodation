package com.br.accommodation.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponseDto {

    private List<CheckoutDetailsDto> details;
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
