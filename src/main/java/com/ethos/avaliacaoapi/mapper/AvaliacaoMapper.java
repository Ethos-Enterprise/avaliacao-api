package com.ethos.avaliacaoapi.mapper;

import com.ethos.avaliacaoapi.controller.request.AvaliacaoRequest;
import com.ethos.avaliacaoapi.model.Avaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface AvaliacaoMapper {
    Avaliacao from(AvaliacaoRequest avaliacaoRequest);
}
