package com.main;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;

public class FindWords {
    public static HashMap find(String html) throws IOException{
        File file = new File(html);
        HashMap<String, Integer> words = new HashMap<>();

        //Выборка текста из файла
        String str = Jsoup.parse(file,"UTF-8").text();

        //Отчистка строк от знаков препинания
        String[] parts = str.replaceAll("[^а-яА-Яa-zA-Z ]","").toUpperCase().split("\\s+");

        //Запись информации в HashMap
        Iterator i = Arrays.stream(parts).iterator();
        while (i.hasNext()) {
            String current = (String) i.next();
            if (current != "") {
                if (!words.containsKey(current)) {
                    words.put(current, 1);
                } else {
                    int l = words.get(current) + 1;
                    words.put(current, l);
                }
            }
        }
        return words;
    }
}
