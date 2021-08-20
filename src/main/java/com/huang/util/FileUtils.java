package com.huang.util;




import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
     * @param fileName 文件名字
     * @return
     */
    public static boolean isLrc(String fileName){

        int indexName = fileName.indexOf(".");
        String suffixName = fileName.substring(indexName + 1);
        if(FILE_SUFFIX.equals(suffixName)){
            return true;
        }else {
            return false;
        }

    }


    /**
     * 获取file的对象
     * @param fileName
     * @return
     */
    public static File getFile(String fileName) throws IOException {

        File file = new File(UPLOAD_PATH + "/" + fileName);
        //判断要上传的目录是否存在，如果不存在就创建一个目录
        if(!file.getParentFile().exists()){

            file.getParentFile().mkdirs();

        }
        if(!file.exists()){
            file.createNewFile();
        }
        return file;

    }

//    public static boolean isEmpty(MultipartFile file) throws Exception {
//
//        JSONObject object = new JSONObject();
//        if(file.isEmpty()){
//            object.put("Fail","不能上传空文件");
//
//            throw new Exception(object.toString());
//        }
//        return true;
//    }





}
