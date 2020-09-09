package connection;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDBOfProduct implements ConnectionDBProduct {

    private static final String SELECT_PRODUCT = "SELECT * FROM product JOIN category on product.category_id = category.category_id;";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product JOIN category on product.category_id = category.category_id WHERE product_id = ? ;";
    private static final String SELECT_CATEGORY = "SELECT * FROM category;";
    private static final String INSERT_PRODUCT = "INSERT INTO product(category_id, product_name, product_price, quantity, color, description) VALUES (?,?,?,?,?,?);";
    private static final String UPDATE_PRODUCT = "UPDATE product SET category_id = ?,product_name = ?,product_price = ?,quantity = ?,color = ?,description = ? WHERE product_id = ?;";
    private static final String DELETE_PRODUCT = "DELETE FROM product where product_id = ?";
    private static final String SELECT_PRODUCT_BY_NAME = "SELECT * FROM product JOIN category on product.category_id = category.category_id WHERE product_name LIKE ?;";

    public ConnectionDBOfProduct() {
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = parseResultSet(resultSet);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();
            product = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                category.setDescription(resultSet.getString("category_description"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public boolean addProduct(Product product) {
        int rowsAffect = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
            setProduct(product , preparedStatement);
            rowsAffect = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }

    @Override
    public boolean editProduct(int id, Product product) {
        int rowsAffect = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
            setProduct(product , preparedStatement);
            preparedStatement.setInt(7, id);
            rowsAffect = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }

    @Override
    public boolean deleteProduct(int id) {
        int rowsAffect = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setInt(1, id);
            rowsAffect = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }


    @Override
    public List<Product> searchProductByName(String name) {
        List<Product> productList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_NAME)) {
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcURL = "jdbc:mysql://localhost:3306/thith?useSSL=false";
            String jdbcUsername = "root";
            String jdbcPassword = "123456";
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void setProduct(Product product, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, product.getCategory().getId());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setInt(3, product.getPrice());
        preparedStatement.setInt(4, product.getQuantity());
        preparedStatement.setString(5, product.getColor());
        preparedStatement.setString(6, product.getDescription());
    }

    private Product parseResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        Category category = new Category();
        product.setProductId(resultSet.getInt("product_id"));
        product.setName(resultSet.getString("product_name"));
        product.setPrice(resultSet.getInt("product_price"));
        product.setQuantity(resultSet.getInt("quantity"));
        product.setColor(resultSet.getString("color"));
        product.setDescription(resultSet.getString("description"));
        category.setId(resultSet.getInt("category_id"));
        category.setName(resultSet.getString("category_name"));
        category.setDescription(resultSet.getString("category_description"));
        product.setCategory(category);
        return product;
    }
}
