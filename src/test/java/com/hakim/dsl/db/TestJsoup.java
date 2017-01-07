package com.hakim.dsl.db;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by hd on 2017/1/4.
 */
public class TestJsoup {


    public static void main(String[] args) {
        File file = new File("C:\\Users\\hd\\Desktop\\test.html");

        try {
            /*Document doc = Jsoup.parse(file,"GBK");*/
            Whitelist whitelist = new Whitelist();
            whitelist.addTags("body","div","script","input");
            String html =
            Jsoup.clean(FileUtils.readFileToString(file,"GBK"),whitelist);
            System.out.println(html);
            /*Element element = doc.getElementById("btn-login");
            System.out.println(element);

            Elements elements =
            doc.select("a:contains(清空)");
            System.out.println(elements);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
