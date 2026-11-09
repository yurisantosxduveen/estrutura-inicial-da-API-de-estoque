package com.API.Empresa.Model;

import lombok.*;

@Data
@AllArgsConstructor
public class Produtos {

    private String Nome;
    private String Categoria;
    private String Codigo;
    private String Preco;
    private Integer Estoque;
    private String Unidade;

}
