package com.example.ecommercenashtechbackend.dto.request;

import javax.validation.Valid;
import java.util.*;

public class ValidList<E> {

    @Valid
    private List<E> list;

    public ValidList() {
        this.list = new ArrayList<E>();
    }

    public ValidList(List<E> list) {
        this.list = list;
    }

    // Bean-like methods, used by javax.validation but ignored by JSON parsing

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}