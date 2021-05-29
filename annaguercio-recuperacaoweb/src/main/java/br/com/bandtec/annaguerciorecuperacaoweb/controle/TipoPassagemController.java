package br.com.bandtec.annaguerciorecuperacaoweb.controle;

import br.com.bandtec.annaguerciorecuperacaoweb.dominio.BilheteUnico;
import br.com.bandtec.annaguerciorecuperacaoweb.dominio.TipoPassagem;
import br.com.bandtec.annaguerciorecuperacaoweb.repositorio.BilheteRepository;
import br.com.bandtec.annaguerciorecuperacaoweb.repositorio.TipoPassagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipo-passagem")
public class TipoPassagemController {

    @Autowired
    private TipoPassagemRepository repository;

    @GetMapping
    public ResponseEntity getTipoPassagem(){
        List<TipoPassagem> passagens = repository.findAll();
        if (passagens.isEmpty()){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(200).body(passagens);
        }
    }

    @PostMapping
    public ResponseEntity postTipoPassagem(@RequestBody @Valid TipoPassagem novoTipo) {
        repository.save(novoTipo);
        return ResponseEntity.status(201).build();
    }


}
