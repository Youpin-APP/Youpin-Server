package com.example.youpin.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("district")
public class District {
    @Id
    Integer did;
    Integer cid;
    String dname;
}
