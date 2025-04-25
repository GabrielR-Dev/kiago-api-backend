package com.kiago.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;

    @NotBlank(message = "El nombre de la compañía es obligatorio")
    private String name;

    private String description;
}
