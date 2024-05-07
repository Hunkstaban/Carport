package app.persistence;

import app.entities.Product;
import app.entities.Type;
import app.entities.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper {


    public static List<Type> loadFilters(ConnectionPool connectionPool) {

        String sql = "SELECT * FROM types";
        List<Type> filters = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int typeID = rs.getInt("type_id");
                String name = rs.getString("name");
                filters.add(new Type(typeID, name));
            }

            return filters;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Product> filterByType(ConnectionPool connectionPool, int filterID) {

        String sql = "SELECT * FROM public.view_all_products WHERE type_id = ?";
        List<Product> filteredList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, filterID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int productID = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String description = rs.getString("description");
                int height = rs.getInt("height");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                int unitID = rs.getInt("unit_id");
                String unitName = rs.getString("unit_name");
                int typeID = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                int price = rs.getInt("price");
                int costPrice = rs.getInt("cost_price");
                int quantity = rs.getInt("quantity");

                Unit unit = new Unit(unitID, unitName);
                Type type = new Type(typeID, typeName);

                filteredList.add(new Product(productID, name, description, height, width, length, unit, type, price, costPrice, quantity));
            }
            return filteredList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Product> loadProducts(ConnectionPool connectionPool) {


        String sql = "SELECT * FROM public.view_all_products";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int productID = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String description = rs.getString("description");
                int height = rs.getInt("height");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                int unitID = rs.getInt("unit_id");
                String unitName = rs.getString("unit_name");
                int typeID = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                int price = rs.getInt("price");
                int costPrice = rs.getInt("cost_price");
                int quantity = rs.getInt("quantity");

                Unit unit = new Unit(unitID, unitName);
                Type type = new Type(typeID, typeName);

                productList.add(new Product(productID, name, description, height, width, length, unit, type, price, costPrice, quantity));

            }
            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateProduct(ConnectionPool connectionPool, int productID) {

        String sql = "UPDATE public.products\n" +
                "\tSET name=?, description=?, height=?, width=?, unit_id=?, type_id=?, price=?, cost_price=?, quantity=?, length=?\n" +
                "\tWHERE product_id = ?;";
    }
}
