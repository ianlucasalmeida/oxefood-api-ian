package br.com.ifpe.oxefood.api.produto;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ifpe.oxefood.modelo.produto.Produto;
import br.com.ifpe.oxefood.modelo.produto.ProdutoService;
import br.com.ifpe.oxefood.util.Util;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvar(ProdutoRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        
        if (file != null && !file.isEmpty()) {
            Util.fazerUploadImagem(file);
            request.setImagem(file.getOriginalFilename());
        }
        
        Produto produto = produtoService.salvar(request.build(), request.getIdCategoria());
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, ProdutoRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        
        if (file != null && !file.isEmpty()) {
            Util.fazerUploadImagem(file);
            request.setImagem(file.getOriginalFilename());
        }
        
        produtoService.atualizar(id, request.build(), request.getIdCategoria());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filtrar")
    public List<Produto> filtrar(
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "idCategoria", required = false) Long idCategoria) {
        
        return produtoService.filtrar(codigo, titulo, idCategoria);
    }

    // --- NOVO ENDPOINT DE LEITURA DE IMAGEM (AULA 27) ---
    @GetMapping("/imagem/{nomeImagem}")
    public ResponseEntity<byte[]> obterImagem(@PathVariable String nomeImagem) {
        try {
            File arquivo = new File(br.com.ifpe.oxefood.util.Util.LOCAL_ARMAZENAMENTO_IMAGENS + nomeImagem);
            
            if (arquivo.exists()) {
                byte[] imagemBytes = Files.readAllBytes(arquivo.toPath());
                String contentType = Files.probeContentType(arquivo.toPath());
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType != null ? contentType : "image/jpeg"))
                        .body(imagemBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}