package com.minis.test.service.impl;

import com.minis.test.service.XService;
import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:25
 */
@Data
public class WangCaiServiceImpl implements XService {
    @Override
    public void sayWhat() {
        System.out.println("汪汪汪");
    }
}
