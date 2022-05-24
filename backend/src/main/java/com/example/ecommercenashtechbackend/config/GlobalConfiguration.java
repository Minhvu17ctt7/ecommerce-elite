package com.example.ecommercenashtechbackend.config;

import com.example.ecommercenashtechbackend.dto.response.CartItemResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.entity.Cart;
import com.example.ecommercenashtechbackend.entity.CartItem;
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

        Converter<CartItem, CartItemResponseDto> cartItemConverter = new Converter<CartItem, CartItemResponseDto>()
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

        modelMapper.addConverter(cartItemConverter);
        modelMapper.addConverter(convertNull);
        return modelMapper;
    }

}