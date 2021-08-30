package persistence;

import model.Item;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Loader class represents all the todoitems we added and saved before in our todolist

public class Loader {

    // EFFECTS: loads todolist and returns it, catches IOException if an error occurs loading data from file.
    //          If there is a line in files, we load each element in the line with their name, status, and
    //          duedate, then each of them in todolist.

    public static void load(TodoList todoList) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(TodoList.PATH));
            if (lines.size() == 0) {
                return;
            }
            String jsonArrayString = lines.get(0); // there is only 1 line in the file
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                Item.ItemStatus status = jsonObject.getEnum(Item.ItemStatus.class, "status");
                String dueDate = jsonObject.getString("dueDate");
                todoList.addItem(new Item(name, status, dueDate));
            }
        } catch (IOException e) {
            System.out.println("Encountered IOException while loading todo list.");
        }
    }
}
