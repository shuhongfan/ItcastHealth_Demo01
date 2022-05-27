package com.shf.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiNiuTest {
    //构造一个带指定 Region 对象的配置类
    Configuration cfg = new Configuration(Region.huanan());
    //...其他参数参考类注释

    //...生成上传凭证，然后准备上传
    public  static String accessKey = "CO1_LZvlnRRvDDoCccqFc_RmVItaxu07p9T-pby0";
    public  static String secretKey = "B8g6Cd26HwaZO6OF3SIGqi1JmIPv3SwfdRtx3Yvi";
    public  static String bucket = "shf-health";

//    @Test
    public void delete(){
        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

//        String accessKey = "your access key";
//        String secretKey = "your secret key";
//
//        String bucket = "your bucket name";
        String key = "shf.png";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

//    @Test
    public void upload(){
        UploadManager uploadManager = new UploadManager(cfg);
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\shuho\\Pictures\\shf.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "shf.png";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
