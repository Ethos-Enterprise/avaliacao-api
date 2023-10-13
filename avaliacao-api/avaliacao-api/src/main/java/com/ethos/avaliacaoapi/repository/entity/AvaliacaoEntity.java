package com.ethos.avaliacaoapi.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Table(name = "AVALIACAO")
@Entity
@Immutable
public class AvaliacaoEntity {
    @Id
    UUID id;
    String comentario;
    Integer nota;
    UUID fkEmpresa;
    UUID fkPrestadoraServico;
    LocalDate data;


    public AvaliacaoEntity() {
    }

    @Builder(toBuilder = true)
    public AvaliacaoEntity(UUID id, String comentario, Integer nota, LocalDate data, UUID fkEmpresa, UUID fkPrestadoraServico) {
        this.id = UUID.randomUUID();
        this.comentario = comentario;
        this.nota = nota;
        this.data = data;
        this.fkEmpresa = fkEmpresa;
        this.fkPrestadoraServico = fkPrestadoraServico;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setFkEmpresa(UUID fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public void setFkPrestadoraServico(UUID fkPrestadoraServico) {
        this.fkPrestadoraServico = fkPrestadoraServico;
    }
}
