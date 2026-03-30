package br.com.ifpe.oxefood.api.livro;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.oxefood.modelo.livro.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LivroRequest {

    @NotBlank(message = "O título é de preenchimento obrigatório")
    @Length(max = 100, message = "O título deverá ter no máximo 100 caracteres")
    private String titulo;

    @NotBlank(message = "O autor é de preenchimento obrigatório")
    @Length(max = 100, message = "O autor deverá ter no máximo 100 caracteres")
    private String autor;

    @NotNull(message = "O valor é de preenchimento obrigatório")
    private Double valor;

    public Livro build() {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setValor(valor);
        return livro;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}