package com.main;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);
    private static Connection connection = null;

    //Подключение БД
    public static void connectDB() throws ClassNotFoundException, SQLException {
        String urldb = "jdbc:postgresql://localhost:5432/statistic";
        String name = "postgres";
        String psw = "1234";
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(urldb, name, psw);
    }

    //Отчистка таблицы word_statistic
    public static void deleteFromTable() throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        String sql = "DELETE FROM word_statistic";
        statement.executeUpdate(sql);
    }

    //Загрузка статистики в БД
    public static void insertToTable(int j, String word, int count) throws SQLException {
        String sql = "INSERT INTO word_statistic (id, word, count) VALUES (?,?,?)";
        PreparedStatement statement1 = connection.prepareStatement(sql);
        statement1.setInt(1, j);
        statement1.setString(2, word);
        statement1.setInt(3, count);
        statement1.executeUpdate();
    }
    public static void main(String args[]) {

        try {
            HashMap<String, Integer> words = new HashMap<>();
            Scanner in = new Scanner(System.in);
            System.out.println("Enter URL");
            String url = in.nextLine();

            //Скачивание html
            HtmlLoad htmlLoad = new HtmlLoad();
            htmlLoad.load(url);
            words = FindWords.find("downloadFile.html");
            connectDB();
            deleteFromTable();

            //Вывод статистики в консоль и загрузка в БД
            int j = 0;
            Set set = words.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                j++;
                Map.Entry me = (Map.Entry) i.next();
                String word = me.getKey().toString();
                int count = (Integer) me.getValue();
                insertToTable(j,word,count);
                System.out.print(me.getKey() + " - ");
                System.out.println(me.getValue());
            }
        } catch (IOException exp) {
            log.error("Incorrect URL", exp);
            System.out.println("Incorrect URL");
        }
        catch (SQLException | ClassNotFoundException exp){
            log.error("Error with DB",exp);
            System.out.println("Error with DB");
        }
    }
}
