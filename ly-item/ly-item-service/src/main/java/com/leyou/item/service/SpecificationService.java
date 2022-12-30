package com.leyou.item.service;

import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Transactional
    public Specification queryById(Long id) {
        return this.specificationMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加规格参数模板
     * @param specification
     */
    public void saveSpecification(Specification specification) {
        this.specificationMapper.insert(specification);
    }

    /**
     * 修改规格参数模板
     * @param specification
     */
    public void updateSpecification(Specification specification) {
        this.specificationMapper.updateByPrimaryKeySelective(specification);
    }

/**
 * 删除规格参数模板
 * @param specification
*/
public void deleteSpecification(Specification specification) {

    this.specificationMapper.deleteByPrimaryKey(specification);
    }
}