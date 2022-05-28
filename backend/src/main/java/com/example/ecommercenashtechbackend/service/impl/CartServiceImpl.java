package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.CartItemRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CartItemResponseDto;
import com.example.ecommercenashtechbackend.dto.response.CartResponseDto;
import com.example.ecommercenashtechbackend.entity.Cart;
import com.example.ecommercenashtechbackend.entity.CartItem;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.NotAcceptableException;
import com.example.ecommercenashtechbackend.repository.CartItemRepository;
import com.example.ecommercenashtechbackend.repository.CartRepository;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.CartService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final Util util;
    private final ModelMapper modelMapper;

    @Override
    public CartResponseDto getCart(Long userId) {
        User user = User.builder().id(userId).build();
        Optional<Cart> cartUserOptional = cartRepository.findCartByUser(user);
        Cart cartUser = cartUserOptional.orElseGet(() -> {
            Cart cartSave = Cart.builder().user(user).build();
            return cartRepository.save(cartSave);
        });
        return modelMapper.map(cartUser, CartResponseDto.class);
    }

    @Override
    public List<CartItemResponseDto> deleteItemToCart(List<CartItemRequestDto> cartItemRequestDtoList, Long userId) {
        User user = User.builder().id(userId).build();

        Cart cartUser = checkCartExistByUser(user);

        Set<CartItem> cartItemCurrentList = cartUser.getCartItems();

        if (cartItemCurrentList.size() == 0) {
            throw new NotAcceptableException("Product not exist in cart");
        }

        List<Long> productIdList = cartItemRequestDtoList.stream().map(cartItemRequestDto -> cartItemRequestDto.getProductId()).collect(Collectors.toList());
        List<Product> productOfNewCartItems = productRepository.findAllByIdIn(productIdList);
        List<CartItem> cartItemSaveList = new ArrayList<>();

        cartItemRequestDtoList.forEach(cartItemRequestDto -> {
            Optional<Product> productOptional = productOfNewCartItems.stream().filter(p -> p.getId() == cartItemRequestDto.getProductId()).findFirst();
            Product productOfCartItem = productOptional.orElseThrow(() -> new NotFoundException("Not found product with id: " + cartItemRequestDto.getProductId()));

            Optional<CartItem> cartItemExistedOptional = cartItemCurrentList.stream().filter(p -> p.getProduct().getId() == cartItemRequestDto.getProductId()).findFirst();

            CartItem cartItemExisted = cartItemExistedOptional.orElseThrow(() -> new NotAcceptableException("Product not exist in cart"));

            CartItem cartItemSave = modelMapper.map(cartItemExisted, CartItem.class);
            int quantity = cartItemSave.getQuantity() - cartItemRequestDto.getQuantity();
            if (quantity < 0) {
                throw new NotAcceptableException("Product quantity invalid");
            } else if (quantity == 0) {
                cartItemRepository.deleteCartItem(cartItemExisted.getId());
            } else {
                cartItemSave.setQuantity(quantity);
                cartItemSaveList.add(cartItemSave);
            }

            cartUser.setTotalPrice(cartUser.getTotalPrice() - cartItemRequestDto.getQuantity() * productOfCartItem.getPrice());
            cartUser.setTotalItem(cartUser.getTotalItem() - cartItemRequestDto.getQuantity());
        });

        List<CartItem> cartItemSavedList = cartItemRepository.saveAll(cartItemSaveList);
        cartRepository.save(cartUser);
        return util.mapList(cartItemSavedList, CartItemResponseDto.class);
    }

    @Override
    public List<CartItemResponseDto> addItemToCart(List<CartItemRequestDto> cartItemRequestDtoList, Long userId) {
        User user = User.builder().id(userId).build();

        Cart cartUser = checkCartExistByUser(user);

        Set<CartItem> cartItemCurrentList = cartUser.getCartItems();

        List<Long> productIdList = cartItemRequestDtoList.stream().map(cartItemRequestDto -> cartItemRequestDto.getProductId()).collect(Collectors.toList());
        List<Product> productOfNewCartItems = productRepository.findAllByIdIn(productIdList);
        List<CartItem> cartItemSaveList = new ArrayList<>();

        cartItemRequestDtoList.forEach(cartItemRequestDto -> {
            Optional<Product> productOptional = productOfNewCartItems.stream().filter(p -> p.getId() == cartItemRequestDto.getProductId()).findFirst();
            Product productOfCartItem = productOptional.orElseThrow(() -> new NotFoundException("Not found product with id: " + cartItemRequestDto.getProductId()));

            if(productOfCartItem.getQuantity() == 0) {
                throw new NotAcceptableException("Product out of stock");
            }

            Optional<CartItem> cartItemExistedOptional = Optional.ofNullable(null);

            if (cartItemCurrentList != null) {
                cartItemExistedOptional = cartItemCurrentList.stream().filter(p -> p.getProduct().getId() == cartItemRequestDto.getProductId()).findFirst();
            }

            CartItem cartItemSave = new CartItem();

            if (cartItemExistedOptional.isPresent()) {
                CartItem cartItemExisted = cartItemExistedOptional.get();
                cartItemSave = modelMapper.map(cartItemExisted, CartItem.class);
                int quantity = cartItemSave.getQuantity() + cartItemRequestDto.getQuantity();
                cartItemSave.setQuantity(quantity);
            } else {
                cartItemSave = CartItem.builder().cart(cartUser).product(productOfCartItem).quantity(cartItemRequestDto.getQuantity()).build();
            }

            if (cartItemSave.getQuantity() > productOfCartItem.getQuantity()) {
                throw new NotAcceptableException("Product quantity not enough");
            }

            cartUser.setTotalPrice(cartUser.getTotalPrice() + cartItemRequestDto.getQuantity() * productOfCartItem.getPrice());
            cartUser.setTotalItem(cartUser.getTotalItem() + cartItemRequestDto.getQuantity());
            cartItemSaveList.add(cartItemSave);
        });

        List<CartItem> cartItemSavedList = cartItemRepository.saveAll(cartItemSaveList);
        cartRepository.save(cartUser);
        return util.mapList(cartItemSavedList, CartItemResponseDto.class);
    }

    private Cart checkCartExistByUser(User user) {
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);
        if (cartOptional.isEmpty()) {
            Cart cartSave = new Cart();
            cartSave.setUser(user);
            return cartRepository.save(cartSave);
        } else {
            return cartOptional.get();
        }
    }

}
