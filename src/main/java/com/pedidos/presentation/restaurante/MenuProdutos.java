package com.pedidos.presentation.restaurante;

import com.pedidos.application.service.CategoriaService;
import com.pedidos.application.service.ProdutoService;
import com.pedidos.domain.model.CategoriaCardapio;
import com.pedidos.domain.model.Produto;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuProdutos {
    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final Scanner scanner;

    public MenuProdutos(ProdutoService produtoService, CategoriaService categoriaService, Scanner scanner) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
        this.scanner = scanner;
    }

    public void exibir(com.pedidos.domain.model.Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("RESTAURANTE | PRODUTOS");
            System.out.println("1 - Listar Produtos");
            System.out.println("2 - Adicionar Produto");
            System.out.println("3 - Editar Produto");
            System.out.println("4 - Ativar/Inativar Produto");
            System.out.println("5 - Remover Produto");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 5);

            switch (opcao) {
                case 1:
                    try {
                        List<Produto> produtos = produtoService.listarPorRestaurante(restauranteLogado.getId());
                        if (produtos.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            for (int i = 0; i < produtos.size(); i++) {
                                Produto p = produtos.get(i);
                                String status = p.isStatusAtivo() ? "ATIVO" : "INATIVO";
                                String preco = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(p.getPreco());
                                System.out.println((i + 1) + " - " + p.getNome()
                                        + " | " + p.getDescricao()
                                        + " | " + preco
                                        + " | Categoria: " + p.getCategoriaCardapioId()
                                        + " | " + status);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        List<CategoriaCardapio> categorias = categoriaService.listarCategoriasCardapio(restauranteLogado.getId());
                        if (categorias.isEmpty()) {
                            System.out.println("Cadastre uma categoria antes de adicionar produtos.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.print("Nome do produto: ");
                        String nome = scanner.nextLine();

                        System.out.print("Descrição (opcional): ");
                        String descricao = scanner.nextLine();

                        System.out.print("Preço (ex: 12,90): ");
                        String precoStr = scanner.nextLine();
                        BigDecimal preco;
                        try {
                            preco = new BigDecimal(precoStr.replace(",", "."));
                        } catch (Exception e) {
                            System.out.println("Preço inválido.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.println("Categorias disponíveis:");
                        for (int i = 0; i < categorias.size(); i++) {
                            System.out.println((i + 1) + " - " + categorias.get(i).getNome());
                        }
                        System.out.print("Escolha o número da categoria: ");
                        int numCat = EntradaSegura.lerOpcao(scanner, 1, categorias.size());
                        String categoriaId = categorias.get(numCat - 1).getId();

                        produtoService.criarProduto(nome, descricao, preco, categoriaId, restauranteLogado.getId());
                        System.out.println("Produto " + nome + " cadastrado com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        List<Produto> produtos = produtoService.listarPorRestaurante(restauranteLogado.getId());
                        if (produtos.isEmpty()) {
                            System.out.println("Nenhum produto para editar.");
                            TerminalUtils.pausar();
                            break;
                        }

                        for (int i = 0; i < produtos.size(); i++) {
                            Produto p = produtos.get(i);
                            System.out.println((i + 1) + " - " + p.getNome());
                        }
                        System.out.print("Escolha o número do produto: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, produtos.size());
                        Produto selecionado = produtos.get(num - 1);

                        System.out.print("Nome atual: " + selecionado.getNome() + ". Novo nome: ");
                        String novoNome = scanner.nextLine();

                        System.out.print("Descrição atual: " + selecionado.getDescricao() + ". Nova descrição: ");
                        String novaDescricao = scanner.nextLine();

                        System.out.print("Preço atual: " + selecionado.getPreco() + ". Novo preço (ex: 12,90): ");
                        String precoStr = scanner.nextLine();
                        BigDecimal novoPreco = null;
                        if (!precoStr.isBlank()) {
                            try {
                                novoPreco = new BigDecimal(precoStr.replace(",", "."));
                            } catch (Exception e) {
                                System.out.println("Preço inválido.");
                                TerminalUtils.pausar();
                                break;
                            }
                        }

                        List<CategoriaCardapio> categorias = categoriaService.listarCategoriasCardapio(restauranteLogado.getId());
                        System.out.println("Categorias disponíveis:");
                        for (int i = 0; i < categorias.size(); i++) {
                            System.out.println((i + 1) + " - " + categorias.get(i).getNome());
                        }
                        System.out.print("Escolha o número da categoria: ");
                        int numCat = EntradaSegura.lerOpcao(scanner, 1, categorias.size());
                        String novaCategoriaId = categorias.get(numCat - 1).getId();

                        produtoService.editarProduto(selecionado.getId(), restauranteLogado.getId(), novoNome, novaDescricao, novoPreco, novaCategoriaId);
                        System.out.println("Produto atualizado com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        List<Produto> produtos = produtoService.listarPorRestaurante(restauranteLogado.getId());
                        if (produtos.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                            TerminalUtils.pausar();
                            break;
                        }

                        for (int i = 0; i < produtos.size(); i++) {
                            Produto p = produtos.get(i);
                            String status = p.isStatusAtivo() ? "ATIVO" : "INATIVO";
                            System.out.println((i + 1) + " - " + p.getNome() + " | " + status);
                        }
                        System.out.print("Escolha o número do produto: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, produtos.size());
                        Produto selecionado = produtos.get(num - 1);

                        String statusAtual = selecionado.isStatusAtivo() ? "ATIVO" : "INATIVO";
                        String acao = selecionado.isStatusAtivo() ? "inativar" : "ativar";
                        System.out.print("Produto está " + statusAtual + ". Deseja " + acao + "? (S/N): ");
                        String confirmacao = scanner.nextLine();

                        if (confirmacao.equalsIgnoreCase("S")) {
                            produtoService.ativarInativar(selecionado.getId(), restauranteLogado.getId());
                            System.out.println("Status do produto alterado com sucesso.");
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 5:
                    try {
                        List<Produto> produtos = produtoService.listarPorRestaurante(restauranteLogado.getId());
                        if (produtos.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                            TerminalUtils.pausar();
                            break;
                        }

                        for (int i = 0; i < produtos.size(); i++) {
                            System.out.println((i + 1) + " - " + produtos.get(i).getNome());
                        }
                        System.out.print("Escolha o número do produto: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, produtos.size());
                        Produto selecionado = produtos.get(num - 1);

                        System.out.print("Tem certeza? Esta ação é irreversível. (S/N): ");
                        String confirmacao = scanner.nextLine();
                        if (confirmacao.equalsIgnoreCase("S")) {
                            produtoService.removerProduto(selecionado.getId(), restauranteLogado.getId());
                            System.out.println("Produto removido permanentemente.");
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    TerminalUtils.pausar();
            }
        }
    }
}