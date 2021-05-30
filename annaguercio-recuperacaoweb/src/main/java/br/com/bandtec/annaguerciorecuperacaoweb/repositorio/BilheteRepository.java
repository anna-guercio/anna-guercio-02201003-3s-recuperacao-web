package br.com.bandtec.annaguerciorecuperacaoweb.repositorio;

import br.com.bandtec.annaguerciorecuperacaoweb.dominio.BilheteUnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BilheteRepository extends JpaRepository<BilheteUnico, Integer> {

    List<BilheteUnico> findByTipoPassagemId(Integer idTipo);

    Integer countByCpf(String cpf);
}
