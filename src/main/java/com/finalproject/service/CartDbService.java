package com.finalproject.service;

import com.finalproject.dao.CartRepository;
import com.finalproject.domain.Cart;
import com.finalproject.exception.CartNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartDbService {


    private final CartRepository cartRepository;

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public Optional<Cart> getCart(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
