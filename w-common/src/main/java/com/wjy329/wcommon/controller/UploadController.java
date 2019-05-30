package com.wjy329.wcommon.controller;/**
 * @Author wjy329
 * @Time 2019/1/2711:41 AM
 * @description
 */


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wjy329.wcommon.model.WangEditor;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-27 11:41
 **/
@Controller
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    @RequiresPermissions("upload:editimg")
    public WangEditor singleFileUpload(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request) {
            String blogUrl = "https://www.wjy329.com";
        try {
            String filename = System.currentTimeMillis() + multipartFile.getOriginalFilename();
            // 获取项目路径
            String realPath = request.getSession().getServletContext().getRealPath("upload");

            InputStream inputStream = multipartFile.getInputStream();
            String contextPath = request.getContextPath();
            // 服务器根目录的路径
            // 获取文件名称
            // 将文件上传的服务器根目录下的upload文件夹
            File file = new File(realPath, filename);
            FileUtils.copyInputStreamToFile(inputStream, file);
            // 返回图片访问路径
//            String url = request.getScheme() + "://" + request.getServerName()
//                    + ":" + request.getServerPort() + "/upload/" + filename;
            String url = blogUrl + "/upload/" + filename;
            System.out.println("realPath"+url);
            String [] str = {url};
            WangEditor we = new WangEditor(str);
            return we;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 上传文件到七牛
     * @author wjy329
     * @Date 2019/3/2
     */
    @ResponseBody
    @RequestMapping("/uploadResource")
    @RequiresPermissions("upload:resource")
    public JSONObject upload(MultipartFile file) throws IOException {
        JSONObject result = new JSONObject();
        String qiniuUrl = "http://qiniu.wjy329.com/";
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "w2tvLIqbT22_8cdgSHsCsGrBGkW-f7mnBKoUyH9n";
        String secretKey = "YBs-xPsYyeTPYE8VaE4Pdlp3eQfnB4Ce12jt2AbS";
        String bucket = "wjyblog";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = file.getOriginalFilename();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            byte[] localFilePath = file.getBytes();
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //拼接url
            String responseUrl = qiniuUrl+putRet.key;
            result.put("data",responseUrl);
            System.out.println("上传文件成功");
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return result;
    }


}
