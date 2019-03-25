package com.greenfox.oauth_practice.mapper;

import com.greenfox.oauth_practice.dto.OrderDTO;
import com.greenfox.oauth_practice.dto.OrderDetailsResponseDTO;
import com.greenfox.oauth_practice.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {

  public Order DTOToEntity(OrderDTO orderDTO) {
    Order order = new Order();
    order.setName(orderDTO.getName());
    order.setAddress(orderDTO.getAddress());
    order.setBase(orderDTO.getBase());
    order.setTopping(orderDTO.getTopping());
    order.setStatus("ordered");
    return order;
  }

  public OrderDetailsResponseDTO entityToDetailsResponseDTO(Order order) {
    OrderDetailsResponseDTO orderDetailsResponseDTO = new OrderDetailsResponseDTO();
    orderDetailsResponseDTO.setId(order.getId());
    orderDetailsResponseDTO.setName(order.getName());
    orderDetailsResponseDTO.setBase(order.getBase());
    orderDetailsResponseDTO.setTopping(order.getTopping());
    orderDetailsResponseDTO.setStatus(order.getStatus());
    return orderDetailsResponseDTO;
  }

}
