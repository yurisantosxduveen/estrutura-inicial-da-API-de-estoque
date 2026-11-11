package com.API.Empresa.Service;

import com.API.Empresa.DTO.ProdutoCadastroDTO;
import com.API.Empresa.DTO.ProdutoRespostaDTO;
import com.API.Empresa.Model.Produtos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {

    private final List<Produtos> produtos =  new ArrayList<>();

    public Service() {

        produtos.add(new Produtos(
                "Parafuso Philips 4,5x40mm",
                "Parafusos",
                "7891000000022",
                "R$ 12,00",
                150,
                "Caixa"
        ));
        produtos.add(new Produtos(
                "Bucha nylon 8mm",
                "Buchas",
                "7891000000028",
                "R$ 8,00",
                200,
                "Pacote"
        ));
        produtos.add(new Produtos(
                "Parafuso sextavado 8mm",
                "Parafusos",
                "7891000000011",
                "R$ 1,50",
                50,
                "Pacote"
        ));
        produtos.add(new Produtos(
                "Parafuso para madeira 5mm",
                "Parafusos",
                "7891000000035",
                "R$ 10,00",
                40,
                "Pacote"
        ));
        produtos.add(new Produtos(
                "Porca sextavada 8mm",
                "Porcas",
                "7891000000042",
                "R$ 18,00",
                30,
                "Caixa"
        ));
        produtos.add(new Produtos(
                "Porca travante 10mm",
                "Porcas",
                "7891000000059",
                "R$ 25,00",
                20,
                "Caixa"
        ));
        produtos.add(new Produtos(
                "Arruela lisa 8mm",
                "Arruelas",
                "7891000000066",
                "R$ 8,00",
                60,
                "Pacote"
        ));
        produtos.add(new Produtos(
                "Arruela de pressão 10mm",
                "Arruelas",
                "7891000000073",
                "R$ 12,00",
                45,
                "Pacote"
        ));
        produtos.add(new Produtos(
                "Bucha nylon 10mm",
                "Buchas",
                "7891000000097",
                "R$ 14,00",
                35,
                "Pacote"
        ));
        produtos.add(new Produtos(
                "Chumbador parabolt 3/8",
                "Fixadores",
                "7891000000103",
                "R$ 45,00",
                15,
                "Caixa"
        ));
        produtos.add(new Produtos(
                "Barra roscada 1/4",
                "Barras",
                "7891000000110",
                "R$ 12,50",
                40,
                "Unidade"
        ));
    }


    public List<Produtos> produtos() {
        return produtos;
    }

    public Produtos buscarPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado")
                );
    }

    public List<Produtos> buscarPorCategoria(String categoria) {
        return produtos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }

    public boolean deletarPorCodigo(String codigo) {
        return produtos.removeIf(p -> p.getCodigo().equals(codigo));
    }

    public Produtos atualizarProduto(String codigo, Produtos novosDados) {

        for (Produtos p : produtos) {

            if (p.getCodigo().equals(codigo)) {

                p.setNome(novosDados.getNome());
                p.setCategoria(novosDados.getCategoria());
                p.setPreco(novosDados.getPreco());
                p.setEstoque(novosDados.getEstoque());
                p.setUnidade(novosDados.getUnidade());

                return p;
            }
        }

        throw new RuntimeException("Produto não encontrado");
    }

    public Produtos cadastrarProduto(Produtos produto) {
        produtos.add(produto);
        return produto;
    }

    public void removerProdutosVazios() {

        produtos.removeIf(p ->
                p == null ||
                        (p.getNome() == null &&
                                p.getCategoria() == null &&
                                p.getCodigo() == null &&
                                p.getPreco() == null &&
                                p.getUnidade() == null)
        );
    }

    public Produtos atualizarEstoque(String codigo, Integer estoque) {

        for (Produtos p : produtos) {

            if (p.getCodigo().equals(codigo)) {

                p.setEstoque(estoque);

                return p;
            }
        }

        throw new RuntimeException("Produto não encontrado");
    }

    public List<Produtos> BuscarPorFaixaDePreco(String minimo, String maximo) {

        Double valorminimo = Double.parseDouble(minimo);
        Double valormaximo = Double.parseDouble(maximo);

        return produtos.stream()
                .filter(p -> {

                    String preco = p.getPreco();

                    preco = preco.replace("R$", "")
                            .trim()
                            .replace(",", ".");

                    Double valorPreco = Double.parseDouble(preco);

                    return valorPreco >= valorminimo &&
                            valorPreco <= valormaximo;
                })
                .collect(Collectors.toList());
    }

    public List<Produtos> ordenarProdutos(String campo, String direcao) {

        Comparator<Produtos> comparator;

        switch (campo.toLowerCase()) {

            case "nome":
                comparator = Comparator.comparing(
                        Produtos::getNome,
                        String.CASE_INSENSITIVE_ORDER
                );
                break;

            case "preco":
                comparator = Comparator.comparing(p -> Double.parseDouble(
                        p.getPreco()
                                .replace("R$", "")
                                .trim()
                                .replace(".", "")
                                .replace(",", ".")
                    )
                );
                break;

            case "estoque":
                comparator = Comparator.comparing(Produtos::getEstoque);
                break;

            default:
                throw new IllegalArgumentException("Campo inválido para ordenação");
        }

        if (direcao.equalsIgnoreCase("desc")) {

            comparator = comparator.reversed();
        } else if (!direcao.equalsIgnoreCase("asc")) {
            throw new IllegalArgumentException("Direção inválida");
        }

        return produtos.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public ProdutoRespostaDTO cadastrarProduto(ProdutoCadastroDTO dto) {

        Produtos produto = new Produtos(

                dto.nome(),
                dto.categoria(),
                dto.codigo(),
                dto.preco(),
                dto.estoque(),
                dto.unidade());


        produtos.add(produto);

        return new ProdutoRespostaDTO(
                produto.getNome(),
                produto.getCategoria(),
                produto.getCodigo(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getUnidade()
        );

    }

    public Page<Produtos> listarProdutosPaginados(Pageable pageable) {

        List<Produtos> listaOrdenada = new ArrayList<>(produtos);

        if (pageable.getSort().isSorted()) {

            Sort.Order ordem = pageable.getSort().iterator().next();

            listaOrdenada = ordenarProdutos(
                    ordem.getProperty(),
                    ordem.getDirection().name()
            );
        }

        int inicio = (int) pageable.getOffset();

        if (inicio > listaOrdenada.size()) {
            return Page.empty(pageable);
        }

        int fim = Math.min(
                inicio + pageable.getPageSize(),
                listaOrdenada.size()
        );

        List<Produtos> conteudo = listaOrdenada.subList(inicio, fim);

        return new PageImpl<>(
                conteudo,
                pageable,
                listaOrdenada.size()
        );

    }

}


























