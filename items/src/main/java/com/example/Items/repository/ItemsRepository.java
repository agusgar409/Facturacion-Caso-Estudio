package com.example.Items.repository;


import com.example.Items.model.Item;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Long> {

    List<Item> findAll(Specification<Item> specification);

}
