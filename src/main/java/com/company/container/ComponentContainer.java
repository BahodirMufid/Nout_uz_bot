package com.company.container;

import com.company.Noutuz;
import com.company.enums.AdminStatus;
import com.company.model.Product;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class ComponentContainer {

    public static final String BOT_TOKEN = "";
    public static final String BOT_NAME = "";

    public static final String ADMIN_ID = "";

    public static Noutuz MY_TELEGRAM_BOT;

    public static String PATH = "src/main/resources/";


    public static final File CUSTOMERS_FILE = new File(ComponentContainer.PATH+"files/db/customers.json");
    public static final File PRODUCTS_FILE = new File(ComponentContainer.PATH+"files/db/products.json");


    public static Map<String, Product> productMap = new HashMap<>();
    public static Map<String, AdminStatus> productStepMap = new HashMap<>();

}
