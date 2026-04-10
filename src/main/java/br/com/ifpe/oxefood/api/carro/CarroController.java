package br.com.ifpe.oxefood.api.carro;

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

import br.com.ifpe.oxefood.modelo.carro.Carro;
import br.com.ifpe.oxefood.modelo.carro.CarroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carro")
@CrossOrigin
public class CarroController {

    @Autowired
    private CarroService carroService;

    @PostMapping
    public ResponseEntity<Carro> save(@RequestBody @Valid CarroRequest request) {
        Carro carro = carroService.save(request.build());
        return new ResponseEntity<Carro>(carro, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Carro> listarTodos() {
        return carroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Carro obterPorID(@PathVariable Long id) {
        return carroService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> update(@PathVariable("id") Long id, @RequestBody @Valid CarroRequest request) {
        carroService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }
}