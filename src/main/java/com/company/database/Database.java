package com.company.database;

import com.company.model.Brand;
import com.company.model.Category;
import com.company.model.Customer;
import com.company.model.Product;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Database {
    List<Customer> customerList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    List<Brand> brandList = new ArrayList<>();

    static Connection getConnection() {
        Connection con = null;
        try {
            // Class.forName("org.postgresql.Driver");  // 1

            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/nout_uz",
                            "postgres", "Bahodir2011"); //2

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;

    }

//    static void loadData() {
//
//        Brand brand1 = new Brand("ASUS");
//        Brand brand2 = new Brand("ASER");
//        Brand brand3 = new Brand("LENOVO");
//        Brand brand4 = new Brand("MSI");
//        Brand brand5 = new Brand("DELL");
//        Brand brand6 = new Brand("HP");
//        Brand brand7 = new Brand("MICROSOFT OFICE");
//        Brand brand8 = new Brand("RAZER");
//        Brand brand9 = new Brand("SAMSUNG");
//        brandList.add(brand1);
//        brandList.add(brand2);
//        brandList.add(brand3);
//        brandList.add(brand4);
//        brandList.add(brand5);
//        brandList.add(brand6);
//        brandList.add(brand7);
//        brandList.add(brand8);
//        brandList.add(brand9);
//
//
//        Product product1 = new Product(brand1.getId(), "Asus Rog Strix", 1600d, "images.rog_image.png");
//        Product product2 = new Product(brand2.getId(), "Aser Aspire 3", 600d, "images.aser_aspire.png");
//        Product product3 = new Product(brand3.getId(), "Legion 5 pro", 1900d, "images.lenovo.png");
//        Product product4 = new Product(brand4.getId(), "Msi Katana", 1300d, "images.msi_katana1.png");
//        Product product5 = new Product(brand5.getId(), "Dell g5", 1400d, "images.dell_g5.png");
//        productList.add(product1);
//        productList.add(product2);
//        productList.add(product3);
//        productList.add(product4);
//        productList.add(product5);
//
//    }
}
