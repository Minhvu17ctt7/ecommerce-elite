package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.NewCartItemRequestDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<CartResponseDto> addNewItemsToCart(List<NewCartItemRequestDto> newCartItemRequestDtoList, Long userId) {
        User user = User.builder().id(userId).build();

        Cart cartUser = checkCartExistByUser(user);

        Set<CartItem> cartItemCurrentList = cartUser.getCartItems();

        List<Long> productIdList = newCartItemRequestDtoList.stream().map(newCartItemRequestDto -> newCartItemRequestDto.getProductId()).collect(Collectors.toList());
        List<Product> productOfNewCartItems = productRepository.findAllByIdIn(productIdList);
        List<CartItem> cartItemSaveList = new ArrayList<>();

        newCartItemRequestDtoList.forEach(newCartItemRequestDto -> {
                Optional<Product> productOptional = productOfNewCartItems.stream().filter(p -> p.getId() == newCartItemRequestDto.getProductId()).findFirst();
                Optional<CartItem> cartItemExistOptional = cartItemCurrentList.stream().filter(p -> p.getProduct().getId() == newCartItemRequestDto.getProductId()).findFirst();
                Product productOfCartItem = productOptional.orElseThrow(() -> new NotFoundException("Not found product with id: " + newCartItemRequestDto.getProductId()));

                CartItem cartItemSave = new CartItem();
                if(cartItemExistOptional.isPresent()) {
                    cartItemSave = modelMapper.map(cartItemExistOptional.get(), CartItem.class);
                    cartItemSave.setQuantity(cartItemSave.getQuantity() + newCartItemRequestDto.getQuantity());
                }else {
                    cartItemSave = CartItem.builder().cart(cartUser).product(productOfCartItem).quantity(newCartItemRequestDto.getQuantity()).build();
                }

                if (cartItemSave.getQuantity() > productOfCartItem.getQuantity()) {
                    throw new NotAcceptableException("Product out of stock");
                }

            cartItemSaveList.add(cartItemSave);
        });

        List<CartItem> cartItemSavedList = cartItemRepository.saveAll(cartItemSaveList);
        return util.mapList(cartItemSavedList, CartResponseDto.class);
    }

    private Cart checkCartExistByUser(User user) {
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);
        if(cartOptional.isEmpty()) {
            Cart cartSave = Cart.builder().user(user).build();
            return cartRepository.save(cartSave);
        }else {
            return cartOptional.get();
        }
    }
}
