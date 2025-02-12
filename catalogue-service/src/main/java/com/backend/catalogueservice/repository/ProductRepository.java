package com.backend.catalogueservice.repository;


import com.backend.catalogueservice.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {

//  Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);

//  @Query(value = "select p from Product p where p.title ilike :filter") //JPQL
//    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);

//  @Query(value = "select * from catalogue.t_product where c_title ilike :filter", nativeQuery = true) //native sql
//  Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);

  @Query(name = "Product.finalAllByTitleIgnoringCase")
  Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);

}
