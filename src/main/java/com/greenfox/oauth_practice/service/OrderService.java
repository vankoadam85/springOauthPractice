package com.greenfox.oauth_practice.service;

import com.greenfox.oauth_practice.entity.Order;

import java.util.Optional;

public interface OrderService {

  Order saveOrder(Order order);

  Optional<Order> getOrderById(long id);

  Iterable<Order> getAllOrdersByStatus(String status);

  Iterable<Order> getAllOrdersByStatusAndTopping(String status, String topping);

}
