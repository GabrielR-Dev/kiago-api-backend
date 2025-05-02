package com.kiago.api.dtos;

import com.kiago.api.entities.Place;
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

    public Long getId() {
        return id;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

