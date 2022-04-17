package com.company.service;
// PROJECT NAME shop_bot
// TIME 14:41
// MONTH 04
// DAY 16

import com.company.database.Database;
import com.company.model.Product;

import java.sql.*;
import java.util.Optional;
import java.util.Scanner;

public class ProductService {
    static Scanner SCANNER_NUM = new Scanner(System.in);

    public static void loadProductList() {
        Connection connection = Database.getConnection();
        if (connection != null) {

            try (Statement statement = connection.createStatement()) {

                Database.productList.clear();

                String query = " SELECT * FROM product WHERE NOT deleted; ";

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int categoryId = resultSet.getInt("category_id");
                    double price = resultSet.getDouble("price");
                    String image = resultSet.getString("image");
                    boolean deleted = resultSet.getBoolean("deleted");

                    Product product = new Product(id, categoryId, name, price, image, deleted);

                    Database.productList.add(product);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    public static void addProduct(Product product) {
        Connection connection = Database.getConnection();
        if (connection != null) {

            String query = " INSERT INTO product(name, category_id, price, image)" +
                    " VALUES(?, ?, ?, ?); ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, product.getName());
                preparedStatement.setInt(2, product.getCategoryId());
                preparedStatement.setDouble(3, product.getPrice());
                preparedStatement.setString(4, product.getImage());

                int executeUpdate = preparedStatement.executeUpdate();
                System.out.println(executeUpdate);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        loadProductList();
    }


    public static void deleteProduct(Product product) {
        Connection connection = Database.getConnection();
        if (connection != null) {
            System.out.print("O'chirmoqchi bolgan Mahsulotingizni id sini kiriting : ");
            int id = SCANNER_NUM.nextInt();

            String query = " DELETE FROM todo WHERE id = ? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setInt(1, id);

                int executeUpdate = preparedStatement.executeUpdate();
                System.out.println("executeUpdate = " + executeUpdate);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
