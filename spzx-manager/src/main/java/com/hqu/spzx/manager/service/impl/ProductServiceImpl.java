package com.hqu.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.mapper.*;
import com.hqu.spzx.manager.service.ProductService;
import com.hqu.spzx.model.dto.product.ProductDto;
import com.hqu.spzx.model.entity.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page,limit);
        List<Product> productList=productMapper.findByPage(productDto);
        PageInfo<Product> productPageInfo = new PageInfo<>(productList);
        return productPageInfo;
    }
    @Transactional
    @Override
    public void save(Product product) {
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId()+"_"+i);
            productSku.setSkuName(product.getName());
            productSku.setSaleNum(0);
            productSku.setProductId(product.getId());
            productSku.setStatus(0);
        }
        productSkuMapper.batchSave(productSkuList);
        ProductDetails productDetails=new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

    @Override
    public Product getById(Long id) {
        Product product=productMapper.getById(id);
        Brand brand=brandMapper.getById(product.getBrandId());
        Category category1=categoryMapper.getByID(product.getCategory1Id());
        Category category2=categoryMapper.getByID(product.getCategory2Id());
        Category category3=categoryMapper.getByID(product.getCategory3Id());
        product.setBrandName(brand.getName());
        product.setCategory1Name(category1.getName());
        product.setCategory2Name(category2.getName());
        product.setCategory3Name(category3.getName());
        // 根据商品的id查询sku数据
        List<ProductSku> productSkuList = productSkuMapper.selectByProductId(id);
        product.setProductSkuList(productSkuList);

        // 根据商品的id查询商品详情数据
        ProductDetails productDetails = productDetailsMapper.selectByProductId(product.getId());
        product.setDetailsImageUrls(productDetails.getImageUrls());
        return product;
    }

    @Override
    public void updateById(Product product) {
        productMapper.updateById(product);
        productDetailsMapper.updateById(product.getId(),product.getDetailsImageUrls());
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku productSku : productSkuList) {
            productSkuMapper.update(productSku);
        }
    }

    @Override
    public void deleteById(Long id) {
        productMapper.deletedById(id);
        productSkuMapper.deleteById(id);
        productDetailsMapper.deleteById(id);
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product=new Product();
        product.setId(id);
        if(auditStatus==1){
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        }
        if(auditStatus==-1){
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product=new Product();
        product.setId(id);
        if(status==1){
            product.setStatus(1);
        }
        if(status==-1) {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
