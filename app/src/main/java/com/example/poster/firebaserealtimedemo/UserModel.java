package com.example.poster.firebaserealtimedemo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by POSTER on 25.05.2017.
 */

public class UserModel {
    String firstName, lastName, job, key;
    int age;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String job, int age, String key) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.job = job;
        this.age = age;
        this.key = key;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("job", job);
        result.put("age", age);
        result.put("key", key);
        return result;
    }
}
