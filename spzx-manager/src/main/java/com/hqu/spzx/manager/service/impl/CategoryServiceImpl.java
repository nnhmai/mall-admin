package com.hqu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.hqu.spzx.commonservice.exception.MyException;
import com.hqu.spzx.manager.listenner.ExcelListener;
import com.hqu.spzx.manager.mapper.CategoryMapper;
import com.hqu.spzx.manager.service.CategoryService;
import com.hqu.spzx.model.entity.product.Category;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Long parentId) {
        List<Category> categoryList = categoryMapper.findByParentId(parentId);
        if(!CollectionUtils.isEmpty(categoryList)){
            categoryList.forEach(item->{
                int count = categoryMapper.countByParentId(item.getId());
                if(count>0){
                    item.setHasChildren(true);
                }
                else{
                    item.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {

            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            List<Category> categoryList=categoryMapper.selectAll();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            // 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
            for(Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);
            }
       catch (Exception e){
            e.printStackTrace();
       }
    }

    @SneakyThrows
    @Override
    public void importData(MultipartFile file) {
        try {
            //创建监听器对象，传递mapper对象
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
            //调用read方法读取excel数据
            EasyExcel.read(file.getInputStream(),
                    CategoryExcelVo.class,
                    excelListener).sheet().doRead();
        } catch (IOException e) {
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
