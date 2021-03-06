package com.example.springboot.controllers;


import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.repositories.ProdutoRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/produtos")
    public ResponseEntity<List<ProductModel>> getAllProduto(){
        List<ProductModel> produtoslist = produtoRepository.findAll();
        if(produtoslist.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            for(ProductModel produto: produtoslist){
                long id = produto.getIdProduto();
                produto.add(linkTo(methodOn(ProdutoController.class).getOneProduto(id)).withSelfRel());
            }
            return new ResponseEntity<List<ProductModel>>(produtoslist, HttpStatus.OK);
        }
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProductModel> getOneProduto(@PathVariable(value = "id") long id){
        Optional<ProductModel> produtoO = produtoRepository.findById(id);
        if(!produtoO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            produtoO.get().add(linkTo(methodOn(ProdutoController.class).getAllProduto()).withSelfRel());
            return new ResponseEntity<ProductModel>(produtoO.get(),HttpStatus.OK);
        }

    }

    @PostMapping("/produtos")
    public ResponseEntity<ProductModel> saveProduto(@RequestBody @Valid ProductModel produto){
        return new ResponseEntity<ProductModel>(produtoRepository.save(produto), HttpStatus.CREATED);

    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable(value = "id") long id){
        Optional<ProductModel> produtoO = produtoRepository.findById(id);
        if(!produtoO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            produtoRepository.delete(produtoO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProductModel> updateProduto(@PathVariable(value = "id") long id, @RequestBody @Valid ProductModel produto){
        Optional<ProductModel> produtoO = produtoRepository.findById(id);
        if(!produtoO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            produto.setIdProduto(produtoO.get().getIdProduto());
            return new ResponseEntity<ProductModel>(produtoRepository.save(produto), HttpStatus.OK);
        }
    }

    @GetMapping("/error")
    public ResponseEntity<?> responseError(){

        return new ResponseEntity<>("Erro Inesperado", HttpStatus.BAD_REQUEST);
    }

}
