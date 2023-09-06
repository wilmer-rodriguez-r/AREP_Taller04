package org.example.serverapi.minispark.handlers;

import java.util.HashMap;

public class Request {

    private HashMap<String, String> params = new HashMap<>();
    private String query;
    private String body;
    private String verb;

    public Request(String args) {
        setVerb(args);
        setParams(args);
        setQuery(args);
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
        String query = path.split("\\?")[0];
        if(!params.isEmpty()) {
            for (String param: params.keySet()) {
                query += "?{"+param+"}";
            }
        }
        this.query = query;
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

    public String getVerb() {
        return verb;
    }
}
