package com.example.springboot.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TB_CLIENTE")
public class ClienteModel extends RepresentationModel<ClienteModel> implements Serializable{

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long idCliente;

    private String nome;

    @OneToMany( mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductModel> produtos = new ArrayList<>();


}
