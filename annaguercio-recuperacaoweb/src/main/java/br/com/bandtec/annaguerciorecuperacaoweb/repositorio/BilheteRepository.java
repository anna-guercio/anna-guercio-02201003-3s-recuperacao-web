package br.com.bandtec.annaguerciorecuperacaoweb.repositorio;

import br.com.bandtec.annaguerciorecuperacaoweb.dominio.BilheteUnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BilheteRepository extends JpaRepository<BilheteUnico, Integer> {
}
