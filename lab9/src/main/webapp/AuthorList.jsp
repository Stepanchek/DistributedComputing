<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Lab 9</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div style="text-align: center;">
  <h1>Authors</h1>
  <h2>
    <a href="${pageContext.request.contextPath}/AuthorNew">Add new author</a>
    &nbsp;&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/AuthorList">List all authors</a>
    &nbsp;&nbsp;&nbsp;
    <a href="index.jsp">Back</a>
  </h2>
</div>
<div align="center">
  <table border="1" cellpadding="5">
    <caption><h2>List of authors</h2></caption>
    <tr>
      <th>ID</th>
      <th>Name</th>
    </tr>
    <c:forEach var="author" items="${listAuthor}">
      <tr>
        <td><c:out value="${author.authorId}"/></td>
        <td><c:out value="${author.name}"/></td>
        <td>
          <a href="${pageContext.request.contextPath}/AuthorEdit?id=<c:out value='${author.authorId}' />">Edit</a>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <a href="${pageContext.request.contextPath}/AuthorDelete?id=<c:out value='${author.authorId}' />">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>