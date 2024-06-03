package com.hqu.spzx.controller;


import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.service.FileLoadUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/system")
public class FileLoadUpController {
    @Autowired
    private FileLoadUpService fileLoadUpService;
    @PostMapping(value = "/fileUpload")
    public Result fileLoadUp(@RequestParam(value = "file")MultipartFile multipartFile){
            String fileUrl=fileLoadUpService.fileUpload(multipartFile);
            return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
