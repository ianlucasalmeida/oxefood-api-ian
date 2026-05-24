package br.com.ifpe.oxefood.util.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class EntidadeAuditavel extends EntidadeNegocio {

    @JsonIgnore
    @Version
    private Long versao;

    @JsonIgnore
    @Column
    private LocalDate dataCriacao;

    @JsonIgnore
    @Column
    private LocalDate dataUltimaModificacao;

    // --- NOVOS CAMPOS DE AUDITORIA (AULA 23) ---
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private Usuario criadoPor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ultima_modificacao_por_id")
    private Usuario ultimaModificacaoPor;
}