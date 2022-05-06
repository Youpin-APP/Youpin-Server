package com.example.youpin.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("pic")
public class Pic {
    Integer pid;//图片位置
    Integer pos;//图片使用类型，0：顶部轮播，1：底部详情
    Integer gid;
    String dir;
}
