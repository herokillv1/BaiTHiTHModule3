<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/vendors/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/vendors/flag-icon-css/css/flag-icon.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"><!-- End layout styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../css/main.css">
    <script src="../js/main.js"></script>
    <link rel="stylesheet" href="../css/all.css">
    <script src="../js/all.js"></script>
</head>
<body>

<!--đây là header-->
<header>
    <div class="container-fluid header" id="header" style="border-bottom: solid 4px lightgrey">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-lg-2 col-xl-2 block-image">
            </div>
            <div class="col-xs-12 col-sm-12 col-lg-8 col-xl-8 block-background">
                <h1 class="name-shop">Bài Thi TH</h1>
                <br>
                <div class="container-ef">
                    <div class="text"><span>Nguyễn Trung Hiếu C0520K1</span></div>
                </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-lg-2 col-xl-2 block-login">
            </div>
        </div>
    </div>
</header>
<!--đây là header-->

<div class="row" style="clear: both">
    <div class="col-lg-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <%--    <form action="${pageContext.request.contextPath}/users?action=search" method="post" style="margin: 0 auto">--%>
                <%--        <label for="search-bar">Search</label>--%>
                <%--        <input type="text" id="search-bar" name="country">--%>
                <%--        <input type="submit">--%>
                <%--    </form>--%>
                <p class="card-title" style="margin-bottom: 20px">Danh sách sản phẩm</p>
                <a href="${pageContext.request.contextPath}/home?action=create"
                   class="nav-link" style="margin-bottom: 10px"><i class="icon-plus"></i> Thêm mới</a>
                <form class="search-form d-none d-md-block"
                      action="${pageContext.request.contextPath}/home?action=search" method="post">
                    <input type="search" class="form-control" placeholder="Tìm ở đây" title="Tìm ở đây" name="search-input">
                </form>
                <table class="table table-striped ">
                    <tr>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Màu sắc</th>
                        <th>Mô tả</th>
                        <th>Nhãn hàng</th>
                        <th colspan="2">Thao tác</th>
                    </tr>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td><c:out value="${product.getName()}"/></td>
                            <td><c:out value="${product.getPrice()}"/></td>
                            <td><c:out value="${product.getQuantity()}"/></td>
                            <td><c:out value="${product.getColor()}"/></td>
                            <td><c:out value="${product.getDescription()}"/></td>
                            <td><c:out value="${product.getCategory().getName()}"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/home?action=edit&id=${product.getProductId()}">Sửa</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/home?action=delete&id=${product.getProductId()}">Xoá</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>

<!--đây là footer-->
<footer class="page-footer font-small special-color-dark pt-4">
    <div class="container">
        <ul class="list-unstyled list-inline text-center">
            <li class="list-inline-item">
                <a class="btn-floating btn-fb mx-1" href="https://www.facebook.com/ShopHQCodeGym/">
                    <i class="fab fa-facebook-f"> </i>
                </a>
            </li>
            <li class="list-inline-item">
                <a class="btn-floating btn-gplus mx-1">
                    <i class="fab fa-google-plus-g"> </i>
                </a>
            </li>
            <li class="list-inline-item">
                <a class="btn-floating btn-tw mx-1">
                    <i class="fab fa-tiktok"> </i>
                </a>
            </li>
            <li class="list-inline-item">
                <a class="btn-floating btn-li mx-1">
                    <i class="fab fa-youtube"> </i>
                </a>
            </li>
        </ul>
    </div>
    <div class="footer-copyright text-center py-3">© 2020
        All rights reserved:
        <a href="#">BaiThiTH</a>
    </div>
</footer>
<!--đây là footer-->
<script>
    function confirmDelete() {
        if (confirm("Bạn có chắc chắn muốn xoá ?")) {
            document.location.href = '${pageContext.request.contextPath}/home?action=delete&id=${product.getProductId()}'
        }
    }
</script>
</body>
</html>