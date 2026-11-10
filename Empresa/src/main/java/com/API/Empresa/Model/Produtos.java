package com.API.Empresa.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@AllArgsConstructor
public class Produtos {

    @NotBlank
    private String Nome;

    @NotBlank
    private String Categoria;

    @NotBlank
    private String Codigo;

    @NotBlank
    private String Preco;

    @NotNull
    @PositiveOrZero
    private Integer Estoque;

    @NotBlank
    private String Unidade;

}
