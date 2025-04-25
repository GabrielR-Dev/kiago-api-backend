package com.kiago.api.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    @NotNull(message = "Debe estar asociado a un lugar")
    private Long placeId;

    @NotNull(message = "Debe estar asociado a un usuario")
    private Long userId;

    @NotBlank(message = "El comentario no puede estar vacío")
    private String comment;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer rating;

    public Long getUserId() {
        return userId;
    }

    public Long getPlaceId() {
        return placeId;
    }
}
