package com.zf.zjtf.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpHandler {
    /**
     * get请求
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) throws Exception {
        HttpClientConnectionManager connManager = init();
        HttpClients.custom().setConnectionManager(connManager);
        //创建自定义的httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
        //CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject json = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            System.out.println("======HttpHandlerResult:" + response.getStatusLine() + "=======");
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("调用API出错，请联系管理员");
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "utf-8");
                json = JSONObject.fromObject(result);
            }
            response.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("API无法访问,请联系管理员");
        }
        return json;
    }

    /**
     * post请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) throws Exception {
        HttpClientConnectionManager connManager = init();
        HttpClients.custom().setConnectionManager(connManager);
        //创建自定义的httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();

        //CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject json = null;
        httpPost.setHeader("Content-Type","application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(outStr, "utf-8"));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println("======HttpHandlerResult:"+response.getStatusLine()+"======");
            if(response.getStatusLine().getStatusCode() !=200){
                System.out.println("调用API出错，请联系管理员");
            }
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            json = JSONObject.fromObject(result);
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("API无法访问,请联系管理员");
        }

        return json;
    }

    /**
     * SSL绕过https证书验证
     * @return
     * @throws Exception
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    public static HttpClientConnectionManager init(){
        try {
            SSLContext sslContext  = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                    // TODO Auto-generated method stub
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslContext, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Registry registry = RegistryBuilder
                    . create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf).build();
            return new PoolingHttpClientConnectionManager(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
