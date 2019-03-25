package com.greenfox.oauth_practice.repository;

import com.greenfox.oauth_practice.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

  Iterable<Order> findAllByStatus(String status);

  Iterable<Order> findAllByStatusAndTopping(String status, String topping);

}
