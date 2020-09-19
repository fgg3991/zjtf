package com.zf.zjtf.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

public class CommonUtil {
    /**
     * 字符串进行shal加密
     *
     * @param str
     * @return
     */
    public static String shal(String str) throws Exception {
        String hexString="";
        MessageDigest digest;
        digest = MessageDigest.getInstance("SHA-1");
        digest.update(str.getBytes("UTF-8"));
        hexString=byteToHex(digest.digest());
        return hexString;
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 创建时间戳
     * @return
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取session中的openid
     * @param request
     * @return
     */
    public static String getOpenIdfromSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        String openid = (String) session.getAttribute("openid");
        if(openid == null){
            //System.out.println("无法获取用户信息，请确认本次操作是从微信公众号入口进入本站点");
        }
        return openid;
    }

    /**
     * 获取excetion的全部信息
     * @param ex
     * @return
     */
    public static String getExceptionAllinformation(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
