package persistence;

import model.Item;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

// Saver class represents all the items we added in our todolist

public class Saver {

    //EFFECTS: save all items we added in the list, and catch IOExpection if an error occurs. We convert each item
    //         current todolist to jsonobject with its name, status and dueDate, ten put all of them in JSONArray

    public static void save(TodoList todoList) {
        try {
            PrintWriter writer = new PrintWriter(TodoList.PATH,"UTF-8");
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            for (Item i : todoList.getList()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", i.getName());
                jsonObject.put("status", i.getStatus());
                jsonObject.put("dueDate", i.getDueDate());
                jsonObjects.add(jsonObject);
            }
            JSONArray jsonArray = new JSONArray(jsonObjects);
            writer.println(jsonArray.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Encountered IOException while saving todo list.");
        }
    }
}
