/**
 * 通联数据机密
 * --------------------------------------------------------------------
 * 通联数据股份公司版权所有 © 2013-2016
 * <p/>
 * 注意：本文所载所有信息均属于通联数据股份公司资产。本文所包含的知识和技术概念均属于
 * 通联数据产权，并可能由中国、美国和其他国家专利或申请中的专利所覆盖，并受商业秘密或
 * 版权法保护。
 * 除非事先获得通联数据股份公司书面许可，严禁传播文中信息或复制本材料。
 * <p/>
 * DataYes CONFIDENTIAL
 * --------------------------------------------------------------------
 * Copyright © 2013-2016 DataYes, All Rights Reserved.
 * <p/>
 * NOTICE: All information contained herein is the property of DataYes
 * Incorporated. The intellectual and technical concepts contained herein are
 * proprietary to DataYes Incorporated, and may be covered by China, U.S. and
 * Other Countries Patents, patents in process, and are protected by trade
 * secret or copyright law.
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from DataYes.
 */
package com.datayes.dyoa.common.imageloader;

import android.content.Context;

import com.datayes.baseapp.tools.DYLog;
import com.datayes.dyoa.utils.DYCookieManager;
import com.nostra13.universalimageloader.core.assist.ContentLengthInputStream;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SecureImageDownloader extends BaseImageDownloader {

    private SSLSocketFactory sf;

    public SecureImageDownloader(Context ctx) {
        super(ctx);
        initSSLSocketFactory();
    }

    private void initSSLSocketFactory() {
        // init sf here
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
                // TODO Auto-generated method stub

            }

        }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            sf = sc.getSocketFactory();
        } catch (Exception e) {
            DYLog.printStackTrace(e);
        }
    }

    @Override
    public InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {

        HttpURLConnection conn = createConnection(imageUri, extra);

        if (DYCookieManager.getInstance().hasCookie())
            conn.setRequestProperty("Cookie", DYCookieManager.getInstance().getCookie());

        if (extra != null) {

            Map<String, String> extraMap = (Map<String, String>) extra;

            if (extraMap != null) {

                for (String key : extraMap.keySet()) {

                    conn.setRequestProperty(key, extraMap.get(key));
                }
            }
        }

        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sf);
            ((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {

//					String responseCookie = conn.getHeaderField("Set-Cookie");// 取到所用的Cookie
//					String sessionIdString = "";
//					if (responseCookie != null) {
//					    sessionIdString = responseCookie.substring(0, responseCookie.indexOf(";"));
//					}

//			         String cookieFromResponse = headers.get("Set-Cookie");

                    return true;
                }
            });
        }

        int redirectCount = 0;
        while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
            conn = createConnection(conn.getHeaderField("Location"), extra);
            redirectCount++;
        }

        InputStream imageStream;
        try {

            imageStream = conn.getInputStream();

        } catch (IOException e) {
            IoUtils.readAndCloseStream(conn.getErrorStream());
            throw e;
        }
        if (imageStream == null) {
            return null;
        }
        return new ContentLengthInputStream(new BufferedInputStream(imageStream, BUFFER_SIZE), conn.getContentLength());
    }
}
