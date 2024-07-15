package com.bot.RequestManager;
import com.google.gson.Gson;
import java.net.http.HttpResponse;


public class ClassParser {

    static public <T> T fromJson(String url, T object) throws Exception{
        Class<?> clazz = object.getClass();
        HttpResponse<String> response = RequestManager.makeGETRequest(url);
        Gson gson = new Gson();
        return (T) gson.fromJson(response.body(),clazz);
    }
}
