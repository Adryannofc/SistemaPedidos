package com.pedidos.infra.seed;

import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.domain.model.*;
import com.pedidos.domain.repository.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public class DataSeeder {

    private final AdminRepository adminRepo;
    private final ClienteRepository clienteRepo;
    private final RestauranteRepository restRepo;
    private final AutenticacaoService authService;
    private final ProdutoRepository produtoRepository;
    private final CategoriaGlobalRepository categoriaGlobalRepository;
    private final CategoriaCardapioRepository categoriaCardapioRepository;
    private final EnderecoRepository enderecoRepository;
    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;
    private final AreaEntregaRepository areaEntregaRepository;


    public DataSeeder(AdminRepository adminRepo, ClienteRepository clienteRepo,
                      RestauranteRepository restRepo, AutenticacaoService authService, ProdutoRepository produtoRepository, CategoriaGlobalRepository categoriaGlobalRepository, CategoriaCardapioRepository categoriaCardapioRepository, EnderecoRepository enderecoRepository, HorarioFuncionamentoRepository horarioFuncionamentoRepository, AreaEntregaRepository areaEntregaRepository) {
        this.adminRepo = adminRepo;
        this.clienteRepo = clienteRepo;
        this.restRepo = restRepo;
        this.authService = authService;
        this.produtoRepository = produtoRepository;
        this.categoriaGlobalRepository = categoriaGlobalRepository;
        this.categoriaCardapioRepository = categoriaCardapioRepository;
        this.enderecoRepository = enderecoRepository;
        this.horarioFuncionamentoRepository = horarioFuncionamentoRepository;
        this.areaEntregaRepository = areaEntregaRepository;
    }

    public void popular() {
        // ========== CATEGORIAS GLOBAIS ==========
        CategoriaGlobal catLanches = new CategoriaGlobal("Lanches", "Categoria de lanches rápidos");
        CategoriaGlobal catPizza = new CategoriaGlobal("Pizza", "Categoria de pizzas variadas");
        CategoriaGlobal catJaponesa = new CategoriaGlobal("Japonesa", "Categoria de culinária japonesa");

        categoriaGlobalRepository.salvar(catLanches);
        categoriaGlobalRepository.salvar(catPizza);
        categoriaGlobalRepository.salvar(catJaponesa);

        // ========== RESTAURANTE 1 - BURGUER HOUSE ==========
        Restaurante restaurante1 = new Restaurante(
                "Burguer House",
                "burguer@delivery.com",
                authService.hashSenha("rest123"),
                "12345678000195"
        );
        restaurante1.setTelefone("44999990001");
        restaurante1.setCategoriaGlobalId(catLanches.getId());
        restaurante1.setStatusAtivo(true);
        restRepo.salvar(restaurante1);

        // ========== CATEGORIAS DO CARDÁPIO - RESTAURANTE 1 ==========
        // Nota: Restaurante 1 não usa categorias de cardápio customizadas,
        // usa apenas a categoria global. Portanto, não criamos CategoriaCardapio para ele.

        // ========== PRODUTOS - RESTAURANTE 1 ==========
        Produto xBurguer = new Produto("X-Burguer", "Hambúrguer com queijo e alface", new BigDecimal("18.90"), null, restaurante1.getId());
        xBurguer.setStatusAtivo(true);
        produtoRepository.salvar(xBurguer);

        Produto xBacon = new Produto("X-Bacon", "Hambúrguer com bacon e queijo", new BigDecimal("22.90"), null, restaurante1.getId());
        xBacon.setStatusAtivo(true);
        produtoRepository.salvar(xBacon);

        Produto cocaCola = new Produto("Coca-Cola 350ml", "Bebida refrigerante", new BigDecimal("6.00"), null, restaurante1.getId());
        cocaCola.setStatusAtivo(true);
        produtoRepository.salvar(cocaCola);

        Produto fritas = new Produto("Fritas G", "Batata frita grande", new BigDecimal("12.00"), null, restaurante1.getId());
        fritas.setStatusAtivo(false);
        produtoRepository.salvar(fritas);

        // ========== HORÁRIOS DE FUNCIONAMENTO - RESTAURANTE 1 ==========
        // Segunda a sexta: 11h às 23h
        for (DayOfWeek dia : new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY}) {
            HorarioFuncionamento horario = new HorarioFuncionamento(restaurante1.getId(), dia, LocalTime.of(11, 0), LocalTime.of(23, 0));
            horarioFuncionamentoRepository.salvar(horario);
        }

        // Sábado e domingo: 12h à 00h
        for (DayOfWeek dia : new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY}) {
            HorarioFuncionamento horario = new HorarioFuncionamento(restaurante1.getId(), dia, LocalTime.of(11, 0), LocalTime.of(23, 0));
            horarioFuncionamentoRepository.salvar(horario);
        }

        // ========== ÁREAS DE ENTREGA - RESTAURANTE 1 ==========
        AreaEntrega areaCentro1 = new AreaEntrega(restaurante1.getId(), "Centro", new BigDecimal("2.0"), new BigDecimal("5.00"), 30);
        areaEntregaRepository.salvar(areaCentro1);

        AreaEntrega areaJardimAmerica = new AreaEntrega(restaurante1.getId(), "Jardim América", new BigDecimal("5.0"), new BigDecimal("8.00"), 45);
        areaEntregaRepository.salvar(areaJardimAmerica);

        // ========== RESTAURANTE 2 - PIZZARIA BELLA ==========
        Restaurante restaurante2 = new Restaurante(
                "Pizzaria Bella",
                "pizza@delivery.com",
                authService.hashSenha("rest123"),
                "98765432000188"
        );
        restaurante2.setTelefone("44999990002");
        restaurante2.setCategoriaGlobalId(catPizza.getId());
        restaurante2.setStatusAtivo(true);
        restRepo.salvar(restaurante2);

        // ========== CATEGORIAS DO CARDÁPIO - RESTAURANTE 2 ==========
        CategoriaCardapio catPizzasSalgadas = new CategoriaCardapio("Pizzas Salgadas", "Pizzas com ingredientes salgados", restaurante2.getId());
        categoriaCardapioRepository.salvar(catPizzasSalgadas);

        CategoriaCardapio catPizzasDoces = new CategoriaCardapio("Pizzas Doces", "Pizzas com ingredientes doces", restaurante2.getId());
        categoriaCardapioRepository.salvar(catPizzasDoces);

        // ========== PRODUTOS - RESTAURANTE 2 ==========
        Produto pizzaMargherita = new Produto("Pizza Margherita", "Pizza clássica com tomate e mozzarella", new BigDecimal("45.00"), catPizzasSalgadas.getId(), restaurante2.getId());
        pizzaMargherita.setStatusAtivo(true);
        produtoRepository.salvar(pizzaMargherita);

        Produto pizzaCalabresa = new Produto("Pizza Calabresa", "Pizza com calabresa e cebola", new BigDecimal("48.00"), catPizzasSalgadas.getId(), restaurante2.getId());
        pizzaCalabresa.setStatusAtivo(true);
        produtoRepository.salvar(pizzaCalabresa);

        Produto pizzaChocolate = new Produto("Pizza Chocolate", "Pizza doce com chocolate derretido", new BigDecimal("42.00"), catPizzasDoces.getId(), restaurante2.getId());
        pizzaChocolate.setStatusAtivo(true);
        produtoRepository.salvar(pizzaChocolate);

        // ========== HORÁRIOS DE FUNCIONAMENTO - RESTAURANTE 2 ==========
        // Terça a domingo: 18h às 23h30
        for (DayOfWeek dia : new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY}) {
            HorarioFuncionamento horario = new HorarioFuncionamento(restaurante1.getId(), dia, LocalTime.of(11, 0), LocalTime.of(23, 0));
            horarioFuncionamentoRepository.salvar(horario);
        }

        // ========== ÁREAS DE ENTREGA - RESTAURANTE 2 ==========
        AreaEntrega areaCentro2 = new AreaEntrega(restaurante2.getId(), "Centro", new BigDecimal("3.0"), new BigDecimal("6.00"), 40);
        areaEntregaRepository.salvar(areaCentro2);

        AreaEntrega areaVilaNova = new AreaEntrega(restaurante2.getId(), "Vila Nova", new BigDecimal("7.0"), new BigDecimal("10.00"), 55);
        areaEntregaRepository.salvar(areaVilaNova);

        // ========== CLIENTE 1 - JOÃO SILVA ==========
        Cliente cliente1 = new Cliente(
                "João Silva",
                "joao@email.com",
                authService.hashSenha("cliente123"),
                "12345678901",
                "44988880001"
        );
        clienteRepo.salvar(cliente1);

        // ========== ENDEREÇO - CLIENTE 1 ==========
        Endereco endereco1 = new Endereco(
                cliente1.getId(),
                "Casa",
                "Rua das Flores",
                "Centro"
        );
        endereco1.setPadrao(true);
        enderecoRepository.salvar(endereco1);

        // ========== CLIENTE 2 - MARIA SOUZA ==========
        Cliente cliente2 = new Cliente(
                "Maria Souza",
                "maria@email.com",
                authService.hashSenha("cliente123"),
                "98765432100",
                "44988880002"
        );
        clienteRepo.salvar(cliente2);

        // ========== ENDEREÇO - CLIENTE 2 ==========
        Endereco endereco2 = new Endereco(
                cliente2.getId(),
                "Trabalho",
                "Av. Brasil",
                "Jardim América"
        );
        endereco2.setPadrao(true);
        enderecoRepository.salvar(endereco2);

        // ========== ADMIN ==========
        Admin admin = new Admin(
                "Administrador",
                "admin@delivery.com",
                authService.hashSenha("admin123")
        );
        adminRepo.salvar(admin);

        System.out.println("[SEED] dados carregados com sucesso.");
    }
}