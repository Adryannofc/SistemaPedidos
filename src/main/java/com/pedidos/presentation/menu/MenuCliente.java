package com.pedidos.presentation.menu;

import com.pedidos.application.service.*;
import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.Cliente;
import com.pedidos.domain.model.Endereco;
import com.pedidos.domain.model.Pedido;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuCliente {

    private final Cliente clienteLogado;
    private final ClienteService clienteService;
    private final EnderecoService enderecoService;
    private final HistoricoService historicoService;
    private final Scanner scanner;
    private final PedidoService pedidoService;
    private final CarrinhoService carrinhoService;
    private final FavoritosService favoritosService;

    public MenuCliente(Cliente clienteLogado,
                       ClienteService clienteService,
                       EnderecoService enderecoService,
                       HistoricoService historicoService,
                       PedidoService pedidoService,
                       CarrinhoService carrinhoService,
                       FavoritosService favoritosService,
                       Scanner scanner) {
        this.clienteLogado    = clienteLogado;
        this.clienteService   = clienteService;
        this.enderecoService  = enderecoService;
        this.historicoService = historicoService;
        this.pedidoService    = pedidoService;
        this.carrinhoService  = carrinhoService;
        this.favoritosService = favoritosService;
        this.scanner          = scanner;
    }

    public void iniciar() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("MENU CLIENTE", "Ola, " + clienteLogado.getNome());

            System.out.println(TerminalUtils.TOPO);
            System.out.println(TerminalUtils.linha("  1  \u00bb  Meu Perfil"));
            System.out.println(TerminalUtils.linha("  2  \u00bb  Meus Enderecos"));
            System.out.println(TerminalUtils.linha("  3  \u00bb  Historico de Pedidos"));
            System.out.println(TerminalUtils.SEPARADOR);
            System.out.println(TerminalUtils.linha("  0  \u00bb  Sair (Logout)"));
            System.out.println(TerminalUtils.BASE);
            System.out.print("\n  Escolha uma opcao: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 3);

            switch (opcao) {
                case 1 -> menuPerfil();
                case 2 -> menuEnderecos();
                case 3 -> menuHistorico();
                case 0 -> {
                    return;
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RF-CLI-01 — Perfil
    // ─────────────────────────────────────────────────────────────────────────

    private void menuPerfil() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("PERFIL", clienteLogado.getNome());

            System.out.println(TerminalUtils.TOPO);
            System.out.println(TerminalUtils.linha("  1  \u00bb  Visualizar Perfil"));
            System.out.println(TerminalUtils.linha("  2  \u00bb  Editar Nome"));
            System.out.println(TerminalUtils.linha("  3  \u00bb  Editar E-mail"));
            System.out.println(TerminalUtils.linha("  4  \u00bb  Editar CPF"));
            System.out.println(TerminalUtils.linha("  5  \u00bb  Editar Telefone"));
            System.out.println(TerminalUtils.linha("  6  \u00bb  Alterar Senha"));
            System.out.println(TerminalUtils.SEPARADOR);
            System.out.println(TerminalUtils.linha("  0  \u00bb  Voltar"));
            System.out.println(TerminalUtils.BASE);
            System.out.print("\n  Escolha uma opcao: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 6);

            switch (opcao) {
                case 1 -> acaoVisualizarPerfil();
                case 2 -> acaoEditarNome();
                case 3 -> acaoEditarEmail();
                case 4 -> acaoEditarCpf();
                case 5 -> acaoEditarTelefone();
                case 6 -> acaoAlterarSenha();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void acaoVisualizarPerfil() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("VISUALIZAR PERFIL");
        try {
            clienteService.visualizarPerfil(clienteLogado);
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoEditarNome() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("EDITAR NOME");
        try {
            System.out.println("  Atual : " + clienteLogado.getNome());
            System.out.print("  Novo  : ");
            String novo = scanner.nextLine().trim();
            if (novo.isBlank()) {
                TerminalUtils.aviso("Nenhuma alteracao realizada.");
            } else {
                clienteService.editarNome(clienteLogado, novo);
                TerminalUtils.sucesso("Nome atualizado para: " + clienteLogado.getNome());
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoEditarEmail() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("EDITAR E-MAIL");
        try {
            System.out.println("  Atual : " + clienteLogado.getEmail());
            System.out.print("  Novo  : ");
            String novo = scanner.nextLine().trim();
            if (novo.isBlank()) {
                TerminalUtils.aviso("Nenhuma alteracao realizada.");
            } else {
                clienteService.editarEmail(clienteLogado, novo);
                TerminalUtils.sucesso("E-mail atualizado.");
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoEditarCpf() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("EDITAR CPF");
        try {
            System.out.println("  Atual : " + clienteLogado.getCpf());
            System.out.print("  Novo (11 digitos): ");
            String novo = scanner.nextLine().trim();
            if (novo.isBlank()) {
                TerminalUtils.aviso("Nenhuma alteracao realizada.");
            } else {
                clienteService.editarCpf(clienteLogado, novo);
                TerminalUtils.sucesso("CPF atualizado.");
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoEditarTelefone() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("EDITAR TELEFONE");
        try {
            System.out.println("  Atual : " + clienteLogado.getTelefone());
            System.out.print("  Novo  : ");
            String novo = scanner.nextLine().trim();
            if (novo.isBlank()) {
                TerminalUtils.aviso("Nenhuma alteracao realizada.");
            } else {
                clienteService.editarTelefone(clienteLogado, novo);
                TerminalUtils.sucesso("Telefone atualizado.");
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoAlterarSenha() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("ALTERAR SENHA");
        try {
            System.out.print("  Senha atual         : ");
            String atual = scanner.nextLine();
            System.out.print("  Nova senha          : ");
            String nova = scanner.nextLine();
            System.out.print("  Confirmar nova senha: ");
            String confirmacao = scanner.nextLine();

            clienteService.alterarSenha(clienteLogado, atual, nova, confirmacao);
            TerminalUtils.sucesso("Senha alterada com sucesso.");
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RF-CLI-03 — Endereços
    // ─────────────────────────────────────────────────────────────────────────

    private void menuEnderecos() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("MEUS ENDERECOS", clienteLogado.getNome());

            System.out.println(TerminalUtils.TOPO);
            System.out.println(TerminalUtils.linha("  1  \u00bb  Listar Enderecos"));
            System.out.println(TerminalUtils.linha("  2  \u00bb  Adicionar Endereco"));
            System.out.println(TerminalUtils.linha("  3  \u00bb  Editar Endereco"));
            System.out.println(TerminalUtils.linha("  4  \u00bb  Definir como Padrao"));
            System.out.println(TerminalUtils.linha("  5  \u00bb  Remover Endereco"));
            System.out.println(TerminalUtils.SEPARADOR);
            System.out.println(TerminalUtils.linha("  0  \u00bb  Voltar"));
            System.out.println(TerminalUtils.BASE);
            System.out.print("\n  Escolha uma opcao: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 5);

            switch (opcao) {
                case 1 -> acaoListarEnderecos();
                case 2 -> acaoAdicionarEndereco();
                case 3 -> acaoEditarEndereco();
                case 4 -> acaoDefinirPadrao();
                case 5 -> acaoRemoverEndereco();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void acaoListarEnderecos() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("MEUS ENDERECOS");
        try {
            List<Endereco> lista = enderecoService.listarEnderecosPorCliente(clienteLogado.getId());
            if (lista.isEmpty()) {
                TerminalUtils.aviso("Nenhum endereco cadastrado.");
            } else {
                exibirListaEnderecos(lista);
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoAdicionarEndereco() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("ADICIONAR ENDERECO");
        try {
            System.out.print("  Apelido (ex: Casa, Trabalho): ");
            String apelido = scanner.nextLine().trim();

            System.out.print("  Rua   : ");
            String rua = scanner.nextLine().trim();

            System.out.print("  Numero: ");
            String numero = scanner.nextLine().trim();

            System.out.print("  Bairro: ");
            String bairro = scanner.nextLine().trim();

            System.out.print("  Cidade: ");
            String cidade = scanner.nextLine().trim();

            System.out.print("  Complemento (Enter para pular): ");
            String complemento = scanner.nextLine().trim();

            Endereco criado = enderecoService.criarEndereco(
                    clienteLogado.getId(), apelido, rua, numero,
                    bairro, cidade, complemento.isBlank() ? null : complemento
            );

            String sufixo = criado.isPadrao() ? " (definido como padrao)" : "";
            TerminalUtils.sucesso("Endereco \"" + apelido + "\" cadastrado." + sufixo);
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoEditarEndereco() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("EDITAR ENDERECO");
        try {
            List<Endereco> lista = enderecoService.listarEnderecosPorCliente(clienteLogado.getId());
            if (lista.isEmpty()) {
                TerminalUtils.aviso("Nenhum endereco cadastrado.");
                TerminalUtils.pausar();
                return;
            }

            exibirListaEnderecos(lista);
            System.out.print("  Escolha o numero: ");
            Endereco sel = lista.get(EntradaSegura.lerOpcao(scanner, 1, lista.size()) - 1);

            System.out.println("\n  Pressione Enter para manter o valor atual.\n");

            System.out.println("  Apelido atual: " + sel.getApelido());
            System.out.print("  Novo apelido : ");
            String apelido = scanner.nextLine().trim();
            if (apelido.isBlank()) apelido = sel.getApelido();

            System.out.println("  Rua atual    : " + sel.getRua());
            System.out.print("  Nova rua     : ");
            String rua = scanner.nextLine().trim();
            if (rua.isBlank()) rua = sel.getRua();

            System.out.println("  Bairro atual : " + sel.getBairro());
            System.out.print("  Novo bairro  : ");
            String bairro = scanner.nextLine().trim();
            if (bairro.isBlank()) bairro = sel.getBairro();

            enderecoService.editarEndereco(sel.getId(), clienteLogado.getId(), apelido, rua, bairro);
            TerminalUtils.sucesso("Endereco atualizado.");
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoDefinirPadrao() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("DEFINIR ENDERECO PADRAO");
        try {
            List<Endereco> lista = enderecoService.listarEnderecosPorCliente(clienteLogado.getId());
            if (lista.isEmpty()) {
                TerminalUtils.aviso("Nenhum endereco cadastrado.");
                TerminalUtils.pausar();
                return;
            }

            exibirListaEnderecos(lista);
            System.out.print("  Escolha o numero: ");
            Endereco sel = lista.get(EntradaSegura.lerOpcao(scanner, 1, lista.size()) - 1);

            enderecoService.definirPadrao(sel.getId(), clienteLogado.getId());
            TerminalUtils.sucesso("\"" + sel.getApelido() + "\" definido como padrao.");
        } catch (IllegalStateException e) {
            TerminalUtils.aviso(e.getMessage());
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoRemoverEndereco() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("REMOVER ENDERECO");
        try {
            List<Endereco> lista = enderecoService.listarEnderecosPorCliente(clienteLogado.getId());
            if (lista.isEmpty()) {
                TerminalUtils.aviso("Nenhum endereco cadastrado.");
                TerminalUtils.pausar();
                return;
            }

            exibirListaEnderecos(lista);
            System.out.print("  Escolha o numero: ");
            Endereco sel = lista.get(EntradaSegura.lerOpcao(scanner, 1, lista.size()) - 1);

            if (TerminalUtils.confirmarPerigo("Remover \"" + sel.getApelido() + "\"?", scanner)) {
                enderecoService.removerEndereco(sel.getId(), clienteLogado.getId());
                TerminalUtils.sucesso("Endereco removido.");
            } else {
                TerminalUtils.aviso("Operacao cancelada.");
            }
        } catch (IllegalStateException e) {
            TerminalUtils.aviso(e.getMessage());
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void exibirListaEnderecos(List<Endereco> lista) {
        System.out.println(TerminalUtils.TOPO);
        System.out.println(TerminalUtils.linha("  #   Apelido        Rua                  Bairro"));
        System.out.println(TerminalUtils.SEPARADOR);
        for (int i = 0; i < lista.size(); i++) {
            Endereco e = lista.get(i);
            String padrao = e.isPadrao() ? " *" : "  ";
            System.out.println(TerminalUtils.linha(String.format(
                    "  %-3d %-14s %-20s %s%s",
                    (i + 1), e.getApelido(), e.getRua(), e.getBairro(), padrao)));
        }
        System.out.println(TerminalUtils.BASE);
        System.out.println("  * = padrao");
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RF-CLI-02 — Histórico
    // ─────────────────────────────────────────────────────────────────────────

    private void menuHistorico() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("HISTORICO DE PEDIDOS", clienteLogado.getNome());

            System.out.println(TerminalUtils.TOPO);
            System.out.println(TerminalUtils.linha("  1  \u00bb  Todos os Pedidos"));
            System.out.println(TerminalUtils.linha("  2  \u00bb  Filtrar por Status"));
            System.out.println(TerminalUtils.linha("  3  \u00bb  Ver Detalhes"));
            System.out.println(TerminalUtils.SEPARADOR);
            System.out.println(TerminalUtils.linha("  0  \u00bb  Voltar"));
            System.out.println(TerminalUtils.BASE);
            System.out.print("\n  Escolha uma opcao: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 3);

            switch (opcao) {
                case 1 -> acaoListarTodosPedidos();
                case 2 -> acaoFiltrarPorStatus();
                case 3 -> acaoVerDetalhes();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void acaoListarTodosPedidos() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("TODOS OS PEDIDOS");
        try {
            List<Pedido> pedidos = historicoService.listarPedidosPorCliente(clienteLogado.getId());
            if (pedidos.isEmpty()) {
                TerminalUtils.aviso("Nenhum pedido encontrado.");
            } else {
                exibirListaPedidos(pedidos);
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoFiltrarPorStatus() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("FILTRAR POR STATUS");
        try {
            StatusPedido[] opcoes = {
                    StatusPedido.AGUARDANDO_CONFIRMACAO,
                    StatusPedido.CONFIRMADO,
                    StatusPedido.EM_PREPARO,
                    StatusPedido.SAIU_PARA_ENTREGA,
                    StatusPedido.ENTREGUE,
                    StatusPedido.CANCELADO
            };
            System.out.println("  1 - AGUARDANDO CONFIRMACAO");
            System.out.println("  2 - CONFIRMADO");
            System.out.println("  3 - EM PREPARO");
            System.out.println("  4 - SAIU PARA ENTREGA");
            System.out.println("  5 - ENTREGUE");
            System.out.println("  6 - CANCELADO");
            System.out.print("  Escolha: ");

            StatusPedido status = opcoes[EntradaSegura.lerOpcao(scanner, 1, 6) - 1];
            List<Pedido> pedidos = historicoService.filtrarPedidosClientePorStatus(clienteLogado.getId(), status);

            if (pedidos.isEmpty()) {
                TerminalUtils.aviso("Nenhum pedido com status " + status + ".");
            } else {
                exibirListaPedidos(pedidos);
            }
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void acaoVerDetalhes() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("DETALHES DO PEDIDO");
        try {
            List<Pedido> pedidos = historicoService.listarPedidosPorCliente(clienteLogado.getId());
            if (pedidos.isEmpty()) {
                TerminalUtils.aviso("Nenhum pedido encontrado.");
                TerminalUtils.pausar();
                return;
            }

            exibirListaPedidos(pedidos);
            System.out.print("  Escolha o numero: ");
            exibirDetalhesPedido(pedidos.get(EntradaSegura.lerOpcao(scanner, 1, pedidos.size()) - 1));
        } catch (Exception e) {
            TerminalUtils.erro(e.getMessage());
        }
        TerminalUtils.pausar();
    }

    private void exibirListaPedidos(List<Pedido> pedidos) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        System.out.println(TerminalUtils.TOPO);
        System.out.println(TerminalUtils.linha("  #   ID        Data              Status         Total"));
        System.out.println(TerminalUtils.SEPARADOR);
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            System.out.println(TerminalUtils.linha(String.format(
                    "  %-3d %-8s  %-17s %-14s %s",
                    (i + 1),
                    p.getId().substring(0, 8),
                    p.getDataPedido().format(fmt),
                    p.getStatus(),
                    moeda.format(p.calcularTotal()))));
        }
        System.out.println(TerminalUtils.BASE);
        System.out.println();
    }

    private void exibirDetalhesPedido(Pedido pedido) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        System.out.println(TerminalUtils.TOPO);
        System.out.println(TerminalUtils.linha("  ID     : " + pedido.getId()));
        System.out.println(TerminalUtils.linha("  Data   : " + pedido.getDataPedido().format(fmt)));
        System.out.println(TerminalUtils.linha("  Status : " + pedido.getStatus()));
        System.out.println(TerminalUtils.SEPARADOR);
        System.out.println(TerminalUtils.linha("  ITENS"));
        System.out.println(TerminalUtils.SEPARADOR);
        if (pedido.getItens().isEmpty()) {
            System.out.println(TerminalUtils.linha("  (sem itens registrados)"));
        } else {
            pedido.getItens().forEach(item -> System.out.println(TerminalUtils.linha(
                    String.format("  %-22s x%-3d %s",
                            item.getNomeProduto(), item.getQuantidade(),
                            moeda.format(item.calcularSubtotal())))));
        }
        System.out.println(TerminalUtils.SEPARADOR);
        System.out.println(TerminalUtils.linha("  Taxa entrega : " + moeda.format(pedido.getTaxaEntrega())));
        System.out.println(TerminalUtils.linha("  Total        : " + moeda.format(pedido.calcularTotal())));
        System.out.println(TerminalUtils.BASE);
        System.out.println();
    }
}