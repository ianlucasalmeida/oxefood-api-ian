package br.com.ifpe.oxefood.api.produto.categoriaproduto;

import br.com.ifpe.oxefood.modelo.produto.CategoriaProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoRequest {

    private String descricao;

    public CategoriaProduto build() {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setDescricao(descricao);
        return categoria;
    }
}