package com.zzw.service;

import com.zzw.controller.TestController;
import com.zzw.entity.Cat;
import com.zzw.mapper.CatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FirstService {

    @Autowired
    private CatMapper catMapper;
    public Collection<Cat> selectAllCat(){
        Collection<Cat> cats = new ArrayList<>();
        return catMapper.selectAll();
    }
}
