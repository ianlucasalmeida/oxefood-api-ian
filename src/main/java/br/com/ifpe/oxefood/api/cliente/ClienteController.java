package br.com.ifpe.oxefood.api.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import br.com.ifpe.oxefood.modelo.cliente.ClienteService;
import jakarta.validation.Valid;

// Anotações de Swagger para documentação (Aula C30)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
@Tag(name = "API Cliente", description = "Endpoints para gestão de clientes no sistema") //
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Salva um novo cliente", description = "Endpoint para inserir um cliente e disparar e-mail de boas-vindas") //
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest request, HttpServletRequest requestHttp) {
        Cliente cliente = clienteService.save(request.build(), usuarioService.obterUsuarioLogado(requestHttp));
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Busca cliente por ID")
    @GetMapping("/{id}")
    public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id);
    }

    @Operation(summary = "Atualiza dados do cliente", description = "Endpoint para editar um cliente existente e realizar auditoria") //
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody ClienteRequest request, HttpServletRequest requestHttp) {
        clienteService.update(id, request.build(), usuarioService.obterUsuarioLogado(requestHttp));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove um cliente (Soft Delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.ok().build();
    }
}