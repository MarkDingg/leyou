package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;


@Data
public class SpuBo extends Spu {
    /**
     * 商品分类名称
     */
    @Transient
  String cname;
    /**
     * 品牌名称
     */
    @Transient
  String bname;

    /**
     * 商品详情
     */
    @Transient
    SpuDetail spuDetail;

    /**
     * sku列表
     */
    @Transient
     List<Sku> skus;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }






    public SpuBo() {
    }

}
