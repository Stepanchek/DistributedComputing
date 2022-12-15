<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Lab 9</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div style="text-align: center;">
  <h1>Books</h1>
  <h2>
    <a href="${pageContext.request.contextPath}/BookNew">Add new book</a>
    &nbsp;&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/BookList">List all books</a>
    &nbsp;&nbsp;&nbsp;
    <a href="index.jsp">Back</a>
  </h2>
</div>
<div align="center">
  <table border="1" cellpadding="5">
    <caption><h2>List of books</h2></caption>
    <tr>
      <th>ID</th>
      <th>Author ID</th>
      <th>Name</th>
      <th>Price</th>
    </tr>
    <c:forEach var="book" items="${listBook}">
      <tr>
        <td><c:out value="${book.bookID}"/></td>
        <td><c:out value="${book.authorId}"/></td>
        <td><c:out value="${book.name}"/></td>
        <td><c:out value="${book.price}"/></td>
        <td>
          <a href="${pageContext.request.contextPath}/BookEdit?id=<c:out value='${book.bookId}' />">Edit</a>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <a href="${pageContext.request.contextPath}/BookDelete?id=<c:out value='${book.bookId}' />">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>