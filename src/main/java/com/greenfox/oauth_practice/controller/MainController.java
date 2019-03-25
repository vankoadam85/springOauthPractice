package com.greenfox.oauth_practice.controller;

import com.greenfox.oauth_practice.dto.OrderDTO;
import com.greenfox.oauth_practice.entity.Order;
import com.greenfox.oauth_practice.exception.OrderNotFoundException;
import com.greenfox.oauth_practice.mapper.OrderMapper;
import com.greenfox.oauth_practice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

  private OrderMapper orderMapper;
  private OrderService orderService;

  @Autowired
  MainController(OrderMapper orderMapper, OrderService orderService) {
    this.orderMapper = orderMapper;
    this.orderService = orderService;
  }

  @GetMapping("/orders")
  public String index(Model model) {
    model.addAttribute("orderDTO", new OrderDTO());
    return "index";
  }

  @PostMapping("/orders")
  public String saveOrder(OrderDTO orderDTO) {
    Order order = orderService.saveOrder(orderMapper.DTOToEntity(orderDTO));
    return"redirect:/orders/" + order.getId();
  }

  @GetMapping("/orders/{orderId}")
  public String orderSummary(@PathVariable long orderId, Model model) {
    model.addAttribute("order", orderService.getOrderById(orderId).orElseThrow(OrderNotFoundException::new));
    return "orderDetails";
  }

}
