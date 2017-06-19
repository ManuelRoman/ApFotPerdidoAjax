<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="pr04.modelo.beans.*"%>
<%@ page errorPage="gesError.jsp?pagOrigen=verInfo.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Información del juego</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="js/jquery320.js"></script>
<script src="js/funciones.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bootstrap-confirmation.min.js"></script>
<style>
body{
	background-color: #9edee5;
	}
</style>
</head>
<body>
	<div class="container"><br/>
	<div class="row">
			<div class="col-md-6 col-md-offset-3"><%@ include file="cabecera.html"%><br/></div>
	</div>
	<div class="row">
			<div class="col-md-4 col-md-offset-4" id="nomUsuario">
				<%@ include file="nomUsuario.jsp"%>
			</div>
	</div>
	<!-- Contenedor del ranking de jugadores -->
	<div class="row">
	<div class="col-md-6 col-md-offset-3">
	<jsp:useBean id="ranking" scope="request" class="pr04.modelo.beans.Ranking" />
	<c:set var="ranking" value="${modelo}"/>
	<h3 class="text-center">Ranking de las 10 puntuaciones más altas</h3>
	<div class="table-responsive">
	<table class="table table-hover">
	<thead>
  		<tr>
    		<th>Login</th>
    		<th>Puntuación</th>
  		</tr>
  	</thead>
  	<tbody>
        <c:forEach var="posicion" items="${ranking}">
  		<tr>
    		<td><c:out value="${posicion.getLogin()}"/></td>
    		<td><c:out value="${posicion.getPuntos()}"/></td>
    	</tr>
    	</c:forEach>
    <tbody>
	</table>
	</div>
	</div>
	</div>
	<!-- Se comprueba si hay un usuario logueado y si es así se realiza una petición ajax para solicitar su información -->
	<jsp:useBean id="usuario" scope="session" class="pr04.modelo.beans.Usuario" />
	<div class="row">
	<c:if test="${not empty usuario.getLogin()}">
	<script>
		peticionAjax("/PR04/controlador?accion=consultarDatos", "", datosUsuario);
	</script>
	<div class="col-md-6 col-md-offset-3">
		<div class="text-center">
			<p>Número total de fotogramas ofrecidos: <span id="numGlobalFotOfrecidos"></span></p>
			<p>Número total de fotogramas acertados: <span id="numGlobalAciertos"></span></p>
			<p>Porcentaje global de fotogramas acertados: <span id="porAciertosGlobal"></span>%</p>
			<p>Total de puntos en el ranking: <span id="puntos"></span></p>
		</div>
	</div>
	</c:if>
	</div>
	<div class="row">
	<div class="text-center">
	<div class="col-md-6 col-md-offset-3">
	<form action="/PR04/controlador" method="post">
		<input type="hidden" name="accion" value="index">
		<input type="submit" value="Volver al inicio" class="btn btn-primary">
	</form>
	</div>
	</div>
	</div>
	</div>
</body>
</html>