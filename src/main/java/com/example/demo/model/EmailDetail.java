package com.example.demo.model;

import com.example.demo.entity.Account;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE) //private
@NoArgsConstructor
public class EmailDetail {
    Account receiver;
    String subject;
    String link;
}
