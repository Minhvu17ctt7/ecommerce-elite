package com.example.ecommercenashtechbackend.config;

import com.example.ecommercenashtechbackend.dto.response.CartItemResponseDto;
import com.example.ecommercenashtechbackend.dto.response.OrderItemResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.entity.Cart;
import com.example.ecommercenashtechbackend.entity.CartItem;
import com.example.ecommercenashtechbackend.entity.OrderItem;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.repository.CartRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.print.attribute.standard.Destination;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class GlobalConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        Converter<?, ?> convertNull =
                context -> context.getSource() == null ? context.getDestination() : context.getSource();

        Converter<CartItem, CartItemResponseDto> cartItemToCartItemResponseDtoConverter = new Converter<CartItem, CartItemResponseDto>()
        {
            public CartItemResponseDto convert(MappingContext<CartItem, CartItemResponseDto> context)
            {
                CartItem cartItem = context.getSource();
                CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();

                cartItemResponseDto.setId(cartItem.getId());
                cartItemResponseDto.setQuantity(cartItem.getQuantity());
                cartItemResponseDto.setProductId(cartItem.getProduct().getId());
                cartItemResponseDto.setNameProduct(cartItem.getProduct().getName());
                cartItemResponseDto.setPriceProduct(cartItem.getProduct().getPrice());
                cartItemResponseDto.setMainImage(cartItem.getProduct().getMainImage());

                return cartItemResponseDto;
            }
        };

        Converter<OrderItem, OrderItemResponseDto> orderItemToOrderItemResponseDtoConverter = new Converter<OrderItem, OrderItemResponseDto>()
        {
            public OrderItemResponseDto convert(MappingContext<OrderItem, OrderItemResponseDto> context)
            {
                OrderItem orderItem = context.getSource();
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();

                orderItemResponseDto.setId(orderItem.getId());
                orderItemResponseDto.setQuantity(orderItem.getQuantity());
                orderItemResponseDto.setProductId(orderItem.getProduct().getId());
                orderItemResponseDto.setNameProduct(orderItem.getProduct().getName());
                orderItemResponseDto.setPriceProduct(orderItem.getProduct().getPrice());
                orderItemResponseDto.setMainImage(orderItem.getProduct().getMainImage());

                return orderItemResponseDto;
            }
        };

        Converter<CartItem, OrderItem> cartItemToOrderItemConverter = new Converter<CartItem, OrderItem>()
        {
            public OrderItem convert(MappingContext<CartItem, OrderItem> context)
            {
                CartItem cartItem = context.getSource();
                OrderItem orderItem = new OrderItem();

                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setProduct(cartItem.getProduct());

                return orderItem;
            }
        };

        modelMapper.addConverter(cartItemToCartItemResponseDtoConverter);
        modelMapper.addConverter(orderItemToOrderItemResponseDtoConverter);
        modelMapper.addConverter(cartItemToOrderItemConverter);
        modelMapper.addConverter(convertNull);
        return modelMapper;
    }

}