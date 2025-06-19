package com.wellerson.processo_api.service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class FeriadoService {

    public boolean isFeriado(LocalDate data) {
        // Lista de feriados fixos — você pode carregar de um banco ou arquivo
        Set<MonthDay> feriadosFixos = Set.of(
            MonthDay.of(1, 1),   // 01/01 - Ano Novo
            MonthDay.of(4, 21),  // 21/04 - Tiradentes
            MonthDay.of(5, 1),   // 01/05 - Dia do Trabalho
            MonthDay.of(9, 7),   // 07/09 - Independência
            MonthDay.of(10, 12), // 12/10 - N. Sra. Aparecida
            MonthDay.of(11, 2),  // 02/11 - Finados
            MonthDay.of(11, 15), // 15/11 - Proclamação da República
            MonthDay.of(12, 25)  // 25/12 - Natal
        );

        return feriadosFixos.contains(MonthDay.from(data));
    }
}

