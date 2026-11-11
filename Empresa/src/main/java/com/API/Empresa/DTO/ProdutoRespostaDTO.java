package com.API.Empresa.DTO;

public record ProdutoRespostaDTO(

        String nome,
        String categoria,
        String codigo,
        String preco,
        Integer estoque,
        String unidade

) {
}
