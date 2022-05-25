package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.OrderRequestDto;
import com.example.ecommercenashtechbackend.dto.response.OrderResponseDto;
import com.example.ecommercenashtechbackend.entity.CartItem;
import com.example.ecommercenashtechbackend.entity.Order;
import com.example.ecommercenashtechbackend.entity.OrderItem;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.NotAcceptableException;
import com.example.ecommercenashtechbackend.repository.CartItemRepository;
import com.example.ecommercenashtechbackend.repository.OrderRepository;
import com.example.ecommercenashtechbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = User.builder().id(userId).build();
        List<CartItem> cartItemList = cartItemRepository.findAllById(orderRequestDto.getCartItemIdList());
        Set<OrderItem> orderItemsSave = new HashSet<>();

        Order orderSave = modelMapper.map(orderRequestDto, Order.class);
        orderSave.setUser(user);
        cartItemList.forEach(cartItem -> {
            if(cartItem.getQuantity() > cartItem.getProduct().getQuantity()) {
                throw new NotAcceptableException("Product out stock");
            }
            OrderItem orderItemTemp = modelMapper.map(cartItem, OrderItem.class);
            orderItemsSave.add(orderItemTemp);

            orderSave.setTotalPrice(orderSave.getTotalPrice() + cartItem.getQuantity() * cartItem.getProduct().getPrice());
            orderSave.setTotalItem(orderSave.getTotalItem() + cartItem.getQuantity());

            cartItemRepository.deleteById(cartItem.getId());
        });

        orderSave.setOrderItems(orderItemsSave);
        Order orderSaved = orderRepository.save(orderSave);
        return modelMapper.map(orderSaved, OrderResponseDto.class);

    }
}
