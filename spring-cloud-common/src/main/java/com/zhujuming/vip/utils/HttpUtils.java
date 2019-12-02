package com.zhujuming.vip.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtils {

//    public static String get(String url) {
//        try {
//            log.info("get http api : \nurl - {}.", url);
//            long start = System.currentTimeMillis();
//            String result = HttpUtil.get(url);
//            long end = System.currentTimeMillis();
//            log.info("调用时间: {}. get http api success: \nurl - {}, \nresult - {}.", (end - start) + " 毫秒", url, result);
//            return result;
//        } catch (Exception e) {
//            log.warn("get http api error: \nurl - " + url, e);
//            throw new HttpApiException("远程调用接口失败");
//        }
//    }
//
//    public static String get(String url, Map<String, Object> params) {
//        try {
//            log.info("get http api : \nurl - {}, \nparams - {}.", url, params);
//            long start = System.currentTimeMillis();
//            String result = HttpUtil.get(url, params);
//            long end = System.currentTimeMillis();
//            log.info("调用时间: {}. get http api success: \nurl - {}, \nparams - {}, \nresult - {}.", (end - start) + " 毫秒", url, params, result);
//            return result;
//        } catch (Exception e) {
//            log.warn("get http api error: \nurl - " + url + ", \nparams - " + params + ".", e);
//            throw new HttpApiException("远程调用接口失败");
//        }
//    }
//
//    public static String post(String url, String body) {
//        try {
//            log.info("post http api : \nurl - {}, \nbody - {}.", url, body);
//            long start = System.currentTimeMillis();
//            String result = HttpUtil.post(url, body);
//            long end = System.currentTimeMillis();
//            log.info("调用时间: {}. post http api success: \nurl - {}, \nbody - {}, \nresult - {}.", (end - start) + " 毫秒", url, body, result);
//            return result;
//        } catch (Exception e) {
//            log.warn("post http api error: \nurl - " + url + ", \nbody - " + body + ".", e);
//            throw new HttpApiException("远程调用接口失败");
//        }
//    }
//
//    public static String postNoResult(String url, String body) {
//        try {
//            log.info("post http api : \nurl - {}, \nbody - {}.", url, body);
//            long start = System.currentTimeMillis();
//            String result = HttpUtil.post(url, body);
//            long end = System.currentTimeMillis();
//            log.info("调用时间: {}. post http api success: \nurl - {}, \nbody - {}, ", (end - start) + " 毫秒", url, body);
//            return result;
//        } catch (Exception e) {
//            log.warn("post http api error: \nurl - " + url + ", \nbody - " + body + ".", e);
//            throw new HttpApiException("远程调用接口失败");
//        }
//    }
//
//    public static String post(String url, Map<String, Object> params) {
//        try {
//            log.info("post http api : \nurl - {}, \nparams - {}.", url, params);
//            long start = System.currentTimeMillis();
//            String result = HttpUtil.post(url, params);
//            long end = System.currentTimeMillis();
//            log.info("调用时间: {}. post http api success: \nurl - {}, \nparams - {}, \nresult - {}.", (end - start) + " 毫秒", url, params, result);
//            return result;
//        } catch (Exception e) {
//            log.warn("post http api error: \nurl - " + url + ", \nparams - " + params + ".", e);
//            throw new HttpApiException("远程调用接口失败");
//        }
//    }
//
//    public static String form(String url, byte[] byt, String key, String fileName) {
//        try {
//            log.info("post http api form : \nurl - {}, \nkey - {}, \nfileName - {}.", url, key, fileName);
//            long start = System.currentTimeMillis();
//            String result = HttpRequest.post(url)
//                    .form("einvoicefile", byt, fileName)
////                    .timeout(20000)//超时，毫秒
//                    .execute().body();
//            long end = System.currentTimeMillis();
//            log.info("调用时间: {}. post http api success: \nurl - {}, \nkey - {}, \nfileName - {}, \nresult - {}.", (end - start) + " 毫秒", url, key, fileName, result);
//            return result;
//        } catch (Exception e) {
//            log.warn("post http api error: \nurl - " + url + ", \nkey - " + key + ", \nfileName - " + fileName + ".", e);
//            throw new HttpApiException("远程调用接口失败");
//        }
//    }

//    public static <T> T get(String url, Class<T> cla) {
//        return JSON.parseObject(get(url), cla);
//    }
//
//    public static <T> T post(String url, String body, Class<T> cla) {
//        return JSON.parseObject(post(url, body), cla);
//    }
//
//    public static <T> T form(String url, byte[] byt, String key, String fileName, Class<T> cla) {
//       // System.out.println(form(url, byt, key, fileName));
//        return JSON.parseObject(form(url, byt, key, fileName), cla);
//    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        log.info("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            log.info("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.info("getRemoteAddr ip: " + ip);
        }
        log.info("获取客户端ip: " + ip);
        return ip;
    }

    public static String getLinuxLocalIp() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                log.info("服务器ip : " + ip);
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.warn("获取ip地址异常 ip = 127.0.0.1", e);
            return "127.0.0.1";
        }
        return ip;
    }

    public static Map<Object, Object> getHeaders(HttpServletRequest request) {
        Map<Object, Object> headers = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    headers.put(name, value);
                }
            }
        }
        return headers;
    }
}
