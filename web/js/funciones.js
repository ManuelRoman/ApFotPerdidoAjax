var x;
x=$(document);
x.ready(inicializarEventos);
function inicializarEventos() {
	var x;
	//Muestra el div que contiene el logín
	$("#mostrarLogin").click(function() {
		mostrarOcultarElemento("#divLogin");
	});
	//Muestra el div que contiene el registro
	$("#mostrarRegistro").click(function() {
		mostrarOcultarElemento("#divRegistro");
	});
	//Ventana de confirmación de Bootstrap
	$('[data-toggle=confirmation]').confirmation({
  	  rootSelector: '[data-toggle=confirmation]',
	});
	//Hace la petición ajax de loguearse
	$('#formLogin').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), login);
	});
	//Hace la petición ajax de finalizar el juego, hay varias diferentes, ya que hay varios formularios para terminar
	//ubicados en sitios diferentes y ocultan o muestran otros elementos
	$('#formTerminar').submit(function(event){
		event.preventDefault();
		mostrarOcultarElemento("#divMenuJuego");
		$("#infoTerminar").toggle();
		peticionAjax($(this).attr('action'), $(this).serialize(), terminar);
	});
	//Hace la petición ajax de finalizar el juego
	$('#formTerminar2').submit(function(event){
		event.preventDefault();
		var elemento = $('#formTerminar2').parent();
		$(elemento).toggle();
		$("#infoTerminar").toggle();
		peticionAjax($(this).attr('action'), $(this).serialize(), terminar2);
	});
	//Hace la petición ajax de finalizar el juego
	$('#formTerminar3').submit(function(event){
		event.preventDefault();
		var elemento = $('#formTerminar3').parent();
		$(elemento).toggle();
		$("#infoTerminar").toggle();
		peticionAjax($(this).attr('action'), $(this).serialize(), terminar2);
	});
	//Hace la petición ajax de finalizar el juego
	$('#formTerminar4').submit(function(event){
		event.preventDefault();
		var elemento = $('#formTerminar4').parent();
		$(elemento).toggle();
		$("#infoTerminar").toggle();
		$("#nuevaSesion").css("display","none");
		peticionAjax($(this).attr('action'), $(this).serialize(), terminar2);
	});
	//Hace la petición ajax de comprobar si un login está en uso
	$("#comprobarLogin").click(function() {
		var url = $('#formRegistro').attr('action')+"?accion=comprobarLogin";
		console.log(url);
		peticionAjax(url, "login="+$("#login2").val(), comprobarLogin);
	});
	//Hace la peticón ajax de registro de un usuario
	$('#formRegistro').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), registro);
	});
	//Hace la petición ajax de enviar la respuesta al fotograma mostrado
	$('#formRespuesta').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), respuestaFotograma);
	});
	//Hace la petición ajax de mostrar un nuevo fotograma
	$('#nuevoFotograma').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), llegadaFotograma);
	});
	//Hace la petición ajax de iniciar una nueva sesión de juego
	$('#formNuevaSesion').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), llegadaNuevaSesion);
	});
}

// Usada por algunas peticiones para ocultar y mostar elementos
function mostrarOcultarElemento(elemento){
	$(elemento).toggle();
    $("#general").toggle();
}

//Petición ajax que recibe la url a la cual debe dirigirse, los datos a enviar y la función que debe procesar los
// datos de respuesta
var peticionAjax = function(url, datos, llegadaDatos){
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json", //tipo de dato que se va ha recibir
        contentType: "application/x-www-form-urlencoded", //como se envia los datos
        url:url,
        data:datos, //cadena, los datos de la petición
        beforeSend: inicioEnvio,
        success: llegadaDatos, //función que recupera los datos devueltos
        timeout: 4000, //tiempo máximo a esperar para la respuesta
        error: problemas //Si hay algún problema, se ejecuta
    });
}

// Función que recibe los datos de la petición ajax de loguearse
function login(datos){
	console.log(datos);
	var loginCorrecto = datos[0];
	if(loginCorrecto == "true"){
		$("#divLogin").toggle();
		$("#divMenuJuego").toggle();
		$("#mensajesErrores").empty();
		$("#mensajesErrores").append("<h4 class='bg-info'>Se ha logeado como: "+datos[1]+"</h4>");
	} else {
		$("#mensajesErrores").empty();
		$("#mensajesErrores").append("<h4 class='bg-info'>"+datos[1]+"</h4>");
	}
}

//Función que recibe los datos de la petición ajax de finalizar el juego
// Hay varias ya que el usuario puede terminar en diferentes situaciones
function terminar(datos){
	console.log(datos);
	if($.isArray(datos)){
		$("#porcentaje").empty();
		$("#porcentaje").text(datos[0]);
		$("#puntosGlobales").empty();
		$("#puntosGlobales").text(datos[1]);
		$("#posicion").empty();
		$("#posicion").text(datos[2]);
		$("#mensajesErrores").css("display","none");
		$("#barraProgreso").css("display","none");
	}
}

//Función que recibe los datos de la petición ajax de finalizar el juego
function terminar2(datos){
	console.log(datos);
	if($.isArray(datos)){
		$("#porcentaje").empty();
		$("#porcentaje").text(datos[0]);
		$("#puntosGlobales").empty();
		$("#puntosGlobales").text(datos[1]);
		$("#posicion").empty();
		$("#posicion").text(datos[2]);
		$("#barraProgreso").css("display","none");
	}
}

//Función que recibe los datos de la petición ajax de comprobar si el login ya está en uso por otro suario
function comprobarLogin(datos){
	console.log(datos);
	if(datos){
		console.log("existe el login");
		$("#mensajesErrores").empty();
		$("#mensajesErrores").append("<h4 class='bg-warning'>El login ya está en uso</h4>");
		
	} else {
		console.log("no existe el login");
		$("#mensajesErrores").empty();
		$("#mensajesErrores").append("<h4 class='bg-info'>El login no está en uso</h4>");
	}
}

//Función que recibe los datos de la petición ajax de registrarse 
function registro(datos){
	if(datos){
		console.log(datos);
		$("#mensajesErrores").empty();
		$("#mensajesErrores").append("<h4 class='bg-info'>"+datos+"</h4>");
	} else {
		console.log("Todo ok");
		$("#mensajesErrores").empty();
		$("#divRegistro").toggle();
		$("#divMenuJuego").toggle();
	}
}

//Función que recibe los datos de la petición ajax de consultar la información
function datosUsuario(datos){
	console.log(datos);
	$("#numGlobalFotOfrecidos").text(datos[0]);
	$("#numGlobalAciertos").text(datos[1]);
	$("#porAciertosGlobal").text(datos[2]);
	$("#puntos").text(datos[3]);
}

//Función que recibe los datos de la petición ajax del un nuevo fotograma
// Hay dos ya que una se realiza automáticamente
function llegadaFotograma(datos){
	var info = datos;
	console.log(info);
	if(info === "enBD"){
		console.log("Sin fotogramas en la BB.DD.");
		$("#muestraRespuesta").empty();
		var elemento = $('#nuevoFotograma').parent().parent();
		$(elemento).toggle();
		$("#mensaje").append("<h4 class='bg-info'>Ha jugado con todos los fotogramas de la BB.DD.</h4>");
		$("#sinFotogramas").toggle();
	} else if(info === "enSesion"){
		console.log("Sin fotogramas en la sesión");
		var elemento = $('#nuevoFotograma').parent().parent();
		$(elemento).toggle();
		$("#mensaje").append("<h4 class='bg-info'>Ha jugado con todos los fotogramas de la sesión.</h4>");
		$("#sinFotogramas").toggle();
		$("#nuevaSesion").toggle();
	} else{
		$("#imagenFotograma").attr("src","fotogramas/"+datos[3]);
		$("#opcion1valor").empty();
		$("#opcion1").empty();
		$("#opcion2valor").empty();
		$("#opcion2").empty();
		$("#opcion3valor").empty();
		$("#opcion3").empty();
		$("#opcion1valor").attr("value",datos[0]);
		$("#opcion1").append(datos[0]);
		$("#opcion2valor").attr("value",datos[1]);
		$("#opcion2").append(datos[1]);
		$("#opcion3valor").attr("value",datos[2]);
		$("#opcion3").append(datos[2]);
		$("#porcentajeBarra").css("width", datos[4]+"0%");
		//Muestra el contenedor del fotograma y las respuestas, ya que la primeravez está oculto
		var elemento = $('#nuevoFotograma').parent().parent();
		$(elemento).toggle();
		$("#muestraFotograma").toggle();
	}
}

//Función que recibe los datos de la petición ajax de un nuevo fotograma
function llegadaFotograma2(datos){
	var info = datos;
	console.log(info);
	if(info === "enBD"){
		console.log("Sin fotogramas en la BB.DD.");
		$("#muestraRespuesta").empty();
		var elemento = $('#nuevoFotograma').parent().parent();
		$(elemento).toggle();
		$("#mensaje").append("<h4 class='bg-info'>Ha jugado con todos los fotogramas de la BB.DD.</h4>");
		$("#sinFotogramas").toggle();
	} else if(info === "enSesion"){
		console.log("Sin fotogramas en la sesión");
		$("#muestraRespuesta").empty();
		var elemento = $('#nuevoFotograma').parent().parent();
		$(elemento).toggle();
		$("#mensaje").append("<h4 class='bg-info'>Ha jugado con todos los fotogramas de la sesión.</h4>");
		$("#sinFotogramas").toggle();
		$("#nuevaSesion").toggle();
	} else{
		$("#imagenFotograma").attr("src","fotogramas/"+datos[3]);
		$("#opcion1valor").empty();
		$("#opcion1").empty();
		$("#opcion2valor").empty();
		$("#opcion2").empty();
		$("#opcion3valor").empty();
		$("#opcion3").empty();
		$("#opcion1valor").attr("value",datos[0]);
		$("#opcion1").append(datos[0]);
		$("#opcion2valor").attr("value",datos[1]);
		$("#opcion2").append(datos[1]);
		$("#opcion3valor").attr("value",datos[2]);
		$("#opcion3").append(datos[2]);
		$("#porcentajeBarra").css("width", datos[4]+"0%");
	}
}

//Función que recibe los datos de la petición ajax de la respuesta al fotograma
function respuestaFotograma(datos){
	console.log(datos);
	if(datos){
		$("#responde").empty();
		$("#responde").append("<h4 class='bg-info'>Acertaste</h4>");
		
	} else {
		console.log("no existe el login");
		$("#responde").empty();
		$("#responde").append("<h4 class='bg-warning'>Fallaste</h4>");
	}
	var elemento = $('#formTerminar2').parent();
	$(elemento).toggle();
	$("#muestraRespuesta").toggle();
}

//Función que recibe los datos de la petición ajax de iniciar una nueva sesión
function llegadaNuevaSesion(datos){
	console.log(datos);
	if(datos){
		console.log("Se ha solicitado una nueva sesión.");
		window.location.href='/PR04/controlador?accion=jugar'; 
	}
}

function inicioEnvio() {
    //no se hace nada
}

//Función que recibe los datos de la petición ajax cuando hay algún error
function problemas() {
	console.log("Hay un problema en el servidor.");
	$("#nomUsuario").append("<h4 class='bg-danger'>Hay un problema en la aplicación, intentelo más tarde.</h4>");
	
}
