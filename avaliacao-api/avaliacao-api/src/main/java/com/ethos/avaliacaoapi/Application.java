package com.ethos.avaliacaoapi;

import com.ethos.avaliacaoapi.controller.arquivo.ListaObj;
import com.ethos.avaliacaoapi.controller.response.AvaliacaoResponse;
import com.ethos.avaliacaoapi.services.AvaliacaoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
