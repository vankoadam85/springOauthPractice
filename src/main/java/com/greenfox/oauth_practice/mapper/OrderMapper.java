package com.greenfox.oauth_practice.mapper;

import com.greenfox.oauth_practice.dto.OrderDTO;
import com.greenfox.oauth_practice.dto.OrderDetailsResponseDTO;
import com.greenfox.oauth_practice.entity.Order;

public interface OrderMapper {

  Order DTOToEntity(OrderDTO orderDTO);

  OrderDetailsResponseDTO entityToDetailsResponseDTO(Order order);

}
