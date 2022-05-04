package com.example.youpin.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("cart")
public class Cart {
    @Id
    Integer did;
    Integer cid;
    String name;
}
