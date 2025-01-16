package com.nick.springbootinit.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class SydneyQT {

    public String chat(String prompt, String msg) {
        String url = "http://localhost:8080/chat/stream";

        String filePath = "/Users/nicklee/Downloads/springboot-init-master/src/main/java/com/nick/springbootinit/api/cookies.json", cookies = null;

        try {
            cookies = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("gpt4turbo", false);
        map.put("prompt", prompt);
        map.put("context", msg);
        map.put("cookies", cookies);

        String json = JSONUtil.toJsonStr(map);

        String result = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .body(json)
                .timeout(60000)
                .execute()
                .body();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new StringReader(result))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("event: suggested_responses"))
                    break;
                if (line.trim().startsWith("data")) {
                    for (int i = 7; i < line.length() - 1; i++) {
                        if ((line.charAt(i) == '\\' && line.charAt(i + 1) == 'n') || line.contains("`") || line.contains("json")) {
                            sb.append("\n");
                            break;
                        }
                        if (line.charAt(i) == '\\')
                            continue;
                        sb.append(line.charAt(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String result = "日期,用户数\n1号,10\n2号,20\n3号,30\n";
        sb.append("Analysis goal: ").append("What is the tendency").append("\n").append("My data: ").append(result).append("\n");
    }
}
