package com.zf.zjtf.util;

import net.sf.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;

public class MyFileUtil {
    private static boolean state = false; //开发false 发布true

    public  static  String FileRead() throws Exception {
        /*
            File file =new File(ResourceUtils.getURL("classpath:").getPath()+"\\userFile\\userinfo.txt");
            if(!file.exists()){
                System.out.println("createFile");
                File fileDir = new File(ResourceUtils.getURL("classpath:").getPath()+"\\userFile");
                fileDir.mkdirs();
                file.createNewFile();
            }*/
        String result;
        InputStream ins = null;
        BufferedReader buf = null;
        Resource resource = new ClassPathResource("userFile/userinfo.txt");
        if(state){
            //发布用
            ins = new FileInputStream("D:\\demo\\classes\\userFile\\userinfo.txt");
        }else{
            //开发用
            ins = resource.getInputStream();
        }
        buf = new BufferedReader(new InputStreamReader(ins,"utf-8"));
        String b;
        StringBuffer sb = new StringBuffer();
        while ((b=buf.readLine())!=null){
            sb.append(b);
        }
        if(ins !=null){
            ins.close();
        }
        if (buf !=null){
            buf.close();
        }
        result = sb.toString();
        return  result;
    }

    public static boolean FileWrite(String openid,String account) throws Exception {
        File file = null;
        FileOutputStream fos = null;
        if(state){
            //发布使用
            fos = new FileOutputStream("D:\\demo\\classes\\userFile\\userinfo.txt");
        }else{
            //开发使用
            file =new File(ResourceUtils.getURL("classpath:").getPath());
            fos = new FileOutputStream(file+"\\userFile\\userinfo.txt");
        }
        String text = "{\"openid\":\""+openid+"\",\"account\":\""+account+"\"}";
        fos.write(text.getBytes());
        fos.close();
        return true;
    }

    /**
     * 验证openid
     * @param openid
     * @return true成功，false失败
     * @throws Exception
     */
    public static boolean OpenIdCheck(String openid) throws Exception {
        String jsonFileStr = FileRead();
        JSONObject jsonFile = JSONObject.fromObject(jsonFileStr);
        if(jsonFile.containsKey("openid")){
            if(jsonFile.getString("openid").equals(openid)){
                System.out.println("验证通过");
                return true;
            }
        }
        return false;
    }
}
