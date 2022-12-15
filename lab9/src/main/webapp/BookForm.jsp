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
  <c:if test="${book != null}">
  <form action="BookUpdate" method="post">
    </c:if>
    <c:if test="${book == null}">
    <form action="BookInsert" method="post">
      </c:if>
      <table border="1" cellpadding="5">
        <caption>
          <h2>
            <c:if test="${book != null}">
              Edit book
            </c:if>
            <c:if test="${book == null}">
              Add new book
            </c:if>
          </h2>
        </caption>
        <c:if test="${book != null}">
          <input type="hidden" name="id" value="<c:out value='${book.bookId}' />"/>
        </c:if>
        <tr>
          <th>AuthorID:</th>
          <td>
            <input type="text" name="authorId" size="45"
                   value="<c:out value='${book.authorId}' />"
            />
          </td>
        </tr>
        <tr>
          <th>Name:</th>
          <td>
            <input type="text" name="name" size="45"
                   value="<c:out value='${book.name}' />"
            />
          </td>
        </tr>
        <tr>
          <th>Price:</th>
          <td>
            <input type="text" name="price" size="45"
                   value="<c:out value='${book.price}' />"
            />
          </td>
        </tr>
        <tr>
          <td colspan="2" style="text-align: center;">
            <input type="submit" value="Save"/>
          </td>
        </tr>
      </table>
    </form>
</div>
</body>
</html>