package com.ctis487.newsfeed.util;

import com.ctis487.newsfeed.entity.Article;
import com.ctis487.newsfeed.entity.Source;
import com.ctis487.newsfeed.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static User user;
    public static int page = 0;
    public static String search = "";
    public static String language = "en";
    public static int pageSize = 10;
    public static ArrayList<Article> articles = new ArrayList<>();
    public static ArrayList<Source> sources = new ArrayList<>();
    public static String ARTICLES_API_BASE = "https://newsapi.org/v2/top-headlines?apiKey=7fc409247c5b49208b0d7c4f48c0c82f";
    public static String ARTICLES_API = "https://newsapi.org/v2/top-headlines?apiKey=7fc409247c5b49208b0d7c4f48c0c82f";
    public static String SOURCES_API = "https://newsapi.org/docs/endpoints/sources";

    public static void setUser(User user) {
        Common.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static ArrayList<Article> getArticles() {
        return articles;
    }

    public static void setArticles(ArrayList<Article> articles) {
        Common.articles = articles;
    }

    public static ArrayList<Source> getSources() {
        return sources;
    }

    public static void setSources(ArrayList<Source> sources) {
        Common.sources = sources;
    }

    public static void clearArticles(){
        Common.articles.clear();
    }
}
