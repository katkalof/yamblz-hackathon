package ru.katkalov.yamblz.hackathon.ui.logic;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsukmanova on 23.07.16.
 */

public class YaTranslator extends AsyncTask<String, Void, Map<String, String>> {
    String urlMainText = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    JSONObject jsonResult = null;
    String LOG_TAG = "HACATHON";

    @Override
    protected Map<String, String> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(formURL(params));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (inputStream != null) {
                Log.e(LOG_TAG, "jsonResult: " + buffer.toString());
            }
            jsonResult = new JSONObject(buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "URL is not available");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e(LOG_TAG, "Cannot open URl connection");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return getTranslation();
    }

    private String formURL(String[] params) {
        String newUrl = urlMainText;
        newUrl += "lookup?key=" + params[0];
        newUrl += "&lang=" + params[1];
        newUrl += "&text=" + params[2];
        return newUrl;
    }

    private Map<String, String> getTranslation() {
        if (jsonResult == null) return null;
        Map<String, String> result = new HashMap<>();
        if (jsonResult.has("def")) {
            try {
                JSONObject definition = jsonResult.getJSONArray("def").getJSONObject(0);
                JSONArray translations = definition.getJSONArray("tr");
                String translatedWord = translations.getJSONObject(0).getString("text");
                result.put("translated", translatedWord);
            } catch (JSONException ex) {

            }
        }
        return result;
    }
}
