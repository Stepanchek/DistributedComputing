<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
  <c:if test="${author != null}">
  <form action="AuthorUpdate" method="post">
    </c:if>
    <c:if test="${author == null}">
    <form action="AuthorInsert" method="post">
      </c:if>
      <table border="1" cellpadding="5">
        <caption>
          <h2>
            <c:if test="${author != null}">
              Edit author
            </c:if>
            <c:if test="${author == null}">
              Add New author
            </c:if>
          </h2>
        </caption>
        <c:if test="${author != null}">
          <input type="hidden" name="id" value="<c:out value='${author.authorId}' />"/>
        </c:if>
        <tr>
          <th>Name:</th>
          <td>
            <input type="text" name="name" size="45"
                   value="<c:out value='${author.name}' />"
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
