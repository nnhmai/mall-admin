package com.hqu.spzx.manager.service;

import com.hqu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findByParentId(Long parentId);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
