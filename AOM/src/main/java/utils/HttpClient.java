package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * @Description 发送http请求帮助类
 * @author:liuyc
 * @time:2016年5月17日 下午3:25:32
 */
public class HttpClient {
    /**
     * @param urlParam url地址
     * @param params 请求内容
     * @param charset 字符集 如UTF-8
     * @param headers 请求头
     * @return
     * @throws IOException
     */
    public static String sendPost(String urlParam, String params, String charset, Map<String, String> headers) throws IOException {
        StringBuilder resultBuffer = new StringBuilder();
        HttpURLConnection con = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        // 发送请求
        try {
            URL url = new URL(urlParam);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            for (Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
            if (params != null && params.length() > 0) {
                osw = new OutputStreamWriter(con.getOutputStream(), charset);
                osw.write(params);
                osw.flush();
            }
            // 读取返回内容
            // TODO: 4/22/2021 AomClinet#resetUser密码重复时 response有msg没有读出来
            System.out.println("ResponseCode: " + con.getResponseCode() + "\t" + con.getResponseMessage());
            int len = con.getContentLength();
            InputStream inputStream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String responsedata;
            while ((responsedata = reader.readLine()) != null) {
                resultBuffer.append(responsedata);
            }
            for (int i = 0; i < len; i++) {
                System.out.println(con.getHeaderField(i));
            }
            int contentLength = con.getContentLength();
            if (contentLength == -1 && con.getErrorStream() != null) {
                System.out.println("登录报错,ErrorStream打印如下:");
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), charset));
                String temp;
                while ((temp = br.readLine()) != null) {
                    resultBuffer.append(temp);
                }
            }
            if (contentLength > 0) {
                System.out.println("返回页面信息html如下:");
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
                String temp;
                while ((temp = br.readLine()) != null) {
                    resultBuffer.append(temp);
                }
            }
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
            if (br != null) {
                try {
                    br.close();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }

        return resultBuffer.toString();
    }


    /**
     * @Description 发送get请求保存下载文件
     * @author:liuyc
     * @time:2016年5月17日 下午3:27:29
     */
    public static void sendGetAndSaveFile(String urlParam, Map<String, Object> params, String fileSavePath) throws IOException {
        // 构建请求参数
        StringBuilder sbParams = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
//        BufferedReader br = null;
        FileOutputStream os = null;
        try {
            URL url;
            if (sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            InputStream is = con.getInputStream();
            os = new FileOutputStream(fileSavePath);
            byte[] buf = new byte[1024];
            int count;
            while ((count = is.read(buf)) != -1) {
                os.write(buf, 0, count);
            }
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } finally {
                    con.disconnect();
                }
            }
        }
    }

    /**
     * @Description 使用HttpURLConnection发送get请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:27:29
     */
    public static String sendGet(String urlParam, Map<String, Object> params, String charset) throws IOException {
        StringBuffer resultBuffer;
        // 构建请求参数
        StringBuilder sbParams = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
        BufferedReader br = null;
        try {
            URL url;
            if (sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } finally {
                    con.disconnect();
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description 使用URLConnection发送get请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:27:58
     */
    public static String sendGet2(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer;
        // 构建请求参数
        StringBuilder sbParams = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        BufferedReader br = null;
        try {
            URL url;
            if (sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            URLConnection con = url.openConnection();
            // 设置请求属性
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立连接
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description 使用HttpClient发送post请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:28:23
     */
    public static String httpClientPost(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = new StringBuffer();
        HttpPost httpPost = new HttpPost(urlParam);
        CloseableHttpClient client = HttpClients.createDefault();
        // 构建请求参数
        List<NameValuePair> list = new ArrayList<>();
        for (Entry<String, Object> elem : params.entrySet()) {
            list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
        }
        BufferedReader br = null;
        try {
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = client.execute(httpPost);
            // 读取服务器响应数据
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description 使用HttpClient发送get请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:28:56
     */
    public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer;
        CloseableHttpClient client = HttpClients.createDefault();
        BufferedReader br = null;
        // 构建请求参数
        StringBuilder sbParams = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                try {
                    sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                sbParams.append("&");
            }
        }
        if (sbParams.length() > 0) {
            urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
        }
        HttpGet httpGet = new HttpGet(urlParam);
        try {
            HttpResponse response = client.execute(httpGet);
            // 读取服务器响应数据
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            resultBuffer = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description 使用socket发送post请求
     * @author:liuyc
     * @time 2016年5月18日 上午9:26:22
     */
    public static String sendSocketPost(String urlParam, Map<String, Object> params, String charset) throws IOException {
        String result;
        // 构建请求参数
        StringBuilder sbParams = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        Socket socket = null;
        OutputStreamWriter osw = null;
        InputStream is = null;
        try {
            URL url = new URL(urlParam);
            String host = url.getHost();
            int port = url.getPort();
            if (-1 == port) {
                port = 80;
            }
            String path = url.getPath();
            socket = new Socket(host, port);
            StringBuilder sb = new StringBuilder();
            sb.append("POST ").append(path).append(" HTTP/1.1\r\n");
            sb.append("Host: ").append(host).append("\r\n");
            sb.append("Connection: Keep-Alive\r\n");
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
            // 这里一个回车换行，表示消息头写完，不然服务器会继续等待
            sb.append("\r\n");
            if (sbParams.length() > 0) {
                sb.append(sbParams.substring(0, sbParams.length() - 1));
            }
            osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(sb.toString());
            osw.flush();
            is = socket.getInputStream();
            String line;
            // 服务器响应体数据长度
            int contentLength = 0;
            // 读取http响应头部信息
            do {
                line = readLine(is, 0, charset);
                if (line.startsWith("Content-Length")) {
                    // 拿到响应体内容长度
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                // 如果遇到了一个单独的回车换行，则表示请求头结束
            } while (!"\r\n".equals(line));
            // 读取出响应体数据（就是你要的数据）
            result = readLine(is, contentLength, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } finally {
                    socket.close();

                }
            }
            if (is != null) {
                try {
                    is.close();
                } finally {
                    socket.close();
                }
            }
        }
        return result;
    }

    /**
     * @Description 使用socket发送get请求
     * @author:liuyc
     * @time 2016年5月18日 上午9:27:18
     */
    public static String sendSocketGet(String urlParam, Map<String, Object> params, String charset) throws IOException {
        String result;
        // 构建请求参数
        StringBuilder sbParams = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        Socket socket = null;
        OutputStreamWriter osw = null;
        InputStream is = null;
        try {
            URL url = new URL(urlParam);
            String host = url.getHost();
            int port = url.getPort();
            if (-1 == port) {
                port = 80;
            }
            String path = url.getPath();
            socket = new Socket(host, port);
            StringBuilder sb = new StringBuilder();
            sb.append("GET ").append(path).append(" HTTP/1.1\r\n");
            sb.append("Host: ").append(host).append("\r\n");
            sb.append("Connection: Keep-Alive\r\n");
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
            // 这里一个回车换行，表示消息头写完，不然服务器会继续等待
            sb.append("\r\n");
            if (sbParams.length() > 0) {
                sb.append(sbParams.substring(0, sbParams.length() - 1));
            }
            osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(sb.toString());
            osw.flush();
            is = socket.getInputStream();
            String line;
            // 服务器响应体数据长度
            int contentLength = 0;
            // 读取http响应头部信息
            do {
                line = readLine(is, 0, charset);
                if (line.startsWith("Content-Length")) {
                    // 拿到响应体内容长度
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                // 如果遇到了一个单独的回车换行，则表示请求头结束
            } while (!"\r\n".equals(line));
            // 读取出响应体数据（就是你要的数据）
            result = readLine(is, contentLength, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } finally {
                    socket.close();

                }
            }
            if (is != null) {
                try {
                    is.close();
                } finally {
                    socket.close();
                }
            }
        }
        return result;
    }

    /**
     * @Description 读取一行数据，contentLe内容长度为0时，读取响应头信息，不为0时读正文
     * @time:2016年5月17日 下午6:11:07
     */
    private static String readLine(InputStream is, int contentLength, String charset) throws IOException {
        List<Byte> lineByte = new ArrayList<>();
        byte tempByte;
        int cumsum = 0;
        do {
            tempByte = (byte) is.read();
            lineByte.add(tempByte);
            cumsum++;
//        cumsum等于contentLength表示已读完
        } while (cumsum < contentLength);
        if (contentLength != 0) {
        } else {
            do {
                tempByte = (byte) is.read();
                lineByte.add(tempByte);
            } while (tempByte != 10);
            // 换行符的ascii码值为10
        }

        byte[] resutlBytes = new byte[lineByte.size()];
        for (int i = 0; i < lineByte.size(); i++) {
            resutlBytes[i] = lineByte.get(i);
        }
        return new String(resutlBytes, charset);
    }
}