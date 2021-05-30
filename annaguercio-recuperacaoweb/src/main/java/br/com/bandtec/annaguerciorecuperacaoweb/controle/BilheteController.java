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
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/bilhete-unico")
public class BilheteController {

    @Autowired
    private BilheteRepository repository;

    @Autowired
    private TipoPassagemRepository tipoPassagemRepository;

    @GetMapping
    public ResponseEntity getBilhetes() {
        List<BilheteUnico> bilhetes = repository.findAll();
        if (bilhetes.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(bilhetes);
        }
    }

    @PostMapping
    public ResponseEntity postBilhete(@RequestBody @Valid BilheteUnico novoBilhete) {
        Integer qtdCpf = repository.countByCpf(novoBilhete.getCpf());
        if (qtdCpf > 1) {
            return ResponseEntity.status(400).body("Este CPF já tem 2BUs!");
        } else {
            repository.save(novoBilhete);
            return ResponseEntity.status(201).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getBilhetesPorId(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            return ResponseEntity.status(200).body(repository.findById(id));
        } else {
            return ResponseEntity.status(404).body("O id informado é inválido");
        }
    }

    @PostMapping("/{id}/recarga/{valorRecarga}")
    public ResponseEntity postRecarga(@PathVariable Integer id,
                                      @PathVariable Double valorRecarga) {
        Optional<BilheteUnico> bilheteOptional = repository.findById(id);
        if (!repository.existsById(id)) {
            return status(404).body("O id informado é inválido");
        } else {
            BilheteUnico bilhetes = bilheteOptional.get();

            if (valorRecarga > 1.00) {
                if (bilhetes.getSaldo() + valorRecarga > 230.00){
                    Double valorRestante = 230 - (bilhetes.getSaldo());
                    return status(400).body("Recarga não efetuada! Passaria do limite de R$ 230,00. " +
                            "Você ainda pode carregar até R$ "+ valorRestante);
                } else {
                bilhetes.setSaldo(bilhetes.getSaldo() + valorRecarga);
                repository.save(bilhetes);
                return status(201).body("Recarga efetuada com sucesso!");
            } }
                else {
                return status(404).body("O valor da recarga deve ser a partir de R$ 1,00");
            }
        }
    }

    @PostMapping("/{id}/passagem/{idTipo}")
    public ResponseEntity postTipoPassagem(@PathVariable Integer id, @PathVariable Integer idTipo){
        Optional<BilheteUnico> bilheteOptional = repository.findById(id);
        Optional<TipoPassagem> tipoPassagemOptional = tipoPassagemRepository.findById(idTipo);

        if (repository.existsById(id)) {
            if (tipoPassagemRepository.existsById(idTipo)) {
                BilheteUnico bilhetes = bilheteOptional.get();
                TipoPassagem passagens = tipoPassagemOptional.get();

                if (passagens.getValor() > bilhetes.getSaldo()){
                    return ResponseEntity.status(404).body("Saldo atual (R$ "+bilhetes.getSaldo()+" )" +
                            "insuficiente para esta passagem");
                } else {
                bilhetes.setSaldo(bilhetes.getSaldo() - passagens.getValor());
                repository.save(bilhetes);
            }
            } else {
                return ResponseEntity.status(404).body("Tipo de passagem não encontrada");
            }

        return ResponseEntity.status(201).build();

        } else {
            return ResponseEntity.status(404).body("BU não encontrado");
        }
    }

}
