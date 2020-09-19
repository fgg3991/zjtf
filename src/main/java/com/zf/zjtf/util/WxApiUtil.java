package com.zf.zjtf.util;

import com.zf.zjtf.domain.AccessToken;
import com.zf.zjtf.domain.Configuration;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class WxApiUtil {

    private static Configuration configuration;
    @Autowired
    public WxApiUtil(Configuration configuration){
        WxApiUtil.configuration = configuration;
    }
    /*private static String appid ="wxf47554a8ff37b158";//"wxbd4231ef6c0152ad";
    private static String secret ="7625a45b34005f36863458236c381169";//"682fad03f3f6614e52cc108e548f264e";*/

    public static JSONObject createWxConfig() throws Exception {
        String url = configuration.getMainUrl();
        JSONObject jsonObject = new JSONObject();
        String jsapi_ticket = getJsApiTicket();
        String nonceStr = CommonUtil.create_nonce_str();
        String timestamp = CommonUtil.create_timestamp();
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
        String signature = CommonUtil.shal(str);
        jsonObject.put("appid",configuration.getAppid());
        jsonObject.put("jsapi_ticket",jsapi_ticket);
        jsonObject.put("url",url);
        jsonObject.put("nonceStr",nonceStr);
        jsonObject.put("timestamp",timestamp);
        jsonObject.put("signature",signature);
        jsonObject.put("title",configuration.getTitle());
        jsonObject.put("desc",configuration.getDesc());
        return jsonObject;
    }

    /**
     * 获取accessToken
     * @return
     * @throws Exception
     */
    private static AccessToken getAccessToken() throws Exception {
        AccessToken token = new AccessToken();
        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+configuration.getAppid()
                +"&secret="+configuration.getSecret()+"";
        JSONObject json = HttpHandler.doGetStr(url);
        if(json!=null){
            token.setToken(json.getString("access_token"));
            token.setExpiresIn(json.getInt("expires_in"));
            token.setGetTime(new Date().getTime());
        }
        return token;
    }

    /**
     * 将accesstoken保存在本地
     * @param at
     * @throws Exception
     */
    private static void saveAccessToken(AccessToken at) throws Exception {
        FileOutputStream fos = new FileOutputStream(configuration.getLocalAccessTokenPath(),false);// 不允许追加
        String json =JSONObject.fromObject(at).toString();
        System.out.println(json);
        fos.write(json.getBytes());
        fos.close();
    }
    /**
     * 获取本地access_token 过期则重新获取e
     * @return
     * @throws IOException
     */
    public static String getSavedAccessToken() throws Exception {
        File file = new File(configuration.getLocalAccessTokenPath());
        File fileParent = file.getParentFile();
        System.out.println(file.getAbsolutePath());
        AccessToken at = new AccessToken();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }

        if(file.length() == 0){
            at = getAccessToken();
            saveAccessToken(at);
        }else{
            FileInputStream fis = new FileInputStream(file);
            byte[] b= new byte[2048];
            int len = fis.read(b);
            String jsonAccessToken = new String(b,0,len);

            AccessToken accessToken = (AccessToken) JSONObject.toBean(JSONObject.fromObject(jsonAccessToken), AccessToken.class);

            long lastSaveTime = accessToken.getGetTime();
            long nowTime = new Date().getTime();
            long remainTime = nowTime-lastSaveTime;
            System.out.println("距上次获取accessToken已过去 "+remainTime/1000+"秒");
            if(remainTime<(accessToken.getExpiresIn()-200)*1000){
                at = accessToken;
            }else{
                at = getAccessToken();
                saveAccessToken(at);
            }
        }
        return at.getToken();
    }

    /**
     * 获取jsApiTicket
     * @return
     * @throws Exception
     */
    private static String getJsApiTicket() throws Exception {
        String access_token = getSavedAccessToken();
        String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
        JSONObject json = HttpHandler.doGetStr(urlStr);
        return json.getString("ticket");
    }

    /**
     * 获取发送对象的openidList
     * @param name 目标群组名
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static JSONArray getUserListByGroupName(String name,String accessToken) throws Exception {
        JSONArray idArr = null;
        //获取发送对象 name->ID
        String userGroupUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="+accessToken;
        Integer groupId = null;
        JSONObject groupJson = HttpHandler.doGetStr(userGroupUrl);
        JSONArray groupArr = groupJson.getJSONArray("groups");
        for(int i=0;i<groupArr.size();i++){
            JSONObject groupItem = (JSONObject) groupArr.get(i);
            if(groupItem.getString("name").equals(name) && groupItem.getInt("count")>0){
                groupId = groupItem.getInt("id");
            }
        }
        if(groupId !=null){
            String userIdListUrl = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token="+accessToken;
            JSONObject userIdListJson = HttpHandler.doPostStr(userIdListUrl,"{\"tagid\":"+groupId+"}");
            idArr = userIdListJson.getJSONObject("data").getJSONArray("openid");
        }
        return idArr;
    }
}
