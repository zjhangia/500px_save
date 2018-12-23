package com.dezhen.a500pxsave;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by root on 24/07/17.
 */

public class ContentParser {

    //private static String image_title;


    public static String parser(final String html) {

        if (TextUtils.isEmpty(html)) return null;
        Document doc = Jsoup.parse(html);
        Elements metas = doc.select("meta");
        for (Element meta : metas) {
            if (meta.attr("property").equals("og:image")) {
                return meta.attr("content");
            }
        }

        /*for (Element meta : metas) {
            if (meta.attr("property").equals("og:title")) {
                image_title = meta.attr("content");
            }
        }*/
        return null;
    }

    public static String getTitle(final String html){
        if (TextUtils.isEmpty(html)) return null;
        Document doc = Jsoup.parse(html);
        Elements metas = doc.select("meta");
        for (Element meta : metas) {
            if (meta.attr("property").equals("og:title")) {
                return meta.attr("content");
            }
        }
        return null;
    }
}
