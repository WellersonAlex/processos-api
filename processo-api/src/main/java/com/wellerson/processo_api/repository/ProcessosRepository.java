package com.wellerson.processo_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellerson.processo_api.model.ProcessoJudicial;
import com.wellerson.processo_api.model.StatusProcesso;

@Repository
public interface ProcessosRepository extends JpaRepository<ProcessoJudicial, Long> {
    List<ProcessoJudicial> findByStatusAndComarca(StatusProcesso status, String comarca);
}
