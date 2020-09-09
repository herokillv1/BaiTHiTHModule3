package service;

import connection.ConnectionDBOfProduct;
import model.Category;
import model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Duc on 5/26/2020
 * @project test-module3-
 **/

@WebServlet(name = "ServletHome", urlPatterns = "/home")
public class HomeRunServlet extends HttpServlet {
    private ConnectionDBOfProduct connectionDBOfProduct = new ConnectionDBOfProduct();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        String action = request.getParameter("action");
        List<Product> productList = connectionDBOfProduct.getAllProduct();


        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                product = parseProduct(request);
                boolean result = connectionDBOfProduct.addProduct(product);
                System.out.println(result);
                showProductList(request, response, productList);
                break;
            case "edit":
                product = parseProduct(request);
                result = connectionDBOfProduct.editProduct(product.getProductId(), product);
                System.out.println(result);
                showProductList(request, response, productList);
                break;
            case "search":
                String input = request.getParameter("search-input");
                productList = connectionDBOfProduct.searchProductByName(input);
                showProductList(request, response, productList);
                break;
            case "done":
                showProductList(request, response, productList);
                break;
            default:
                showProductList(request, response, productList);
        }
    }

    private Product parseProduct(HttpServletRequest request) {
        Product product;
        product = new Product();
        product.setProductId(Integer.parseInt(request.getParameter("product-id")));
        product.setName(request.getParameter("product-name"));
        product.setPrice(Integer.parseInt(request.getParameter("product-price")));
        product.setQuantity(Integer.parseInt(request.getParameter("product-quantity")));
        product.setColor(request.getParameter("product-color"));
        product.setDescription(request.getParameter("product-description"));
        Category category = new Category();
        category.setId(Integer.parseInt(request.getParameter("category-id")));
        product.setCategory(category);
        return product;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                create(request,response);
                product = new Product();
                showForm(request, response, product, action);
                break;
            case "edit":
                int productId = Integer.parseInt(request.getParameter("id"));
                product = connectionDBOfProduct.getProductById(productId);
                showForm(request, response, product, action);
                break;
            case "delete":
                productId = Integer.parseInt(request.getParameter("id"));
                System.out.println(connectionDBOfProduct.deleteProduct(productId));
                List<Product> productList = connectionDBOfProduct.getAllProduct();
                showProductList(request, response, productList);
                break;
            default:
                productList = connectionDBOfProduct.getAllProduct();
                showProductList(request, response, productList);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) {

    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Product product, String action) {
        List<Category> categoryList = connectionDBOfProduct.getCategoryList();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("product", product);
        request.setAttribute("action", action);
        try {
            request.getRequestDispatcher("views/form.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response, List<Product> productList) {
        request.setAttribute("productList", productList);
        try {
            request.getRequestDispatcher("views/index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
