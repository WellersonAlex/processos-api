package com.wellerson.processo_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wellerson.processo_api.model.Audiencia;

@Repository
public interface AudienciaRepository extends JpaRepository<Audiencia, Long> {
    @Query("SELECT a FROM Audiencia a WHERE a.dataHora BETWEEN :start AND :end AND a.local = :local AND a.processo.vara = :vara")
    List<Audiencia> findConflitos(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end,
                                  @Param("local") String local,
                                  @Param("vara") String vara);

    List<Audiencia> findByProcesso_ComarcaAndDataHoraBetween(String comarca, LocalDateTime inicio, LocalDateTime fim);
}
