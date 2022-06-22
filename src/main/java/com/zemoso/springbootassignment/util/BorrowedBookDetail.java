package com.zemoso.springbootassignment.util;

import com.zemoso.springbootassignment.entity.Book;
import com.zemoso.springbootassignment.entity.Record;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BorrowedBookDetail {
    private Book book;
    private Record record;
}
