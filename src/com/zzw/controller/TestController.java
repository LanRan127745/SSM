package com.zzw.controller;

import com.zzw.entity.Cat;
import com.zzw.service.FirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TestController {

    @Autowired
    private FirstService firstService;

    @RequestMapping("getAllCat")
    public Collection<Cat> getAllCat(){
        Collection<Cat> cats = firstService.selectAllCat();
        for(Cat cat :cats)
            System.out.println(cat);
        return cats;
    }

}
