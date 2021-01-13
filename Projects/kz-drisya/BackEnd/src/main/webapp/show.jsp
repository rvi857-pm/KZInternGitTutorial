<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<ol>
	
	 <c:forEach items="${accounts}" var="lists">
    <li>
       ${lists.toString()}
       </li>
</c:forEach>
</ol> 
</table>
	</body>
</html>