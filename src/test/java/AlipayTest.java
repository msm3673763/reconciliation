/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/14

 * Contributors:
 *      - initial implementation
 */

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 暂无描述
 *
 * @author ucs_masiming
 * @since 2017/9/14
 */
public class AlipayTest {

    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String APP_ID = "2016081600257930";
    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCKMYecdil0VJgLlHesYfx5dXYQNtfY7SYvhAyMLMW2s7k3ZB5p2pvG+n0hh+B6CWYUItxe2RQA4UH9wtMRcFiAn0lhpD5cR2Hk546H/eJ5izchvXIH9hSo5sC7wst8GlK0SK9IpP90y5cQq+AJIMHg8hI/AwZy7xFTzbgxA+NP6p33emF9YwYpNxtPqKbcZPAGYvJN1nkltR9hxSVurNdKUqOs3QbhDH+2JkOUYGvkM7CU20KmTRHqRCXd+hK1xgVhUzs8VZKHlcd2KQgbPRsIc+6pakFrjMMb/kCM03jJIfcQbCGsWph3P3PNUpJvCAJH6aHb4fp9lu04I7ZewjmnAgMBAAECggEAWjfFocaef5yuenjdvANoUV72ydUUC0GfOUwK6QRdu04Pr/LDzDV0l212fYp4aRNzudecaKttXLmyXFlxdCQVEztqK72PHK1ZYpgtO+jqjpDtWDCWnk3v5OhokOu17no83gfJrzJQt84l/DNJGoxxJkrC0LJH5obemAhopE82ejfpNIU9g0QG1QqVjqVUwelmTXhgb45Y2mvDgiwn96hZiK4wCLT1LQpEZpMnnCMBm5fvVQNGOPQqw4GOJqJc/PMajTJiWrgJ33ZxfwxXJgBp+DY6hgGkB8bS7/yKMazKfJufXCLl6Ft5YYsUHhBg0t8u6I7OX5VqrihMFNjUbDSrYQKBgQDQT8zojpG+/zD1gOs2jLVSdeg5gBTRBPBcASB61fOnIQvTgxcPNeHXKtD8ABldEAqqxN6p+dUeqkhIRiUxOmVcgRU0ooqIH5uaicVw513urYbL091mSp4DIAjiYdrL//twwOTikPxYP0jIJC7rG1M9zPpI1yx0D3vNHeHt+8GQNwKBgQCp1Gs626ZAL+Wwq0hy4fekdsne9dcxzruAVXZWBzqgQRF8GNKG/sCNUJKgWOaKiDWqhJkgeMH0Y4Rzapsc/zu7qwOpDLIzMrFxAvisB2A0oGFoPYLKqnGxRzPye7Sf7M4Q8Q4Fv5q4gL4x+a9WPo4DqRk/+0M16HFbV+kW0SaKEQKBgQCM10d9fNdBwZxCgPWHHj+CD0IBjn2EjMhODFz/cCSavRcX3Kv8lrupFMPTfeWoLC5qI01Asy/K2eqhIvwGeqsJjiTeP39WZbh5FekuWuABfcWDji1E+fI3vrMDDJI4F0zTgTe/j7NtnuwAlaY7Q3BWen5GPWVK2yUjG68jjnHipQKBgCwETE6TA4H7aBLkMgDkpGTSDPxmx4G3TU2z2NnvQbirZLa/UEGA4bM+wOIiaI5bQ12QxIdwBQUsjfXEGbPoSzkW1/oGumEesHLUDFO6DatWLijwWGfiyVjjVYlxmp0gqUAm2jWPNbDAXYtiZZl62SAFwe+dVgww1MCSBm3Ng6mhAoGADZlqFAdK4KRX5rO/hqqFermCwSrYX5EtPirHXBSGN691QhVkI8HzOg1r0GWWpbnjSie4uia4pwG09T7u7R6XSa9QdWi9RvzWrkcCaEhljAQUl31chpyEt2JR9J3OYcLzxmGEHneN8AFh5C/0320ftKSKfXa7MXK2JrDcbSvVOsk=";
    private static final String FORMAT = "json";
    private static final java.lang.String CHARSET = "UTF-8";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1Ae3PqjghNnIimKsEJ/G+VDl8tV1oYueHq0NMu9wUvmh7BqV6qlk3OdjTJtAbCryc4EUEnpeJtnNZu77S/GPnetutUpDDI/M94yVDLl3xKS+Q7XD5b5b5P/Sg4uShThzFxVvX4R4gpPEYC47uBhduGGkpORbIiu45Oro4YAP3hnag9UoMGt9S9n9bLxLmgs6SsE184p/mu2axPeGlHjwLZXOip4+G1u9uXEb1nf4DqaGe7S561zJXdu2E20bj38aRwwEvBq/2dMkvQcLLVTeyvRMHDanxFHm3hBukIxdB9uDp/7HiksPRPbVjEDoSS2Ljbbj6ewkVMoClSW0dVBkBwIDAQAB";
    private static final String SIGN_TYPE = "RSA2";

    @Test
    public void testQueryDownloadUrl() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY,
                FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE); //获得初始化的AlipayClient
        AlipayDataDataserviceBillDownloadurlQueryRequest request
                = new AlipayDataDataserviceBillDownloadurlQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                        "    \"bill_type\":\"trade\"," +
                        "    \"bill_date\":\"2017-09-12\"   }");//设置业务参数
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        System.out.print(response.getBody());
//根据response中的结果继续业务逻辑处理
    }

    @Test
    public void testDownloadBillFile() {
        //将接口返回的对账单下载地址传入urlStr
        String urlStr = "http://dwbillcenter.alipaydev.com/downloadBillFile.resource?bizType=trade&userId=20881021714158790156&fileType=csv.zip&bizDates=20170912&downloadFileName=20881021714158790156_20170912.csv.zip&fileId=%2Ftrade%2F20881021714158790156%2F20170912.csv.zip&timestamp=1505356820&token=7e22bc6e86adc1bf7bc2c30fac95c2b1";
        //指定希望保存的文件路径
        String filePath = "D:/20881021714158790156.zip";
        URL url;
        HttpURLConnection httpUrlConnection = null;
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            url = new URL(urlStr);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.connect();
            fis = httpUrlConnection.getInputStream();
            byte[] temp = new byte[1024];
            int b;
            fos = new FileOutputStream(filePath);
            while ((b = fis.read(temp)) != -1) {
                fos.write(temp, 0, b);
                fos.flush();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis!=null) fis.close();
                if(fos!=null) fos.close();
                if(httpUrlConnection!=null) httpUrlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
