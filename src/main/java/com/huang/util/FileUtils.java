package com.huang.util;




import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class FileUtils {



    /**
     * 允许的文件名的长度
     */
    private static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 允许的文件名后缀
     */
    private static final String FILE_SUFFIX = "lrc";


//    private static final String UPLOAD_PATH = System.getProperty("file.upload.path");



    private static String UPLOAD_PATH = "H:/test";

    /**
     * 通过文件名来判断文件的后缀名是否是lrc
     * @param files 文件名字
     * @return
     */
    public static boolean isLrc(MultipartFile files){

//        int indexName = fileName.indexOf(".");
//        String suffixName = fileName.substring(indexName + 1);
        if(FILE_SUFFIX.equals(getExtension(files))){
            return true;
        }else {
            return false;
        }

    }

    /**
     * 此处自定义文件名，UUID + extension
     * @param file
     * @return
     */
    private static String getPathName(MultipartFile file) {
        String extension = getExtension(file);
        System.out.println(UUID.randomUUID().toString() + "." + extension);
        return UUID.randomUUID().toString() + "." + extension;
    }


    /**
     * 获取file的对象
     * @param files
     * @return
     */
    public static File getFile(MultipartFile files) throws IOException {

        File file = new File(UPLOAD_PATH + "/" + getPathName(files));
        //判断要上传的目录是否存在，如果不存在就创建一个目录
        if(!file.getParentFile().exists()){

            file.getParentFile().mkdirs();

        }
        if(!file.exists()){
            file.createNewFile();
        }
        System.out.println(file);
        return file;

    }


    /**
     * 获取文件拓展名
     * @param file
     * @return
     */
    private static String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }




}
