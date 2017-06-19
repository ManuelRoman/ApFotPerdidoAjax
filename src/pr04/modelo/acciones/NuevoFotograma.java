package pr04.modelo.acciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import pr04.controlador.Accion;
import pr04.dao.BeanDaoConsultasImpl;
import pr04.dao.BeanDaoInsercionImpl;
import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.DatosJuego;
import pr04.modelo.beans.Fotograma;
import pr04.modelo.beans.ModeloAjax;
import pr04.modelo.beans.Usuario;

/**
 * Encapsula el proceso de seleccionar un nuevo fotograma para mostrar
 */
public class NuevoFotograma implements Accion{

	/**
	 * Unidad de persistencia que se empleará para acceder a la base de datos.
	 * @uml.property  name="UP"
	 */
	private String UP = null;
	/**
	 * Bean de error para situaciones en los que el método ejecutar() devuelve false.
	 * @uml.property  name="error"
	 * @uml.associationEnd  
	 */
	private BeanError error = null;
	/**
	 * Objeto que encapsula el modelo que procesará la vista.
	 * @uml.property  name="modelo"
	 */
	private Object modelo = null;
	/**
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property  name="vista"
	 */
	private String vista = null;
	
	/**
	 * Contexto de aplicación.
	 */
	private ServletContext Sc;

	/** 
	 * Ejecuta el proceso asociado a la acción.
	 * @param request Objeto que encapsula la petición.
	 * @param response Objeto que encapsula la respuesta.
	 * @return true o false en función de que no se hayan producido errores o lo contrario.
	 * @see fotperdido.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean estado = true;
		ModeloAjax modelo = new ModeloAjax();
		ArrayList<String> respuesta = new ArrayList<String>();
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		DatosJuego datosJuego = (DatosJuego) sesion.getAttribute("datosJuego");
		String sinFotogramasBD = (String) sesion.getAttribute("sinFotogramasBD");
		System.out.println("Sin fotogramas en la bd:" + sinFotogramasBD);
		String sinFotogramasSesion = (String) sesion.getAttribute("sinFotogramasSesion");
		System.out.println("Recibida petición de un nuevo fotograma.");
		Fotograma fotogramaSelec = new Fotograma();
		// Recupera la lista de id de todos los fotogramas, para construir las respuestas falsas
		ArrayList<Integer> listaIdFotTotal = (ArrayList<Integer>) sesion.getAttribute("listaIdFotTotal");
		// Recupera la lista de id de fotogramas  a mostrar en la sesion
		ArrayList<Integer> listaIdFotSesion = (ArrayList<Integer>) sesion.getAttribute("listaIdFotSesion");
		String json = null;		
		BeanDaoConsultasImpl daoConsultas = (BeanDaoConsultasImpl) sesion.getAttribute("daoConsultas");
		BeanDaoInsercionImpl daoInsercion = (BeanDaoInsercionImpl) sesion.getAttribute("daoInsercion");
		
		// Se comprueba si ya ha jugado todos los fotogramas de la sesión, si no es así se realiza todo el 
		// proceso de seleccionar un nuevo fotograma
		if (listaIdFotSesion.isEmpty()) {
			if (sinFotogramasBD.equals("false")){
				sesion.setAttribute("sinFotogramasSesion", "true");
				sesion.setAttribute("sinFotogramas", "enSesion");
				System.out.println("Sin fotogramas en la sesión.");
			}
		} else {
			int idFotogramaSelec = listaIdFotSesion.get((int) (Math.random() * listaIdFotSesion.size()));
			int idRespFalsa;
			int idRespFalsa2;
			// obtiene un id mientras coincida con el id del fotograma seleccionado
			do {
				idRespFalsa = listaIdFotTotal.get((int) (Math.random() * listaIdFotTotal.size()));
			} while (idRespFalsa == idFotogramaSelec);
			// obtiene un id mientras coincida con el id del fotograma
			// seleccionado y el de la primera respuesta falsa
			do {
				idRespFalsa2 = listaIdFotTotal.get((int) (Math.random() * listaIdFotTotal.size()));
			} while (idRespFalsa2 == idFotogramaSelec || idRespFalsa2 == idRespFalsa);
			datosJuego.setNumFotOfrecidosSesion(1);
			
			try {
				daoInsercion.getConexion();
				daoConsultas.getConexion();
				respuesta.add(daoConsultas.obtenerTituloFotograma(idRespFalsa));
				respuesta.add(daoConsultas.obtenerTituloFotograma(idRespFalsa2));
				fotogramaSelec = daoConsultas.obtenerFotograma(idFotogramaSelec);
				// Actualiza el número de fotogramas ofrecidos, evitamos trampas por parte del jugador si no acierta el fotograma
				daoInsercion.actualizaDatosJuego(usuario, datosJuego);
			} catch (RollbackException e) {
				error = new BeanError("La base de datos no está operativa, intentelo más tarde");
				e.printStackTrace();
			} catch (IllegalStateException e) {
				error = new BeanError("La base de datos no está operativa, intentelo más tarde",e);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				error = new BeanError("La base de datos no está operativa, intentelo más tarde",e);
				e.printStackTrace();
			} catch (Exception e) {
				error = new BeanError("La base de datos no está operativa, intentelo más tarde",e);
				e.printStackTrace();
			} finally {
				try {
					daoInsercion.close();
					daoConsultas.close();
				} catch (Exception e) {
					System.out.println("Error al cerrar la conexión.");
					e.printStackTrace();
				}
			}			
			respuesta.add(fotogramaSelec.getTitPelicula());
			Collections.shuffle(respuesta);
			respuesta.add(fotogramaSelec.getArchivo());
			respuesta.add(String.valueOf(listaIdFotSesion.size()));
		}
		System.out.println("Sin fotogramas en la Sesion:" + sinFotogramasSesion);
		// Comprobamos que no se hay producido ningún error al acceder a la bb.dd.
		if (error == null) {
			// Comprobamos si se ha quedado  sin fotogramas, si es así añadimos la variable a la sesión, que nos servirá por si el usuario
			// intenta acceder de nuevo al juego desde index, la accion por defecto
			if ((sesion.getAttribute("sinFotogramasSesion").equals("true")) || (sesion.getAttribute("sinFotogramasBD").equals("true"))){
				json = new Gson().toJson(sesion.getAttribute("sinFotogramas"));
				} else {
					json = new Gson().toJson(respuesta);
					sesion.setAttribute("fotogramaSelec", fotogramaSelec);
				}
		} 
		else {
			json = new Gson().toJson(error.getMensError());
		}
		modelo.setContentType("application/json;charset=UTF-8");
		modelo.setRespuesta(json);
		this.setModelo(modelo);
		System.out.println("json a enviar: "+json.toString());
		return estado;
	}

	/**
	 * Devuelve el error asociado a la acción, si lo hubiera.
	 * @return
	 * @uml.property  name="error"
	 */
	@Override
	public Exception getError() {
		return error;
	}

	/**
	 * Devuelve el objeto modelo
	 * @return
	 * @uml.property  name="modelo"
	 */
	@Override
	public Object getModelo() {
		return modelo;
	}
	
	/**
	 * Método setter para la propiedad modelo.
	 * @param modelo  El modelo a establecer.
	 * @uml.property  name="modelo"
	 */
	private void setModelo(Object modelo) {
		this.modelo = modelo;
	}	

	/**
	 * Devuelve la vista que debe procesar el modelo. En caso de ser
	 * una petición AJAX, la vista deberá ser null.
	 * @return
	 * @uml.property  name="vista"
	 */
	@Override
	public String getVista() {
		// La vista devuelta por una petición AJAX es null
		return vista;
	}
	
	/**
	 * Método setter para la propiedad vista.
	 * @param vista  La vista a establecer.
	 * @uml.property  name="vista"
	 */
	private void setVista(String vista) {
		this.vista = vista;
	}
	
	/**
	 * Método getter para la propiedad UP (unidad de persistencia).
	 * @return  La unidad de persistencia UP.
	 * @uml.property  name="UP"
	 */
	private String getUP() {
		return UP;
	}
	
	/**
	 * Establece el valor de la unidad de persistencia
	 * @param up
	 * @uml.property  name="UP"
	 */
	@Override
	public void setUP(String up) {
		this.UP = up;
	}

	/**
	 * Establece el contexto de aplicación
	 * @param sc
	 * @uml.property  name="sc"
	 */
	@Override
	public void setSc(ServletContext sc) {
		this.Sc = sc;
	}

}
