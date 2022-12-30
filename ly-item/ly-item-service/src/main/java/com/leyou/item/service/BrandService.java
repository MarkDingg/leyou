package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.Lyexception;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;
    private CategoryMapper categoryMapper;

    public PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        // 返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        // 新增品牌信息
        brand.setId(null);
       int count= brandMapper.insert(brand);
       if (count != 1){
           throw new Lyexception(ExceptionEnum.BRAND_SAVE_ERROR);
       }
        // 新增品牌和分类中间表
        for (Long cid : cids) {
           count= brandMapper.insertCategoryBrand(cid, brand.getId());
           if (count !=1){
               throw new Lyexception(ExceptionEnum.BRAND_SAVE_ERROR);
           }
        }
    }


    @Transactional
    public void updateBrand(List<Long> cids, Brand brand) {
        // 先更新 Brand
        brandMapper.updateByPrimaryKey(brand);
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(brand.getId());
        // 再新增中间表
        for (Long cid : cids) {
            brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    /**
     * 删除品牌
     * @param bid
     */
    @Transactional
    public void deleteBrand(Long bid) {
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(bid);
        // 删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }

    public List<Brand> queryBrandByCategory(Long cid) {
        return this.brandMapper.queryByCategoryId(cid);
    }


    public List<Brand> queryBrandByCid(Long cid) {
        return this.brandMapper.queryByCategoryId(cid);
    }
}
