package com.greenfox.oauth_practice.controller;

import com.greenfox.oauth_practice.dto.OrderDetailsResponseDTO;
import com.greenfox.oauth_practice.dto.OrderStatusUpdateRequestDTO;
import com.greenfox.oauth_practice.entity.Order;
import com.greenfox.oauth_practice.exception.InvalidQueryException;
import com.greenfox.oauth_practice.mapper.OrderMapper;
import com.greenfox.oauth_practice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainRestController {

  private OrderService orderService;
  private OrderMapper orderMapper;

  MainRestController(OrderService orderService, OrderMapper orderMapper) {
    this.orderService = orderService;
    this.orderMapper = orderMapper;
  }

  @GetMapping("/api/orders")
  public List<OrderDetailsResponseDTO> getSortedOrders(@RequestParam String type, @RequestParam String status) {
    List<OrderDetailsResponseDTO> ordersReport = new ArrayList<>();
    if (this.isStatusQueryValid(status)) {
      switch (type) {
        case "all":
          orderService.getAllOrdersByStatus(status)
              .forEach(order -> ordersReport.add(orderMapper.entityToDetailsResponseDTO(order)));
          return ordersReport;
        case "vegetarian":
          orderService.getAllOrdersByStatusAndTopping(status, "smoked tofu")
              .forEach(order -> ordersReport.add(orderMapper.entityToDetailsResponseDTO(order)));
          return ordersReport;
        default:
          throw new InvalidQueryException();
      }
    } else {
      throw new InvalidQueryException();
    }
  }

  private boolean isStatusQueryValid(String status) {
    return (status.equals("ordered") || status.equals("inprogress") || status.equals("done"));
  }

  @PatchMapping("/api/orders/{orderId}")
  public void updateStatus(@PathVariable long orderId, @RequestBody OrderStatusUpdateRequestDTO orderStatusUpdateRequestDTO) {
    if (this.isStatusUpdateRequestValid(orderStatusUpdateRequestDTO.getStatus())) {
      Order orderToUpdate = orderService.getOrderById(orderId).orElseThrow(InvalidQueryException::new);
      orderToUpdate.setStatus(orderStatusUpdateRequestDTO.getStatus());
      orderService.saveOrder(orderToUpdate);
    } else {
      throw new InvalidQueryException();
    }
  }

  private boolean isStatusUpdateRequestValid(String statusUpdate) {
    return (statusUpdate.equals("ordered") || statusUpdate.equals("inprogress") || statusUpdate.equals("done"));
  }

}
