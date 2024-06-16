package com.exchangerateapi.repository;

import com.exchangerateapi.entity.FXData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class fxRepositoryCB {
    EntityManager em;
    public fxRepositoryCB(EntityManagerFactory emf){

        this.em = emf.createEntityManager();

    }
    public FXData findByCurrencyCB(String currency) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FXData> cq = cb.createQuery(FXData.class);
        Root<FXData> fxData = cq.from(FXData.class);

        cq.where(cb.equal(fxData.get("fxType"), currency));
        cq.orderBy(cb.desc(fxData.get("id")));
        TypedQuery<FXData> q = em.createQuery(cq);
        q.setMaxResults(1);
        return q.getSingleResult();


    }


    public Page<FXData> findWithParams(String source,  String currency, Pageable page){


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FXData> cq = cb.createQuery(FXData.class);
        Root<FXData> fxData = cq.from(FXData.class);

        List<Predicate> predicates = new ArrayList<>();
        if (source != null) {
            predicates.add(cb.equal(fxData.get("fxSource"), source));
        }

        if (currency != null) {
            predicates.add(cb.equal(fxData.get("fxType"), currency));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<FXData> query = em.createQuery(cq);

        int size = query.getResultList().size();

        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        Page<FXData> result = new PageImpl<FXData>(query.getResultList(), page, size);

        return result;



    }


    




}
