package com.example.youpin.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tid;
    Integer tfid;
    String tname;
}
