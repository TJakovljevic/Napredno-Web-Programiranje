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

        // Remove curly braces and whitespace
        jsonString = jsonString.trim().replaceAll("[{}]", "");

        // Split the string by commas to get key-value pairs
        String[] keyValuePairs = jsonString.split(",");

        for (String pair : keyValuePairs) {
            // Split by colon to separate keys and values
            String[] keyValue = pair.split(":");

            if (keyValue.length == 2) { // Ensure there are exactly two elements
                String key = keyValue[0].trim().replace("\"", ""); // Remove quotes from key
                String value = keyValue[1].trim().replace("\"", ""); // Remove quotes from value
                jsonMap.put(key, value);
            } else {
                // Handle case where the key-value pair is not valid
                System.out.println("Invalid key-value pair: " + pair);
            }
        }

        return jsonMap; // Return the populated map
    }
}
