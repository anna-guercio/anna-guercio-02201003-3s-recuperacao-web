package br.com.bandtec.annaguerciorecuperacaoweb.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class TipoPassagem {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3)
    private String descricao;

    @NotNull
    @PositiveOrZero
    private Double valor;

    @OneToMany(mappedBy = "tipoPassagem")
    @JsonIgnore
    private List<BilheteUnico> bilhetes;

    // Get and Set
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<BilheteUnico> getBilhetes() {
        return bilhetes;
    }

    public void setBilhetes(List<BilheteUnico> bilhetes) {
        this.bilhetes = bilhetes;
    }
}
