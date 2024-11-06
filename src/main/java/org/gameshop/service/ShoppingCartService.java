package org.gameshop.service;


import org.gameshop.service.dtos.CartItemDTO;

public interface ShoppingCartService {
    String addItem(CartItemDTO item);
    String deleteItem(CartItemDTO item);
    String buyItem();
}
