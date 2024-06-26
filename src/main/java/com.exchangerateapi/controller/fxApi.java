package com.exchangerateapi.controller;


import com.exchangerateapi.entity.FXData;
import com.exchangerateapi.service.fxService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rates")
public class fxApi {


    private final fxService fxservice;

    public fxApi( fxService fxservice) {

        this.fxservice = fxservice;
    }

    @GetMapping("/{fxType}")
    public ResponseEntity<FXData> getByCurrency(@PathVariable("fxType") String currency) {

        return fxservice.findByCurrency(currency).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }


     @GetMapping("")
     public ResponseEntity<Page<FXData>> getbyCriteria(

             @org.springdoc.core.annotations.ParameterObject Pageable page,
             @RequestParam(name = "source", required = false) String source,
             @RequestParam(name = "currency", required = false) String currency


             ) {

         Page<FXData> pageable = fxservice.findByCriteria(source, currency, page);
         return ResponseEntity.ok(pageable);

     }

    @GetMapping("getall")
    public ResponseEntity<List<FXData>> getAll() {


        List<FXData> fxData = fxservice.findAll();

        return ResponseEntity.ok(fxData);
    }









}
