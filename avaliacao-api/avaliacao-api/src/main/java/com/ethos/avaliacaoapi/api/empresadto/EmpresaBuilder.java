package com.ethos.avaliacaoapi.api.empresadto;

import java.util.UUID;

public class EmpresaBuilder {
    private UUID id;

    public EmpresaBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public EmpresaDTO createEmpresaDTO() {
        return new EmpresaDTO(id);
    }
}
