package com.pedidos.application.service;

import com.pedidos.domain.model.Admin;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.AdminRepository;
import com.pedidos.domain.repository.RestauranteRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository;
    private final AutenticacaoService autenticacaoService;
    private final RestauranteRepository restauranteRepository;

    public AdminService(AdminRepository adminRepository, AutenticacaoService autenticacaoService, RestauranteRepository restauranteRepository) {
        this.adminRepository = adminRepository;
        this.autenticacaoService = autenticacaoService;
        this.restauranteRepository = restauranteRepository;
    }

    /**
     *  Registra um novo administrador no sistema com as credencias informadas.
     * @param nome
     * @param email
     * @param senha
     * @throws IllegalArgumentException se ja tiver o email informado cadastrado
     */
    public void cadastrarAdmin(String nome, String email, String senha) {
        if (adminRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        String hash = autenticacaoService.hashSenha(senha);
        Admin admin = new Admin(nome, email, hash);
        adminRepository.salvar(admin);
    }

    /**
     * Altera senha atual de um administrador
     * @param usuario administrador que tera senha alterada
     * @param senhaAtual senha atual informada pelo usuario
     * @param novaSenha nova senha que sera aplicada
     * @param confirmacaoSenha confirmacao da nova senha
     * @throws IllegalArgumentException se a senha atual fornecida for incorreta
     * @throws IllegalArgumentException se a nova senha for diferente da confirmacao
     */
    public void alterarSenha(Usuario usuario, String senhaAtual, String novaSenha, String confirmacaoSenha) {
        String hashAtual = autenticacaoService.hashSenha(senhaAtual);
        if (!usuario.verificarSenha(hashAtual)) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if (!novaSenha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("Nova senha e confirmação não coincidem.");
        }

        String novoHash = autenticacaoService.hashSenha(novaSenha);
        usuario.setSenhaHash(novoHash);
        adminRepository.salvar(usuario);
    }


    /**
     * Lista todos os restaurantes cadastrados
     * @return lista de restaurantes
     */
    public List<Restaurante> listarRestaurantes() {
        return restauranteRepository.listarRestaurantes();
    }

    /**
     * Aprova um restaurante, tornando-o visível e ativo no sistema.
     * @param id identificador único do restaurante
     * @throws IllegalArgumentException se nenhum restaurante for encontrado com o id informado
     */
    public void aprovarRestaurante(String id) {
        Restaurante restaurante = buscarRestaurantePorId(id);
        restaurante.setStatusAtivo(true);
        restauranteRepository.salvar(restaurante);
    }

    /**
     * Bloqueia um restaurante, tornado-o invisivel e desativado no sistema
     * @param id
     * @throws IllegalArgumentException se nenhum restaurante for encontrado com id informado
     */
    public void bloquearRestaurante(String id) {
        Restaurante restaurante = buscarRestaurantePorId(id);
        restaurante.setStatusAtivo(false);
        restauranteRepository.salvar(restaurante);
    }

    /**
     * Deleta um restaurante do sistema com id fornecido
     * @param id identificador unico do restaurante
     * @throws IllegalArgumentException se nenhum restaurante for encontrado com id informado
     */
    public void removerRestaurante(String id) {
        buscarRestaurantePorId(id);
        restauranteRepository.deletar(id);
    }

    /**
     * Procura restaurante com id fornecido.
     * @param id idetificador do restaurante
     * @return o restaurante correspondente ao id informado
     * @throws IllegalArgumentException se nenhum restaurante foi encontrado com id informado.
     */
    private Restaurante buscarRestaurantePorId(String id) {
        return restauranteRepository.listarRestaurantes().stream()
                .filter(r -> r.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado."));
    }
}