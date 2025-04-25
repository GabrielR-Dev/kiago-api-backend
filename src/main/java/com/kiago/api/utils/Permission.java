package com.kiago.api.utils;

import com.kiago.api.entities.Usuario;

public interface Permission<T> {
    boolean authorizeAction(T recurso, Usuario usuario);

}
