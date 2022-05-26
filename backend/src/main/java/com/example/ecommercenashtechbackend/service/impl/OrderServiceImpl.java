package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.OrderRequestDto;
import com.example.ecommercenashtechbackend.dto.response.OrderResponseDto;
import com.example.ecommercenashtechbackend.entity.*;
import com.example.ecommercenashtechbackend.exception.custom.NotAcceptableException;
import com.example.ecommercenashtechbackend.exception.custom.UnauthorizedException;
import com.example.ecommercenashtechbackend.repository.CartItemRepository;
import com.example.ecommercenashtechbackend.repository.CartRepository;
import com.example.ecommercenashtechbackend.repository.OrderRepository;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
import com.example.ecommercenashtechbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = User.builder().id(userId).build();
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);

        Cart cartUser = cartOptional.get();

        List<CartItem> cartItemRequestList = cartItemRepository.findAllById(orderRequestDto.getCartItemIdList());
        Set<OrderItem> orderItemsSave = new HashSet<>();
        List<Product> productUpdateList = new ArrayList<>();

        cartItemRequestList.forEach(cartItemRequest -> {
            boolean checkExists = cartUser.getCartItems().stream().anyMatch(cartItemUser -> cartItemUser.getId() == cartItemRequest.getId());
            if(!checkExists) {
                throw new UnauthorizedException("Product '" + cartItemRequest.getProduct().getName() + "' do not exist in your cart");
            }
        });

        Order orderSave = modelMapper.map(orderRequestDto, Order.class);
        orderSave.setUser(user);

        cartItemRequestList.forEach(cartItem -> {
            if(cartItem.getQuantity() > cartItem.getProduct().getQuantity()) {
                throw new NotAcceptableException("Product '" + cartItem.getProduct().getName() +"' out of stock");
            }
            OrderItem orderItemTemp = modelMapper.map(cartItem, OrderItem.class);
            orderItemsSave.add(orderItemTemp);

            orderSave.setTotalPrice(orderSave.getTotalPrice() + cartItem.getQuantity() * cartItem.getProduct().getPrice());
            orderSave.setTotalItem(orderSave.getTotalItem() + cartItem.getQuantity());

            Product productUpdate = cartItem.getProduct();
            productUpdate.setQuantity(productUpdate.getQuantity() - cartItem.getQuantity());
            productUpdateList.add(productUpdate);
            cartItemRepository.deleteCartItem(cartItem.getId());
        });

        orderSave.setOrderItems(orderItemsSave);
        Order orderSaved = orderRepository.save(orderSave);

        cartUser.setTotalItem(cartUser.getTotalItem() - orderSave.getTotalItem());
        cartUser.setTotalPrice(cartUser.getTotalPrice() - orderSave.getTotalPrice());
        cartRepository.save(cartUser);

        productRepository.saveAll(productUpdateList);

        return modelMapper.map(orderSaved, OrderResponseDto.class);
    }
}
