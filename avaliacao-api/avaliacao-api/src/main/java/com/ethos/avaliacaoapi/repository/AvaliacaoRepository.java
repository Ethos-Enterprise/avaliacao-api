package com.ethos.avaliacaoapi.repository;

import com.ethos.avaliacaoapi.repository.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, UUID> {

    @Transactional
    @Modifying
    @Query("update AvaliacaoEntity a set a.comentario = ?1 where a.id = ?2")
    void updateComentario(@NonNull String comentario, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update AvaliacaoEntity a set a.data = ?1 where a.id = ?2")
    void updateData(@NonNull LocalDate date, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("update AvaliacaoEntity a set a.nota = ?1 where a.id = ?2")
    void updateNota(@NonNull Integer nota, @NonNull UUID id);


    List<AvaliacaoEntity> findByComentario(String comentario);

    List<AvaliacaoEntity> findByNota(Integer nota);

    List<AvaliacaoEntity> findByData(LocalDate data);

    List<AvaliacaoEntity> findByFkEmpresa(UUID fkEmpresa);

    List<AvaliacaoEntity> findByFkPrestadoraServico(UUID fkPrestadoraServico);

}
