# 🍔 Sistema de Delivery — Java Console

Aplicação de delivery desenvolvida em Java puro, sem frameworks externos, como projeto universitário. Roda inteiramente no terminal com interface de caixas em Unicode e cobre o ciclo completo de um pedido: do cadastro do restaurante à entrega ao cliente.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Funcionalidades por Perfil](#funcionalidades-por-perfil)
- [Arquitetura](#arquitetura)
- [Estrutura de Pacotes](#estrutura-de-pacotes)
- [Modelo de Domínio](#modelo-de-domínio)
- [Fluxo de Status do Pedido](#fluxo-de-status-do-pedido)
- [Tecnologias e Decisões Técnicas](#tecnologias-e-decisões-técnicas)
- [Como Executar](#como-executar)
- [Dados de Seed (Acesso Imediato)](#dados-de-seed-acesso-imediato)
- [Estrutura de Camadas](#estrutura-de-camadas)

---

## Visão Geral

O sistema gerencia três perfis de usuário — **Administrador**, **Restaurante** e **Cliente** — com autenticação por e-mail e senha (hash SHA-256), controle de acesso por tipo, e persistência em memória via repositórios trocáveis por interface.

A interface é 100% terminal, com menus interativos construídos sobre caracteres Unicode de box-drawing, sem dependências de UI externa.

---

## Funcionalidades por Perfil

### 👤 Administrador
- Listar todos os restaurantes cadastrados com status
- Aprovar restaurantes (tornar visíveis para clientes)
- Bloquear restaurantes (desativar do sistema)
- Remover restaurantes permanentemente
- Gerenciar categorias globais (criar, editar, remover)
- Alterar senha com verificação da senha atual
- Visualizar perfil

### 🍕 Restaurante
- Gerenciar produtos: criar, editar, ativar/inativar, remover
- Gerenciar categorias de cardápio próprias
- Editar perfil: nome, CNPJ, telefone
- Editar e-mail com verificação de unicidade no sistema
- Alterar categoria global do restaurante
- Alterar senha com validação

### 🛒 Cliente
- Visualizar restaurantes ativos
- Navegar pelo cardápio de um restaurante
- Gerenciar carrinho: adicionar itens, remover, esvaziar
- Finalizar pedido (checkout) com resumo e confirmação
- Visualizar histórico de pedidos com status e total
- Editar perfil: nome, e-mail, CPF, telefone
- Alterar senha com validação

---

## Arquitetura

O projeto segue os princípios da **Clean Architecture**, com separação rígida em camadas e dependências apontando sempre para dentro (do domínio):

```
Presentation  →  Application  →  Domain  ←  Infrastructure
```

- **Domain**: regras de negócio puras, sem dependências externas
- **Application**: orquestração via Services, depende apenas do Domain
- **Infrastructure**: implementações concretas dos repositórios (in-memory)
- **Presentation**: menus de terminal, entrada do usuário, formatação de saída

As interfaces de repositório vivem no Domain — a camada de Infrastructure as implementa. Isso permite substituir a persistência em memória por JPA/JDBC sem tocar nas regras de negócio.

---

## Estrutura de Pacotes

```
src/main/java/com/pedidos/
├── domain/
│   ├── model/
│   │   ├── Usuario.java              # Entidade base abstrata
│   │   ├── Admin.java
│   │   ├── Cliente.java
│   │   ├── Restaurante.java
│   │   ├── Produto.java
│   │   ├── Pedido.java
│   │   ├── Carrinho.java
│   │   ├── ItemPedido.java
│   │   ├── CategoriaGlobal.java
│   │   └── CategoriaCardapio.java
│   ├── repository/                   # Contratos (interfaces)
│   │   ├── UsuarioRepository.java
│   │   ├── AdminRepository.java
│   │   ├── ClienteRepository.java
│   │   ├── RestauranteRepository.java
│   │   ├── ProdutoRepository.java
│   │   ├── PedidoRepository.java
│   │   ├── CategoriaGlobalRepository.java
│   │   └── CategoriaCardapioRepository.java
│   ├── enums/
│   │   ├── StatusPedido.java
│   │   └── TipoUsuario.java
│   └── exception/
│       ├── ProdutoInativoException.java
│       └── UsuarioNaoEncontradoException.java
│
├── application/
│   └── service/
│       ├── AutenticacaoService.java  # Hash SHA-256 + login multi-perfil
│       ├── AdminService.java
│       ├── ClienteService.java
│       ├── RestauranteService.java
│       ├── ProdutoService.java
│       ├── CategoriaService.java
│       ├── PedidoService.java
│       └── CarrinhoService.java
│
├── infra/
│   ├── repository/impl/              # Persistência in-memory (HashMap)
│   │   ├── AdminRepositoryMemoria.java
│   │   ├── ClienteRepositoryMemoria.java
│   │   ├── RestauranteRepositoryMemoria.java
│   │   ├── ProdutoRepositoryMemoria.java
│   │   ├── PedidoRepositoryMemoria.java
│   │   ├── CategoriaGlobalRepositoryMemoria.java
│   │   └── CategoriaCardapioRepositoryMemoria.java
│   └── seed/
│       └── DataSeeder.java           # Dados iniciais para desenvolvimento
│
└── presentation/
    ├── Main.java                     # Ponto de entrada e composição do grafo
    ├── menu/
    │   ├── MenuLogin.java
    │   ├── MenuCliente.java
    │   └── MenuAdmin.java            # (legado — não usado no fluxo principal)
    ├── admin/
    │   ├── MenuAdmin.java            # Menu principal do admin
    │   └── MenuCategorias.java
    ├── restaurante/
    │   ├── MenuRestaurante.java
    │   ├── MenuProdutos.java
    │   └── MenuCategoriasCardapio.java
    └── util/
        ├── TerminalUtils.java        # Caixas Unicode, feedback, pausas
        └── EntradaSegura.java        # Leitura validada de int e String
```

---

## Modelo de Domínio

```
Usuario (abstract)
├── Admin
├── Cliente          — CPF, telefone, lista de favoritos
└── Restaurante      — CNPJ, telefone, statusAtivo, categoriaGlobalId

CategoriaGlobal      — criada pelo Admin, vinculada ao Restaurante
CategoriaCardapio    — criada pelo Restaurante, vinculada a Produtos

Produto              — preço, statusAtivo, restauranteId, categoriaCardapioId
Carrinho             — sessão do cliente (não persiste)
Pedido               — snapshot dos itens + StatusPedido + taxaEntrega + total
ItemPedido           — produtoId, nomeProduto, quantidade, precoUnitario
```

> **Carrinho é efêmero** — existe apenas em memória na sessão. Um novo login ou logout zera o estado. Pedido é a entidade persistida.

---

## Fluxo de Status do Pedido

```
AGUARDANDO_CONFIRMACAO
       │
       ├──► CONFIRMADO ──► ENTREGUE  (terminal)
       │         │
       │         └──► CANCELADO     (terminal)
       │
       └──► CANCELADO               (terminal)
```

Transições inválidas lançam `IllegalStateException`. Pedidos `ENTREGUE` ou `CANCELADO` são imutáveis.

> **Nota:** os status `EM_PREPARO` e `SAIU_PARA_ENTREGA` estão declarados no enum mas ainda não implementados na máquina de estados do `PedidoService`.

---

## Tecnologias e Decisões Técnicas

| Decisão | Escolha | Motivo |
|---|---|---|
| Linguagem | Java 17+ | Requisito da disciplina |
| Framework | Nenhum | Foco em fundamentos OO e arquitetura |
| Persistência | HashMap in-memory | Isola a regra de negócio da infraestrutura |
| Hash de senha | SHA-256 (`MessageDigest`) | Sem dependência externa |
| Validação de e-mail | Regex no setter de `Usuario` | Controle no nível do modelo |
| Validação de CNPJ | Regex + normalização em `RestauranteService` | Aceita formatado e sem formatação |
| UI | Terminal com box-drawing Unicode | Sem dependência de biblioteca gráfica |
| IDs | `UUID.randomUUID()` | Garante unicidade sem banco |
| Composição | Manual em `Main.java` | DI explícita sem container |

---

## Como Executar

**Pré-requisitos:** JDK 17 ou superior.

```bash
# Compilar
javac -d out -sourcepath src/main/java src/main/java/com/pedidos/presentation/Main.java

# Executar
java -cp out com.pedidos.presentation.Main
```

Com Maven:

```bash
mvn compile exec:java -Dexec.mainClass="com.pedidos.presentation.Main"
```

---

## Dados de Seed (Acesso Imediato)

O `DataSeeder` popula o sistema automaticamente ao iniciar. Use as credenciais abaixo para testar cada perfil:

| Perfil | E-mail | Senha |
|---|---|---|
| Administrador | `admin@delivery.com` | `admin123` |
| Burguer House | `burguer@delivery.com` | `rest123` |
| Pizzaria Bella | `pizza@delivery.com` | `rest123` |
| João Silva | `joao@email.com` | `cliente123` |
| Maria Souza | `maria@email.com` | `cliente123` |

**Dados pré-carregados:**
- 3 categorias globais: Lanches, Pizza, Japonesa
- 2 restaurantes ativos com produtos (Burguer House com 4 produtos, Pizzaria Bella com 3)
- 2 clientes cadastrados

---

## Estrutura de Camadas

### Regras de Negócio Relevantes

- **E-mail único no sistema inteiro** — `ClienteService` verifica admin, restaurante e cliente antes de aceitar um novo e-mail
- **Produto bloqueado por restaurante inativo** — cliente não vê restaurantes com `statusAtivo = false`
- **Produto de outro restaurante no carrinho** — `CarrinhoService` bloqueia a adição e exige esvaziar o carrinho
- **Categoria global em uso** — `CategoriaService` bloqueia remoção se houver restaurante vinculado
- **Categoria de cardápio em uso** — `CategoriaService` bloqueia remoção se houver produto vinculado
- **CNPJ normalizado** — aceita entrada com ou sem formatação; armazena apenas dígitos

### Pontos de Extensão

Para evoluir o projeto para persistência real, basta implementar as interfaces do pacote `domain/repository` com JPA ou JDBC e trocar o binding em `Main.java`. Nenhuma regra de negócio muda.
