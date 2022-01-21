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

@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteModel>> getAllCliente(){
        List<ClienteModel> clientesList = clienteRepository.findAll();
        if(clientesList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<List<ClienteModel>>(clientesList, HttpStatus.OK);
        }
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteModel> saveCliente(@RequestBody @Valid ClienteModel cliente){
        return new ResponseEntity<ClienteModel>(clienteRepository.save(cliente), HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteModel> updateCliente(@PathVariable(value = "id") long id, @RequestBody @Valid ClienteModel cliente ){
        Optional<ClienteModel> clienteE = clienteRepository.findById(id);
        Optional<ProductModel> produtoO = produtoRepository.findById(id);
        if(!clienteE.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            cliente.setProdutos(produtoO.get());
            return new ResponseEntity<ClienteModel>(clienteRepository.save(cliente), HttpStatus.OK);

        }
    }
}
