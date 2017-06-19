<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pr04.modelo.beans.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="gesError.jsp?pagOrigen=juego.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Juego</title>
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
img {
    width:100%;
    max-width:600px;
	}
#sinFotogramas{
	display: none;
	}
#muestraRespuesta{
	display: none;
	}
#infoTerminar{
	display: none;
	}
#nuevaSesion{
	display: none;
	}
</style>
<script>
// Se realiza una petición ajax para mostrar el primer fotograma automáticamente al llegar a esta página, después
// de que el jugador haya pulsado en concursar.
$(document).ready(function() {
    peticionAjax("/PR04/controlador?accion=nuevoFotograma", "", llegadaFotograma2);
});
</script>
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
		<!-- Contenedor del fotograma y las opciones de respuesta-->
		<div class="row">
			<div class="text-center" class="col-md-6 col-md-offset-3" id="muestraFotograma">
				<c:if test="${empty sinFotogramas}">
					<img alt="fotograma" src="" class="img-rounded" class="img-responsive" id="imagenFotograma">
					<form action="/PR04/controlador" method="post" id="formRespuesta">
						<input type="hidden" name="accion" value="responderFotograma">
						<h4>Seleccione una respuesta:</h4>
						<div class="radio">
						<label>
    						<input type="radio" name="opcionSeleccionada" id="opcion1valor" value="" checked><span id="opcion1"></span>
  						</label>
						</div>
						<div class="radio">
						<label>
    						<input type="radio" name="opcionSeleccionada" id="opcion2valor" value=""><span id="opcion2"></span>
  						</label>
						</div>
						<div class="radio">
						<label>
    						<input type="radio" name="opcionSeleccionada" id="opcion3valor" value=""><span id="opcion3"></span>
  						</label>
						</div>
						<input type="submit" value="Responder" class="btn btn-primary">
					</form><br/>
					<form action="/PR04/controlador" method="post" id="formTerminar2">
						<input type="hidden" name="accion" value="terminar">
						<input type="submit" value="Terminar" class="btn btn-danger" data-toggle="confirmation" data-title="¿Estás seguro que quieres terminar de jugar?">
					</form>
    			</c:if>
			</div>
		</div>
		<!-- Contenedor que muestra si el usuario ha acertado o fallado el fotograma -->
		<div class="row">
			<div class="text-center" class="col-md-6 col-md-offset-3" id="muestraRespuesta">
				<div class="row"><div class="col-md-2 col-md-offset-5" id="responde"></div></div>
				<div class="row">
				<p>Seleccione una opción:</p>
				<form action="/PR04/controlador" method="post" id="nuevoFotograma">
					<input type="hidden" name="accion" value="nuevoFotograma">
					<input type="submit" value="Nuevo fotograma" class="btn btn-primary">
				</form><br/>
				<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="verRanking">
					<input type="submit" value="Consultar" class="btn btn-primary">
				</form><br/>
				<form action="/PR04/controlador" method="post" id="formTerminar3">
					<input type="hidden" name="accion" value="terminar">
					<input type="submit" value="Terminar" class="btn btn-danger" data-toggle="confirmation" data-title="¿Estás seguro que quieres terminar de jugar?">
				</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3" id="barraProgreso"><br/>
			<div class="progress">
				<div id="porcentajeBarra" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: <c:out value='${listaIdFotSesion.size()}'/>0%;"></div>
			</div>
			</div>
		</div>
		<!-- Avisa cuando no hay fotogramas en la sesión o en la base de datos-->
		<div class="row">
			<div class="text-center" class="col-md-6 col-md-offset-4" id="sinFotogramas">
				<div class="row"><div class="col-md-4 col-md-offset-4" id="mensaje"></div></div>
				<div class="row">
       	 		<form action="/PR04/controlador" method="post">
					<input type="hidden" name="accion" value="verRanking">
					<input type="submit" value="Consultar" class="btn btn-primary">
				</form><br/>
				<form action="/PR04/controlador" method="post" id="formTerminar4">
					<input type="hidden" name="accion" value="terminar">
					<input type="submit" value="Terminar" class="btn btn-danger" data-toggle="confirmation" data-title="¿Estás seguro que quieres terminar de jugar?">
				</form><br/>
				</div>
			</div>
		</div>
		<!-- Nueva sesión -->
		<div class="row">
			<div class="text-center" class="col-md-6 col-md-offset-4" id="nuevaSesion">
				<form action="/PR04/controlador" method="post" id="formNuevaSesion">
					<input type="hidden" name="accion" value="nuevaSesion">
					<input type="submit" value="Nueva sesión" class="btn btn-primary">
				</form>
			</div>
		</div>
		<!-- Información al terminar -->
		<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="text-center" id="infoTerminar">
				<p>Porcentaje de fotogramas acertados de la sesión:	<span id="porcentaje"><c:out value="${datosJuego.getPorAciertosSesion()}" /></span>%</p>
				<p>Total de puntos acumulados: <span id="puntosGlobales"><c:out value="${usuario.getRanking().getPuntos()}" /></span></p>
				<p>Posición en el ranking: <span id="posicion"><c:out value="${datosJuego.getPosicion()}" /></span></p>
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