<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${empty usuario}">
		<h4 class="text-center"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <c:out value="Sin iniciar sesión"/></h4>
	</c:when>
	<c:otherwise>
		<h4 class="text-center"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <c:out value="${usuario.getLogin()}"/></h4>
	</c:otherwise>
</c:choose>