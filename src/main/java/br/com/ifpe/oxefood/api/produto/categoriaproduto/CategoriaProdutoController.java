package br.com.ifpe.oxefood.api.produto.categoriaproduto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ifpe.oxefood.modelo.produto.CategoriaProduto;
import br.com.ifpe.oxefood.modelo.produto.CategoriaProdutoService;

@RestController
@RequestMapping("/api/categoriaproduto")
@CrossOrigin
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @PostMapping
    public ResponseEntity<CategoriaProduto> save(@RequestBody CategoriaProdutoRequest request) {
        CategoriaProduto categoriaProdutoNovo = request.build();
        CategoriaProduto categoriaProduto = categoriaProdutoService.save(categoriaProdutoNovo);
        return new ResponseEntity<CategoriaProduto>(categoriaProduto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoriaProduto> listarTodos() {
        return categoriaProdutoService.listarTodos();
    }

    @GetMapping("/{id}")
    public CategoriaProduto obterPorID(@PathVariable Long id) {
        return categoriaProdutoService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProduto> update(@PathVariable("id") Long id, @RequestBody CategoriaProdutoRequest request) {
        categoriaProdutoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaProdutoService.delete(id);
        return ResponseEntity.ok().build();
    }
}