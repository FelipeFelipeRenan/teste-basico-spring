package controllers;


import models.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import repositories.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/produtos")
    public ResponseEntity<List<ProductModel>> getAllProduto(){
        List<ProductModel> produtoslist = produtoRepository.findAll();
        if(produtoslist.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<List<ProductModel>>(produtoslist, HttpStatus.OK);
        }
    }

    @GetMapping("/produtps/{}")
    public ResponseEntity<ProductModel> getOneProduto(@PathVariable(value = "id") long id){
        Optional<ProductModel> produtoO = produtoRepository.findById(id);
        if(!produtoO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<ProductModel>(produtoO.get(),HttpStatus.OK);
        }

    }

    @GetMapping("/error")
    public String responseError(){
        return "ERROR DOIDO";
    }

}
