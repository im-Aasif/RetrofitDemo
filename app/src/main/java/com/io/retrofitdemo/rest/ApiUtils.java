package com.io.retrofitdemo.rest;

/**
 * Created by GLaDOS on 9/19/2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static RestService getRestService() {
        return RestClient.getClient(BASE_URL).create(RestService.class);
    }
}
