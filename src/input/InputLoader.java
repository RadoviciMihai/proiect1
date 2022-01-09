package input;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import data.DataBase;
import org.json.simple.parser.ParseException;

public final class InputLoader {
    private DataBase dataBase;

    InputLoader(final String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(jsonString);
        DataBase dataBase = new DataBase(jsonData);
    }

}
