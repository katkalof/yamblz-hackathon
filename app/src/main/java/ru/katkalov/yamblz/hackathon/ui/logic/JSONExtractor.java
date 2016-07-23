package ru.katkalov.yamblz.hackathon.ui.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsukmanova on 23.07.16.
 */

public class JSONExtractor {
    InputStream is = null;

    public JSONExtractor(InputStream is) {
        this.is = is;
    }

    public List<String> getWordsFromJSon(String language) {
        JSONObject jsonObject = null;

        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonObject = new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        JSONArray jWords;
        List<String> words = null;
        if (jsonObject.has(language)) {
            try {
                jWords = jsonObject.getJSONArray(language);
                words = new ArrayList<>(jWords.length());
                for (int i = 0; i < jWords.length(); i++) {
                    words.add(i, jWords.getString(i));
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return words;
    }
}
