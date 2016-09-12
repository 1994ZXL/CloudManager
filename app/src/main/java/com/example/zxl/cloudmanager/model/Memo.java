package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by zqq on 16-7-6.
 */
public class Memo {
    private String note_id; //备忘录id
    private String mem_id; //员工id
    private String title; //标题
    private String content; //内容
    private int create_time; //创建时间

    public Memo(JSONObject json) throws JSONException{
        if (json.has("note_id"))
            note_id = json.getString("note_id");
        if (json.has("mem_id"))
            mem_id = json.getString("mem_id");
        if (json.has("title"))
            title = json.getString("title");
        if (json.has("content"))
            content = json.getString("content");
        if (json.has("create_time"))
            create_time = json.getInt("create_time");
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
