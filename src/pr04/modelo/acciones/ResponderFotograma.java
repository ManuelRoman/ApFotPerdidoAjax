package pr04.modelo.acciones;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import pr04.controlador.Accion;
import pr04.dao.BeanDaoInsercionImpl;
import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.DatosJuego;
import pr04.modelo.beans.Fotograma;
import pr04.modelo.beans.ModeloAjax;
import pr04.modelo.beans.Usuario;

/**
 * Clase que procesa la respuesta del usuario al fotograma ofrecido
 */
public class ResponderFotograma implements Accion{

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
		boolean acierto = true;
		ModeloAjax modelo = new ModeloAjax();
		HttpSession sesion = request.getSession();
		String opcionSeleccionada = request.getParameter("opcionSeleccionada");
		System.out.println("Respuesta seleccionada: " + opcionSeleccionada);
		DatosJuego datosJuego = (DatosJuego) sesion.getAttribute("datosJuego");
		Fotograma fotogramaSelec = (Fotograma) sesion.getAttribute("fotogramaSelec");
		String json = null;		
		ArrayList<Integer> listaIdFotSesion = (ArrayList<Integer>) sesion.getAttribute("listaIdFotSesion");
		int puntosAcierto = (int) sesion.getAttribute("puntosAcierto");
		int puntosFallo = (int) sesion.getAttribute("puntosFallo");
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		BeanDaoInsercionImpl daoInsercion = (BeanDaoInsercionImpl) sesion.getAttribute("daoInsercion");
		
		// Comprueba se el usuario ha acertado y añade o resta los puntos
		if (!opcionSeleccionada.equals(fotogramaSelec.getTitPelicula())) {
			acierto = false;
			datosJuego.setPuntosSesion(puntosFallo);
		} else {
			datosJuego.setPuntosSesion(puntosAcierto);
			datosJuego.setNumAciertosSesion(1);
		}
		// Para evistar trampas si el usuario ha fallado se actualizan los datos en la bb.dd.
		try {
			daoInsercion.getConexion();
			if(acierto){
				daoInsercion.actualizaFotAcertado(fotogramaSelec.getIdFotograma(), usuario.getLogin());
			}
			// actualiza puntuación del ranking y numGlobalAciertos de concurso
			daoInsercion.actualizaDatosJuego(usuario, datosJuego);
			daoInsercion.actualizaRanking(usuario, datosJuego);
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
			} catch (Exception e) {
				System.out.println("Error al cerrar la conexión.");
				e.printStackTrace();
			}	
		}
		// Se elimina el fotograma jugado (acertado o no), de la sesión de juego
		listaIdFotSesion.remove((Integer)fotogramaSelec.getIdFotograma());
		// Se comprueba si se han acabado los fotogramas de la sesión de juego
		if (listaIdFotSesion.isEmpty()) {
			sesion.setAttribute("sinFotogramasSesion", "true");
			sesion.setAttribute("sinFotogramas", "enSesion");
			System.out.println("Sin fotogramas en la Sesión");
		}
		if(error==null){
			json = new Gson().toJson(acierto);
		} else {
			json = new Gson().toJson(error);
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
