package org.gameshop.service.impl;


import org.gameshop.data.entities.Game;
import org.gameshop.data.entities.Order;
import org.gameshop.data.entities.User;
import org.gameshop.data.repositories.GameRepository;
import org.gameshop.data.repositories.OrderRepository;
import org.gameshop.data.repositories.UserRepository;
import org.gameshop.service.ShoppingCartService;
import org.gameshop.service.UserService;
import org.gameshop.service.dtos.CartItemDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;
    private Set<Game> cart;

    public ShoppingCartServiceImpl(UserService userService, UserRepository userRepository, GameRepository gameRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.cart = new HashSet<>();
    }

    @Override
    public String addItem(CartItemDTO item) {
        User user = this.userService.getLoggedIn();
        if (user == null) {
            return "No user is logged in";
        }

        Optional<Game> optionalGame = this.gameRepository.findByTitle(item.getTitle());
        if (optionalGame.isEmpty()) {
            return "No such game exists";
        }

        Game game = optionalGame.get();
        this.cart.add(game);
        return String.format("%s added to cart", game.getTitle());

    }

    @Override
    public String deleteItem(CartItemDTO item) {
        User user = this.userService.getLoggedIn();
        if (user == null) {
            return "No user is logged in";
        }

        Optional<Game> optionalGame = this.gameRepository.findByTitle(item.getTitle());
        if (optionalGame.isEmpty()) {
            return "No such game exists";
        }
        Game game = optionalGame.get();
        this.cart.remove(game);
        return String.format("%s removed from cart", game.getTitle());
    }

    @Override
    public String buyItem() {
        User user = this.userService.getLoggedIn();
        if (user == null) {
            return "No user is logged in";
        }

        user.getGames().addAll(this.cart);
        this.userRepository.saveAndFlush(user);
        Order order = new Order(LocalDateTime.now(), user, this.cart);
        this.orderRepository.saveAndFlush(order);

        String output = String.format("Successfully bought games:%n%s", this.cart.stream().map(game -> String.format(" -%s", game.getTitle())).collect(Collectors.joining("\n")));
        this.cart = new HashSet<>();
        return output;
    }
}
