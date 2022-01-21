package com.example.springboot.controllers;

import com.example.springboot.models.ClienteModel;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ClienteRepository;
import com.example.springboot.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteModel>> getAllCliente() {
        List<ClienteModel> clientesList = clienteRepository.findAll();
        if (clientesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ClienteModel cliente : clientesList) {
                long id = cliente.getIdCliente();
                cliente.add(linkTo(methodOn(ClienteController.class).getOneCliente(id)).withSelfRel());
            }

            return new ResponseEntity<List<ClienteModel>>(clientesList, HttpStatus.OK);
        }
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteModel> getOneCliente(@PathVariable(value = "id") long id) {
        Optional<ClienteModel> clienteE = clienteRepository.findById(id);
        if (!clienteE.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            clienteE.get().add(linkTo(methodOn(ClienteController.class).getAllCliente()).withSelfRel());
            return new ResponseEntity<ClienteModel>(clienteE.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteModel> saveCliente(@RequestBody @Valid ClienteModel cliente) {
        return new ResponseEntity<ClienteModel>(clienteRepository.save(cliente), HttpStatus.CREATED);
    }

}
