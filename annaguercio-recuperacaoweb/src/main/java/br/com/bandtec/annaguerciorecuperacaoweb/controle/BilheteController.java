package br.com.bandtec.annaguerciorecuperacaoweb.controle;

import br.com.bandtec.annaguerciorecuperacaoweb.dominio.BilheteUnico;
import br.com.bandtec.annaguerciorecuperacaoweb.repositorio.BilheteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bilhete-unico")
public class BilheteController {

    @Autowired
    private BilheteRepository repository;

    @GetMapping
    public ResponseEntity getBilhetes(){
        List<BilheteUnico> bilhetes = repository.findAll();
        if (bilhetes.isEmpty()){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(200).body(bilhetes);
        }
    }

    @PostMapping
    public ResponseEntity postBilhete(@RequestBody @Valid BilheteUnico novoBilhete) {
        repository.save(novoBilhete);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("{id}")
    public ResponseEntity getBilhetesPorId(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            return ResponseEntity.status(200).body(repository.findById(id));
        } else {
            return ResponseEntity.status(404).body("O id informado é inválido");
        }
    }
}
