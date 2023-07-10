package com.br.accommodation.dto.guest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGuestDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 11 , max = 11)
    private String document;

    @NotNull
    @NotBlank
    @Size(min = 10 , max = 11)
    private String phone;

}
