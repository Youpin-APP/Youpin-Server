package com.example.youpin.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("token")
public class Token {
    @Id
    private String token;
    private String uid;
}
