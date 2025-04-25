package com.kiago.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {

    private Long id;

    @NotNull(message = "Debe estar asociada a un lugar")
    private Long placeId;

    @NotBlank(message = "La URL de la foto es obligatoria")
    private String photoUrl;
}

