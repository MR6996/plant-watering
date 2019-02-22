package com.randazzo.mario.plantwateringapp.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class Messages {

    public static String fromMessageResponse(String response) {
        Map<String, List<String>> responseMap = new Gson().fromJson(
                response, new TypeToken<Map<String, List<String>>>() {
                }.getType()
        );

        List<String> respongeMessages = responseMap.get("message");
        if (respongeMessages != null && respongeMessages.size() > 0)
            return respongeMessages.get(0);

        return "";
    }

}
