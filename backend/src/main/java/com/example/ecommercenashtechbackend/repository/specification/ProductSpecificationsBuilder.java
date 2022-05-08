package com.example.ecommercenashtechbackend.repository.specification;

import com.example.ecommercenashtechbackend.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public ProductSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public ProductSpecificationsBuilder with(String key, String operation, Object value, boolean orPredicate) {
        params.add(new SearchCriteria(key, operation, value, orPredicate));
        return this;
    }

    public ProductSpecificationsBuilder (List<SearchCriteria> searchCriteriaList) {
        params = searchCriteriaList;
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(ProductSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result =  params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
