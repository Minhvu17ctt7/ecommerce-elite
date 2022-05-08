package com.example.ecommercenashtechbackend.repository.specification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;
}
