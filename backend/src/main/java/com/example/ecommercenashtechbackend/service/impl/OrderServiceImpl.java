package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.OrderRequestDto;
import com.example.ecommercenashtechbackend.dto.response.OrderResponseDto;
import com.example.ecommercenashtechbackend.dto.response.PaginationResponseDto;
import com.example.ecommercenashtechbackend.entity.*;
import com.example.ecommercenashtechbackend.exception.custom.NotAcceptableException;
import com.example.ecommercenashtechbackend.exception.custom.UnauthorizedException;
import com.example.ecommercenashtechbackend.repository.CartItemRepository;
import com.example.ecommercenashtechbackend.repository.CartRepository;
import com.example.ecommercenashtechbackend.repository.OrderRepository;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
import com.example.ecommercenashtechbackend.service.OrderService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final Util util;

    @Override
    public PaginationResponseDto<OrderResponseDto> getListOrderPagination(Long userId, int pageNumber, int pageSize, boolean deleted) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        User user = User.builder().id(userId).build();
        Page<Order> orderPage = orderRepository.findAllByUserAndDeleted(user, deleted, pageable);
        List<OrderResponseDto> orderResponseDtoList = util.mapList(orderPage.getContent(), OrderResponseDto.class);
        PaginationResponseDto<OrderResponseDto> result = new PaginationResponseDto<>(orderResponseDtoList, orderPage.getTotalPages(), pageSize);
        return result;
    }

    @Override
    public OrderResponseDto getDetailOrder(Long userId, Long orderId, boolean deleted) {
        User user = User.builder().id(userId).build();
        Optional<Order> orderOptional = orderRepository.findOrderByIdAndUserAndDeleted(orderId, user, deleted);
        Order order = orderOptional.orElseThrow(() -> new NotFoundException("Not found order id: " + orderId));
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = User.builder().id(userId).build();
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);

        Cart cartUser = cartOptional.get();

        List<Long> cartItemIdList = orderRequestDto.getCartItemList().stream().map(cartItemRequestDto -> cartItemRequestDto.getId()).collect(Collectors.toList());;

        List<CartItem> cartItemRequestList = cartItemRepository.findAllById(cartItemIdList);

        cartItemIdList.forEach(cartItemId -> {
            boolean checkExist = cartItemRequestList.stream().anyMatch(cartItemRequest -> cartItemRequest.getId() == cartItemId);
            if (!checkExist) {
                throw new NotFoundException("Cart item with id " + cartItemId + " not exist");
            }
        });

        Set<OrderItem> orderItemsSave = new HashSet<>();
        List<Product> productUpdateList = new ArrayList<>();

        cartItemRequestList.forEach(cartItemRequest -> {
            boolean checkExists = cartUser.getCartItems().stream().anyMatch(cartItemUser -> cartItemUser.getId() == cartItemRequest.getId());
            if (!checkExists) {
                throw new UnauthorizedException("Product '" + cartItemRequest.getProduct().getName() + "' do not exist in your cart");
            }
        });

        Order orderSave = modelMapper.map(orderRequestDto, Order.class);
        orderSave.setUser(user);

        cartItemRequestList.forEach(cartItem -> {
            if (cartItem.getQuantity() > cartItem.getProduct().getQuantity()) {
                throw new NotAcceptableException("Product '" + cartItem.getProduct().getName() + "' out of stock");
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
