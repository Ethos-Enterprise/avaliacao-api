package com.ethos.avaliacaoapi.mapper;

import com.ethos.avaliacaoapi.model.Avaliacao;
import com.ethos.avaliacaoapi.repository.entity.AvaliacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface AvaliacaoEntityMapper {
    AvaliacaoEntity from(Avaliacao avaliacao);
}
