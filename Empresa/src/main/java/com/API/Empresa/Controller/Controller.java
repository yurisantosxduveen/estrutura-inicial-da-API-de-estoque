package com.API.Empresa.Controller;

import com.API.Empresa.DTO.ProdutoCadastroDTO;
import com.API.Empresa.DTO.ProdutoRespostaDTO;
import com.API.Empresa.Model.Produtos;
import com.API.Empresa.Service.Service;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/Produtos")
    public List<Produtos> produtos() {
        return service.produtos();
    }

    // =================================================== //

    @GetMapping("/Produto/codigo/{codigo}")
    public Produtos buscar(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    // =================================================== //

    @GetMapping("/Produto/categoria/{categoria}")
    public List<Produtos> BuscarCategoria(@PathVariable String categoria) {
        return service.buscarPorCategoria(categoria);
    }

    // =================================================== //

    @DeleteMapping("/Produto/delete/{codigo}")
    public String deletar(@PathVariable String codigo) {

        boolean removido = service.deletarPorCodigo(codigo);

        if (removido) {
            return "Produto excluído com sucesso";
        }

        return "Produto não encontrado";
    }

    // ==================================================== //

    @DeleteMapping("/Produtos/invalidos")
    public String removerProdutosVazios() {

        service.removerProdutosVazios();

        return "Produtos vazios removidos!";
    }

    // ===================================================== //

    @PutMapping("/atualizar/{codigo}")
    public Produtos atualizar(
            @PathVariable String codigo,
            @RequestBody Produtos novosDados) {

        return service.atualizarProduto(codigo, novosDados);
    }

    // ===================================================== //

    @PostMapping("/Cadastrar")
    public Produtos cadastrarProduto(@RequestBody Produtos produto) {
        return service.cadastrarProduto(produto);
    }

    // ===================================================== //

    @PatchMapping("/Produto/{codigo}/Estoque")
    public Produtos atualizarEstoque(
            @PathVariable String codigo,
            @RequestBody Map<String, Integer> dados) {

        return service.atualizarEstoque(
                codigo,
                dados.get("Estoque")
        );

        // =================================================== //

    }

    @GetMapping("/Produtos/Faixa-preco")
    public List<Produtos> BuscarPorFaixa(

            @RequestParam String min,
            @RequestParam String max) {

        return service.BuscarPorFaixaDePreco(min, max);
    }

    // ======================================================== //

    @GetMapping("/Produtos/Ordenar")
    public List<Produtos> ordenarprodutos(
            @RequestParam String campo,
            @RequestParam String direcao) {

        return service.ordenarProdutos(campo, direcao);
    }

    @PostMapping("/Produtos")
    public ProdutoRespostaDTO cadastrarProduto(
            @Valid @RequestBody ProdutoCadastroDTO dto) {

        return service.cadastrarProduto(dto);
    }

    @GetMapping("/Produtos/Paginados")
    public Page<Produtos> listarProdutosPaginados(Pageable pageable) {

        return service.listarProdutosPaginados(pageable);
    }

}