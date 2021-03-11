package com.zzw.mapper;

import com.zzw.entity.Cat;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cat
     *
     * @mbg.generated Tue Mar 09 10:25:08 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cat
     *
     * @mbg.generated Tue Mar 09 10:25:08 CST 2021
     */
    int insert(Cat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cat
     *
     * @mbg.generated Tue Mar 09 10:25:08 CST 2021
     */
    Cat selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cat
     *
     * @mbg.generated Tue Mar 09 10:25:08 CST 2021
     */
    List<Cat> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cat
     *
     * @mbg.generated Tue Mar 09 10:25:08 CST 2021
     */
    int updateByPrimaryKey(Cat record);
}