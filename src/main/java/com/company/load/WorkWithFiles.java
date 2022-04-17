package com.company.load;
// PROJECT NAME shop_bot
// TIME 15:07
// MONTH 04
// DAY 12

import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class WorkWithFiles {
    public static void writeToJson(List list, File file){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(list);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }




    public static void readProducts(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = new BufferedReader(new FileReader(ComponentContainer.PRODUCTS_FILE))) {
            Type type = new TypeToken<List<Product>>(){}.getType();

            List<Product> list = gson.fromJson(reader, type);
            Database.productList.clear();
            Database.productList.addAll(list);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
