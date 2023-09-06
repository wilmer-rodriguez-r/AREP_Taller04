package org.example.serverapi.minispark.handlers;

import org.json.JSONObject;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    private HashMap<String, String> params = new LinkedHashMap<>();
    private String query;
    private String body;
    private String verb;

    public Request(String rawData, String payload) {
        String args = rawData.split("\n")[0];
        System.out.println(args);
        setVerb(args);
        setParams(args);
        setQuery(args);
        setBody(payload);
    }

    private void setVerb(String args) {
        this.verb = args.split(" ")[0];
    }

    private void setParams(String args) {
        String path = args.split(" ")[1];
        String[] params = path.split("\\?");
        if(params.length > 1) {
            String[] paramsList = params[1].split("&");
            for (String param: paramsList) {
                String[] listParams = param.split("=");
                this.params.put(listParams[0], listParams[1]);
            }
        }
    }

    private void setQuery(String args) {
        String path = args.split(" ")[1];
        String query = path.split("\\?")[0] + "?";
        if(!params.isEmpty()) {
            for (String param: params.keySet()) {
                query += "{"+param+"}&";
            }
        }
        this.query = query.replaceAll(".$", "");
    }

    public void setBody(String payload) {
        this.body = payload;
    }

    public String getResource() {
        return query;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public String getParam(String nameParam) {
        return params.get(nameParam);
    }

    public JSONObject getBody() {
        return new JSONObject(body);
    }

    public String getVerb() {
        return verb;
    }
}
