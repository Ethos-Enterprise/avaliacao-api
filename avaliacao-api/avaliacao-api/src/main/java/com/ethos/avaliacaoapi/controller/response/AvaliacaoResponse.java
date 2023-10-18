package com.ethos.avaliacaoapi.controller.response;

import java.time.LocalDate;
import java.util.UUID;

public record AvaliacaoResponse (UUID id,String comentario, Integer nota, LocalDate data, UUID fkEmpresa, UUID fkServico){
}
