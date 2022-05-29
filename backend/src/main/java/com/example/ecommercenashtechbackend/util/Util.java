package com.example.ecommercenashtechbackend.util;

import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.repository.specification.ProductSpecification;
import com.example.ecommercenashtechbackend.repository.specification.ProductSpecificationsBuilder;
import com.example.ecommercenashtechbackend.repository.specification.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class Util {

    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

//    public Specification buildProductSpecifications(String search) {
//        ProductSpecificationsBuilder builder = new ProductSpecificationsBuilder();
//        Pattern pattern = Pattern.compile("([a-zA-Z0-9\\.\\_]+?)(:|<|>)([a-zA-Z0-9 ]+?)(,|;)");
//        Matcher matcher = pattern.matcher(search);
//        while (matcher.find()) {
//            boolean orPredicate = matcher.group(4).equals(",");
//            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), orPredicate);
//        }
//
//        Specification<Product> spec = builder.build();
//        return spec;
//    }

    public List<SearchCriteria> buildListProductSpecifications(String search) {
        List<SearchCriteria> listSpec = new ArrayList<>();
        Pattern pattern = Pattern.compile("([a-zA-Z0-9\\.\\_]+?)(:|<|>)([a-zA-Z0-9 ]+?)(,|;)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search);
        while (matcher.find()) {
            boolean orPredicate = matcher.group(4).equals(",");
            listSpec.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), orPredicate));
        }
        return listSpec;
    }
}
