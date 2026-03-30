package br.com.ifpe.oxefood.modelo.livro;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Livro")
@SQLRestriction("habilitado = true")
public class Livro extends EntidadeAuditavel {

    // Validação de Banco: Título obrigatório e com até 100 caracteres [cite: 154]
    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(nullable = false)
    private Double valor;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}