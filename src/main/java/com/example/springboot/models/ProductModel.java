package com.example.springboot.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_PRODUTO")
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idProduto;

    private String nome;

    private BigDecimal valor;


}
