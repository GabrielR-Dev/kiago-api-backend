package com.kiago.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {

    private Long id;

    @NotNull(message = "Debe tener una compañía asociada")
    private Long companyId;

    @NotBlank(message = "El nombre del lugar es obligatorio")
    private String name;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @NotNull(message = "Debe tener una latitud")
    private BigDecimal latitude;

    @NotNull(message = "Debe tener una longitud")
    private BigDecimal longitude;

    private String description;

    public Long getPlaceId() {
        return id;
    }
}
