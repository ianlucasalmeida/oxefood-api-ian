package br.com.ifpe.oxefood.api.livro;

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

import br.com.ifpe.oxefood.modelo.livro.Livro;
import br.com.ifpe.oxefood.modelo.livro.LivroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livro")
@CrossOrigin
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    // A anotação @Valid ativa a validação que fizemos no Request 
    public ResponseEntity<Livro> save(@RequestBody @Valid LivroRequest request) { 
        Livro livro = livroService.save(request.build()); 
        return new ResponseEntity<Livro>(livro, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Livro obterPorID(@PathVariable Long id) {
        return livroService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> update(@PathVariable("id") Long id, @RequestBody @Valid LivroRequest request) {
        livroService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.ok().build();
    }
}