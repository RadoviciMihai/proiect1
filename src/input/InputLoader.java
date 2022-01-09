package input;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import data.DataBase;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class InputLoader {
    private final DataBase dataBase;

    InputLoader(final String path) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)
                parser.parse(new FileReader(path));
        this.dataBase = new DataBase(jsonData);
    }

    public DataBase getDataBase() {
        return dataBase;
    }
}
