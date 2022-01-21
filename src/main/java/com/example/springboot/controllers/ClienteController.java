package com.example.springboot.controllers;

import com.example.springboot.models.ClienteModel;
import com.example.springboot.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

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
}
