<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="pr04.modelo.beans.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Página de gestión de Errores</title>
<link rel="stylesheet" href="css/bootstrap.css">
<style>
body {
	background-color: #9edee5;
}
</style>
</head>
<body>
	<div class="container"><br />
	<div class="row">
			<div class="col-md-6 col-md-offset-3"><%@ include file="cabecera.html"%><br/></div>
		</div>
	<div class="row">
		<div class="text-center" class="col-md-6 col-md-offset-3">
			<c:choose>
				<c:when test="${not empty error}">
					<p>
						<c:out value="${error.toString()}" default="Error" />
					</p>
				</c:when>
				<c:otherwise>
					<p>
						Página del error:
						<c:out value="${param.pagOrigen}" default="Error" />
					</p>
					<p>
						Error:
						<%=exception.toString()%></p>
				</c:otherwise>
			</c:choose>
			<form action="/PR04/controlador" method="post">
				<input type="hidden" name="accion" value="index"> <input
					type="submit" value="Volver al inicio" class="btn btn-primary">
			</form>
		</div>
	</div>
	</div>
</body>
</html>