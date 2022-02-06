package com.main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HtmlLoad {
    public static void load(String url) throws IOException {

        //настройка подключения
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        //Получение данных и запись в downloadFile.html
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new FileWriter("downloadFile.html"));
            String str;
            while ((str = br.readLine()) != null) {
                bw.write(str);
                bw.newLine();
            }
            br.close();
            bw.close();
            System.out.println("Download success");
        } else {
            System.out.println("Fail connection");
        }
        if (connection != null) {
            connection.disconnect();
        }
    }
}
