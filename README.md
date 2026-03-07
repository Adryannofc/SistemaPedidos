# 🍔 Sistema de Pedidos — Food Delivery CLI

> Projeto acadêmico desenvolvido em Java SE 17, implementando um sistema completo de delivery por linha de comando com arquitetura em camadas e princípios de OOP.

---

## 👥 Equipe

| Membro    | Responsabilidade                                                                 |
|-----------|----------------------------------------------------------------------------------|
| Adryann   | Camada de serviço: `AdminService`, `CategoriaService`, `ClienteService`, `EnderecoService`, `CarrinhoService` |
| Thiago    | Camada de apresentação (menus)                                                   |
| Pedro     | Entidades de domínio, `ProdutoService`, `RestauranteService`, revisão de código  |
| Luan      | Repositórios, `DataSeeder`, `HistoricoService`, `FavoritosService`               |
| Guilherme | Entidades de domínio, `AreaEntregaService`, `HorarioService`, `PedidoService`, `Main.java` |

---

##  Arquitetura

O projeto segue uma **arquitetura em camadas** rigorosamente separadas:

```
presentation/   → menus CLI (Scanner, System.out)
application/    → services (regras de negócio)
domain/         → entidades, repositórios (interfaces), enums, exceções
infra/          → implementações de repositório (HashMap in-memory), seed
```

> **Regra de ouro:** nenhuma camada interna conhece a camada externa. Services não usam `Scanner` nem `System.out` — apenas lançam exceções. Menus não instanciam services — recebem por construtor.

---

## ⚙️ Decisões Técnicas

| Aspecto              | Decisão                                         | Motivo                                      |
|----------------------|-------------------------------------------------|---------------------------------------------|
| IDs                  | `UUID` como `String`                            | Unicidade garantida, sem dependência de BD  |
| Valores monetários   | `BigDecimal`                                    | Precisão decimal sem erros de ponto flutuante |
| Datas e horários     | `java.time` (`LocalDateTime`, `LocalTime`, `DayOfWeek`) | API moderna, sem `Date` legado        |
| Persistência         | `HashMap` in-memory                             | Escopo acadêmico, sem dependência externa   |
| Queries              | Stream API                                      | Filtragem declarativa sobre coleções        |
| Injeção de dependência | Construtores (sem framework)                  | Explícita, testável, sem magia              |
| Montagem do grafo    | `Main.java` como único assembler                | Single Responsibility — só ele sabe como montar tudo |
| Senhas               | SHA-256 via `AutenticacaoService`               | Hash antes de persistir, nunca texto puro   |
| Booleans de estado   | Toggle pattern (`!atual`)                       | Consistência para `statusAtivo`, `isPadrao` |

---

##  Tipos de Usuário

### Admin
- Gerencia restaurantes (aprovar, bloquear, remover)
- Gerencia categorias globais (criar, editar, remover)
- Acesso ao próprio perfil e troca de senha

### Restaurante
- Gerencia produtos e categorias do cardápio
- Configura áreas de entrega por bairro (com taxa e previsão)
- Define horários de funcionamento por dia da semana
- Acompanha e atualiza status dos pedidos
- Edita perfil, e-mail, CNPJ, telefone e senha

### Cliente
- Cadastro com CPF e e-mail únicos no sistema
- Gerencia endereços (com endereço padrão)
- Navega por restaurantes e cardápio
- Monta carrinho e finaliza pedido (checkout com validações)
- Consulta histórico de pedidos com filtro por status

---

##  Fluxo Principal de Pedido

```
1. Cliente escolhe restaurante (deve estar ativo e aberto)
2. Navega pelo cardápio (apenas produtos ativos)
3. Adiciona itens ao carrinho (validação de restaurante único)
4. Seleciona endereço de entrega
5. Sistema valida:
   ├── Restaurante aberto no momento (HorarioService)
   ├── Produtos ainda ativos (ProdutoService)
   ├── Endereço pertence ao cliente (EnderecoService)
   └── Bairro atendido + calcula taxa (AreaEntregaService)
6. Pedido criado com status AGUARDANDO_CONFIRMACAO
7. Restaurante atualiza status → CONFIRMADO → ENTREGUE (ou CANCELADO)
```

---

##  Estrutura de Pacotes

```
com.pedidos/
├── application/
│   └── service/           # AdminService, ClienteService, PedidoService...
├── domain/
│   ├── enums/             # StatusPedido, TipoUsuario
│   ├── exception/         # BairroNaoAtendidoException, ProdutoInativoException...
│   ├── model/             # Admin, Cliente, Restaurante, Pedido, Produto...
│   └── repository/        # Interfaces de repositório
├── infra/
│   ├── repository/impl/   # *RepositoryMemoria (HashMap)
│   └── seed/              # DataSeeder
└── presentation/
    ├── admin/             # MenuAdmin, MenuCategorias
    ├── menu/              # MenuLogin, MenuCliente, MenuPrincipal
    ├── restaurante/       # MenuRestaurante, MenuProdutos, MenuHorarios...
    └── util/              # TerminalUtils, EntradaSegura
```

---

##  Como Executar

**Pré-requisitos:** Java 17+

```bash
# Compilar
javac -d out -sourcepath src/main/java src/main/java/com/pedidos/presentation/Main.java

# Executar
java -cp out com.pedidos.presentation.Main
```

Ou via Maven/IDE: execute `Main.java` diretamente.

---

##  Dados de Seed

O `DataSeeder` popula automaticamente ao iniciar:

| Tipo        | Login                        | Senha        |
|-------------|------------------------------|--------------|
| Admin       | `admin@delivery.com`         | `admin123`   |
| Restaurante | `burguer@delivery.com`       | `rest123`    |
| Restaurante | `pizza@delivery.com`         | `rest123`    |
| Cliente     | `joao@email.com`             | `cliente123` |
| Cliente     | `maria@email.com`            | `cliente123` |

---

## ✅ Padrões de Qualidade

- **Zero atributos públicos** em entidades — tudo via getters/setters com validação
- **`@Override`** obrigatório em `exibirDetalhes()` — polimorfismo explícito
- **Exceções em vez de null** — services lançam `IllegalArgumentException` ou `IllegalStateException`
- **Validação em camadas:** formato → regra de negócio → repositório (nessa ordem)
- **Unicidade de e-mail** verificada em todos os tipos de usuário (`Admin`, `Restaurante`, `Cliente`)
- **`TerminalUtils`** como fonte única de verdade para toda formatação visual

---

## 📋 Status dos Requisitos

| Módulo             | Status |
|--------------------|--------|
| Autenticação       | ✅     |
| Gestão de Admin    | ✅     |
| Gestão de Restaurante | ✅  |
| Gestão de Cliente  | ✅     |
| Endereços          | ✅     |
| Categorias Globais | ✅     |
| Categorias Cardápio| ✅     |
| Produtos           | ✅     |
| Áreas de Entrega   | ✅     |
| Horários           | ✅     |
| Carrinho           | ✅     |
| Pedidos / Checkout | ✅     |
| Histórico          | ✅     |
| Favoritos          | ✅     |
