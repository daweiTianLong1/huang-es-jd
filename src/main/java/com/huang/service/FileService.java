package com.huang.service;

import com.alibaba.fastjson.JSONObject;
import com.huang.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传的逻辑实现
 * @author PanYi
 * @date 2021/08/1
 *
 *
 * 9日 14:34
 */
@Service
@Slf4j
public class FileService {

    public String uploadFile(MultipartFile files[]) {

        JSONObject object = new JSONObject();
        for (int i = 0; i < files.length; i++) {
            //获取文件名
            if(files[i].isEmpty()){
                object.put("Fail","文件不能为空");
                return object.toString();
            }
            String fileName = files[i].getOriginalFilename();
            if(!FileUtils.isLrc(fileName)){
                object.put("Fail","文件的类型名必须为lrc格式");
                return object.toString();
            }
            try{
                files[i].transferTo(FileUtils.getFile(fileName));
            }catch (Exception e){
                log.error("{}",e);
                object.put("success",2);
                object.put("result","程序错误，请重新上传");
                return object.toString();
            }
        }
        object.put("success",1);
        object.put("result","文件上传成功");
        return object.toString();
    }


}
