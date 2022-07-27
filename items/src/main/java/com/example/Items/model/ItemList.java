package com.example.Items.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ItemList extends PageImpl<Item> {

    public ItemList(List<Item> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
