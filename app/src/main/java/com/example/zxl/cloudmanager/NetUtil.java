package com.example.zxl.cloudmanager;

import android.util.Log;

import com.example.zxl.cloudmanager.model.Project;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/28.
 */

public class NetUtil {
    private final static String IP1 = "http://localhost/yunmgr_v1.0/api/uc.php?app=";
    private final static String ENDPOINT = IP1;
    private final static String MANAGE_PM = "manage_pm&";
    private final static String TAG = "NetUtil";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = null;
        ByteArrayOutputStream out = null;
        try{
            conn = (HttpURLConnection) url.openConnection();
            out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[10240];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();

        } finally {
            if(out != null){
                out.close();
            }
            if(conn != null){
                conn.disconnect();
            }
        }
    }

    public String post(String urlString, Project project){
        String rs = "post请求错误";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-type", "application/json");

            OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());
            osw.write(project.toJSON().toString());

            osw.flush();
            osw.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = urlConnection.getInputStream();

            if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[10240];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return new String(out.toByteArray());
        } catch (Exception e) {
            Log.e(TAG, "POST ex: " + e.getLocalizedMessage());
            return rs;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(ENDPOINT+urlSpec));
    }

    public String saveCrime(Project project) {
        String urlString = ENDPOINT + MANAGE_PM + project.get();
        return post(urlString, project);
    }

    public ArrayList<Project> getProjects()  {
        String crimeJSON = null;
        try {
            crimeJSON = getUrl(MANAGE_PM + "get_list");
        } catch (IOException e) {
            Log.e(TAG, "ee: "+e.getLocalizedMessage());
        }
        if(crimeJSON != null) {
            ArrayList<Project> projects = new ArrayList<Project>();
            try {
                JSONArray array = (JSONArray) new JSONTokener(crimeJSON.toString()).nextValue();
                for (int i = 0; i < array.length();i++) {
                    projects.add(new Project(array.getJSONObject(i)));
                }
                Log.e(TAG, "projects: " + projects);
                return projects;
            } catch (Exception e){
                Log.e(TAG, "ee: "+e.getLocalizedMessage());
            }
        }
        return null;
    }
}
