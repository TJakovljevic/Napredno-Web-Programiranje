package framework.request;

import java.util.HashMap;

public class Helper {

    public static HashMap<String, String> getParametersFromRoute(String route) {
        String[] splittedRoute = route.split("\\?");

        if(splittedRoute.length == 1) {
            return new HashMap<String, String>();
        }

        return Helper.getParametersFromString(splittedRoute[1]);
    }

    public static HashMap<String, String> getParametersFromString(String parametersString) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        String[] pairs = parametersString.split("&");
        for (String pair:pairs) {
            String[] keyPair = pair.split("=");
            parameters.put(keyPair[0], keyPair[1]);
        }

        return parameters;
    }

    public static HashMap<String, String> parseJsonToMap(String jsonString) {
        HashMap<String, String> jsonMap = new HashMap<>();

        jsonString = jsonString.trim().replaceAll("[{}]", "");


        String[] keyValuePairs = jsonString.split(",");

        for (String pair : keyValuePairs) {

            String[] keyValue = pair.split(":");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim().replace("\"", "");
                jsonMap.put(key, value);
            } else {

                System.out.println("Invalid key-value pair: " + pair);
            }
        }

        return jsonMap;
    }
}
