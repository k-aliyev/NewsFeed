package com.ctis487.newsfeed.service;

import com.ctis487.newsfeed.util.Common;

public class ApiService {

    public String nextPage(){
        Common.page += 1;
        if(!Common.search.isEmpty())
            return Common.ARTICLES_API + "&pageSize=" + Common.pageSize + "&page=" + Common.page + "&q=" + Common.search;
        return Common.ARTICLES_API + "&pageSize=" + Common.pageSize + "&page=" + Common.page;

    }

    public void updateLanguageAPI(){
        Common.ARTICLES_API = Common.ARTICLES_API_BASE + "&language=" + Common.language;
    }
}
