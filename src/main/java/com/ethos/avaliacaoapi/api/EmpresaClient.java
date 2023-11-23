package com.ethos.avaliacaoapi.api;

import com.ethos.avaliacaoapi.api.empresadto.EmpresaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "empresa-api", url = "http://empresa-api:8081/v1.0/empresa")
public interface EmpresaClient {
    @GetMapping("/{id}")
    EmpresaDTO findById(@PathVariable UUID id);
}
