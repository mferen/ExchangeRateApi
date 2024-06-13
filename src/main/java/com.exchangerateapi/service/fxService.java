package com.exchangerateapi.service;

import com.exchangerateapi.entity.FXData;
import com.exchangerateapi.repository.fxRepository;
import com.exchangerateapi.repository.fxRepositoryCB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class fxService {

    private final fxRepository fxrepository;
    private final fxRepositoryCB fxrepositoryCB;

    public fxService(fxRepository fxrepository, fxRepositoryCB fxrepositoryCB) {
        this.fxrepository = fxrepository;
        this.fxrepositoryCB = fxrepositoryCB;
          }



    public Optional<FXData> findByCurrency(String currency) {


        return Optional.ofNullable(fxrepository.findTopByFxTypeOrderByIdDesc(currency));
    }

    public List<FXData> findAll() {

        return fxrepository.findAll();
    }


    public Optional<FXData> findByCurrencyCB(String currency) {


        return Optional.ofNullable(fxrepositoryCB.findByCurrencyCB(currency));
    }

    public Page<FXData> findByCriteria(String source, String currency, Pageable page) {

        return fxrepositoryCB.findWithParams(source, currency, page);
    }




}
