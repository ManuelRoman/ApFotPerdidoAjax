<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pr04.modelo.beans.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="WEB-INF/gesError.jsp?pagOrigen=index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PR04: Fotograma Perdido con AJAX</title>
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
	#divLogin{
	display: none;
	}
	#divRegistro{
	display: none;
	}
	#divMenuJuego{
	display: none;
	}
	#infoTerminar{
	display: none;
	}
</style>
<jsp:useBean id="usuario" scope="session" class="pr04.modelo.beans.Usuario" />
<style>
	<c:if test="${not empty usuario.getLogin()}">
	#divMenuJuego{
	display: inline;
	}
	#general{
	display: none;
	}
	</c:if>
</style>
</head>
<body>
	<div class="container"><br/>
		<div class="row">
			<div class="col-md-6 col-md-offset-3"><%@ include file="WEB-INF/cabecera.html"%><br/></div>
		</div>
		<div class="row">
			<div class="col-md-4 col-md-offset-4" id="nomUsuario">
				<%@ include file="WEB-INF/nomUsuario.jsp"%>
			</div>
		</div>
		<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="text-center" id="general">
				<p>Seleccione una acción:</p>
				<button id="mostrarLogin" class="btn btn-primary">Formulario de logueo</button><br/><br/>
				<button id="mostrarRegistro" class="btn btn-primary">Formulario de Registro</button><br/><br/>
				<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="verRanking">
					<input type="submit" value="Consulta Ranking" class="btn btn-primary">
				</form><br/>
			</div>
		<!--Formulario de logueo-->
			<div id="divLogin">
				<form action="/PR04/controlador" method="post" id="formLogin">
					<div class="form-group">
						<label for="login">Login usuario:</label>
						<input class="form-control" id="login" type="text" name="login" maxlength="12" placeholder="Máximo 12" required="required" pattern="\S*"><br>
					</div>
					<div class="form-group">
						<label for="clave">Clave:</label>
						<input class="form-control" id="clave" type="password" name="clave" maxlength="12" placeholder="Máximo 12" required="required" autocomplete="off" pattern="\S*"><br>
					</div>
					<input type="hidden" name="accion" value="login">
					<div class="text-center">
						<input type="submit" value="Login" class="btn btn-primary">
					</div>
				</form><br/>
				<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="index">
					<div class="text-center">
						<input type="submit" value="Volver al inicio" class="btn btn-primary">
					</div>
				</form>	
			</div>
		<!--Formulario de registro-->
			<div id="divRegistro">
				<form action="/PR04/controlador" method="post" id="formRegistro">
					<div class="form-group">
						<label for="login2">Login usuario:</label>
						<input class="form-control" id="login2" type="text" name="login" maxlength="12" placeholder="Máximo 12" required="required" pattern="\S*">
					</div>
					<div class="form-group">
						<label for="clave">Clave:</label>
						<input class="form-control" id="clave" type="password" name="clave" maxlength="12" placeholder="Máximo 12" required="required" autocomplete="off" pattern="\S*">
					</div>
					<input type="hidden" name="accion" value="registrar">
					<div class="text-center">
						<button type="button" class="btn btn-primary" id="comprobarLogin">Comprobar login</button><br/><br/>
						<input type="submit" value="Registro" class="btn btn-primary">
					</div><br/>
				</form>
				<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="index">
					<div class="text-center">
					<input type="submit" value="Volver al inicio" class="btn btn-primary">
					</div>
				</form>
			</div>
		<!-- Menú del juego -->
			<div id="divMenuJuego" class="text-center">
				<p>Seleccione una opción:</p>
				<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="jugar">
					<input type="submit" value="Concursar" class="btn btn-primary">
				</form><br/>
				<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="verRanking">
					<input type="submit" value="Consultar" class="btn btn-primary">
				</form><br/>
				<form action="/PR04/controlador" method="post" id="formTerminar">
					<input type="hidden" name="accion" value="terminar">
					<input type="submit" value="Terminar" class="btn btn-danger" data-toggle="confirmation" data-title="¿Estás seguro que quieres terminar de jugar?">
				</form>
			</div>
		</div>
		</div>
		<!-- Mensajes al usuario -->
		<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="text-center" id="mensajesErrores">
			</div>
		</div>
		</div>
		<!-- Información al terminar -->
		<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="text-center" id="infoTerminar">
			<c:if test="${not empty usuario.getLogin()}">
				<p>Porcentaje de fotogramas acertados de la sesión:	<c:out value="${datosJuego.getPorAciertosSesion()}" />%</p>
				<p>Total de puntos acumulados: <c:out value="${datosJuego.getPuntosGlobales()}" /></p>
				<p>Posición en el ranking: <c:out value="${datosJuego.getPosicion()}" /></p>
			</c:if>
			</div>
		</div>
		</div>
	</div>
</body>
</html>