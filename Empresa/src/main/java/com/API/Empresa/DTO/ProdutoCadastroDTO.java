package com.API.Empresa.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoCadastroDTO(

        @NotBlank
        String nome,

        @NotBlank
        String categoria,

        @NotBlank
        String codigo,

        @NotBlank
        String preco,

        @NotNull
        @PositiveOrZero
        Integer estoque,

        @NotBlank
        String unidade
) {
}
