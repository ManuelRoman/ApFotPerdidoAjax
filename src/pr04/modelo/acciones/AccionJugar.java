package pr04.modelo.acciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.persistence.EntityExistsException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr04.controlador.Accion;
import pr04.dao.BeanDaoConsultasImpl;
import pr04.dao.BeanDaoInsercionImpl;
import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.DatosJuego;
import pr04.modelo.beans.Usuario;
import pr04.utilidades.LeePropiedades;

/**
 * Clase que encapsula la preparación de los elementos/objetos necesarios para el juego.
 */
public class AccionJugar implements Accion{

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
	 * Si no hay errores en el procesamiento de la acción
	 */
	private String vistaOk = "WEB-INF/juego.jsp";
	
	/**
	 * Si se precisa reintentar el registro.
	 */
	private String vistaNoOk = "WEB-INF/gesError.jsp";
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
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		DatosJuego datosJuego = (DatosJuego) sesion.getAttribute("datosJuego");
		String sinFotogramasBD = (String) sesion.getAttribute("sinFotogramasBD");
		String sinFotogramasSesion = (String) sesion.getAttribute("sinFotogramasSesion");
		int numFotSesion = 0; //Se inicializa el número de fotogramas por sesión.
		BeanDaoConsultasImpl daoConsultas = (BeanDaoConsultasImpl) sesion.getAttribute("daoConsultas");
		BeanDaoInsercionImpl daoInsercion = (BeanDaoInsercionImpl) sesion.getAttribute("daoInsercion");

		// Listas relacionadas con los fotogramas
		ArrayList<Integer> listaIdFotTotal = (ArrayList<Integer>) sesion.getAttribute("listaIdFotTotal");
		ArrayList<Integer> listaIdFotAcertadosSesion = (ArrayList<Integer>) sesion.getAttribute("listaIdFotAcertadosSesion");
		ArrayList<Integer> listaIdFotSesion = (ArrayList<Integer>) sesion.getAttribute("listaIdFotSesion");

		// Pasa al objeto encargado de almacenar los datos del juego y clacular las estadísticas, la información necesaria
		// anteriormente almacenada en la base de datos.
		if(datosJuego == null){
			System.out.println("Puntos totales del usuario: "+usuario.getRanking().getPuntos());
			datosJuego = new DatosJuego(usuario.getConcurso().getNumGlobalFotOfrecidos(), usuario.getConcurso().getNumGlobalAciertos(), usuario.getRanking().getPuntos());
			sesion.setAttribute("datosJuego", datosJuego);
			System.out.println("Fotogramas totales ofrecidos: "+usuario.getConcurso().getNumGlobalFotOfrecidos());
		}
		// Se comprueba si el usuario ya ha jugado todos los fotogramas de la bb.dd. (para cuando vuelve al index pulsado intro),
		// si no es así se pone a false		
		if(sinFotogramasBD == null){
			sesion.setAttribute("sinFotogramasBD", "false");
		}
		// Se comprueba si el usuario ya ha jugado todos los fotogramas de la sesión (para cuando vuelve al index pulsado intro),
		// si no es así se pone a false
		if(sinFotogramasSesion == null){
			sinFotogramasSesion = "false";
			sesion.setAttribute("sinFotogramasSesion", "false");
		}		
		if (daoConsultas == null){
			daoConsultas = new BeanDaoConsultasImpl(this.UP);
		}		
		if (daoInsercion == null){
			daoInsercion = new BeanDaoInsercionImpl(this.UP);
			sesion.setAttribute("daoInsercion", daoInsercion);
		}
		
		// Se obtiene el número de fotogramas de la sesión, los puntos por acierto y fallo y se añaden a la sesión
		if (sesion.getAttribute("numFotSesion") == null) {
			Properties propiedades = null;
			try{
				propiedades = LeePropiedades.getPropiedades(Sc.getRealPath("/WEB-INF/"+Sc.getInitParameter("datosConcurso")));
				numFotSesion = Integer.parseInt(propiedades.getProperty("numFotSesion"));
				sesion.setAttribute("numFotSesion", numFotSesion);
				int puntosAcierto = Integer.parseInt(propiedades.getProperty("puntosAcierto"));
				sesion.setAttribute("puntosAcierto", puntosAcierto);
				int puntosFallo = Integer.parseInt(propiedades.getProperty("puntosFallo"));
				sesion.setAttribute("puntosFallo", puntosFallo);
			} catch (FileNotFoundException e2) {
				System.out.println("Archivo de acciones no encontrado: datosConcurso");
				estado = false;
				error = new BeanError("La aplicación no está operativa, intentelo más tarde");
				e2.printStackTrace();
			} catch (IOException e2) {
				System.out.println("Error al leer del archivo de acciones: datosConcurso");
				estado = false;
				error = new BeanError("La aplicación no está operativa, intentelo más tarde");
				e2.printStackTrace();
			}
		} else {
			numFotSesion = (int) sesion.getAttribute("numFotSesion");
		}
		
		// Si entra por primera vez se inicializan las listas relacionadas con los fotogramas
		if (listaIdFotTotal == null) {
			System.out.println("Consulta todas las lista por primera vez.");
			listaIdFotSesion = new ArrayList<Integer>();
			listaIdFotAcertadosSesion = new ArrayList<Integer>();
			try {
				daoConsultas.getConexion();
				//Obtiene la posición el jugador en el ranking
				datosJuego.setPosicion(daoConsultas.getPosicion(usuario));
				//Obtiene la lista de todos los fotogramas de la base de datos (para las respuestas)
				listaIdFotTotal = (ArrayList<Integer>) daoConsultas.getIdFotogramas();
				//Obtiene la lista de los fotogramas que ha acertado el usuario en participaciones anteriores
				listaIdFotSesion = (ArrayList<Integer>) daoConsultas.listaFotNoAcertados(usuario.getLogin(), numFotSesion);
				// Si la lista es nula significa que usuario no ha jugado nunca y se inicializa la lista de de fotogramas a certados a false 
				if (listaIdFotSesion == null){
					System.out.println("no existe crear entradas");
					daoInsercion.insertaFotAcertados(usuario.getLogin());
					listaIdFotSesion = (ArrayList<Integer>) daoConsultas.listaFotNoAcertados(usuario.getLogin(), numFotSesion);
				}
			} catch (IllegalStateException e) {
				estado = false;
				error = new BeanError("La base de datos no está operativa, intentelo más tarde");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				estado = false;
				error = new BeanError("La base de datos no está operativa, intentelo más tarde",e);
				e.printStackTrace();
			} catch (Exception e) {
				estado = false;
				error = new BeanError("La base de datos no está operativa, intentelo más tarde",e);
				e.printStackTrace();
			} finally {
				try {
					daoConsultas.close();
				} catch (Exception e) {
					System.out.println("Error al cerrar la conexión.");
					e.printStackTrace();
				}
			}
	        // Se añade a la sesión la lista de id de todos los fotogramas de la bbdd 
			sesion.setAttribute("listaIdFotTotal", listaIdFotTotal);
			sesion.setAttribute("listaIdFotSesion", listaIdFotSesion);
			sesion.setAttribute("listaIdFotAcertadosSesion", listaIdFotAcertadosSesion);
		}
		// Cuando el usuario ha acertado todos los fotogramas de la bbdd
		if (listaIdFotSesion.isEmpty()) {
			if (sinFotogramasSesion.equals("false")){
				sesion.setAttribute("sinFotogramasBD", "true");
				sesion.setAttribute("sinFotogramas", "enBD");
				System.out.println("Sin fotogramas en la BBDD");
			}
		}
		if (estado == true) {
			vista = vistaOk;
		} else
			vista = vistaNoOk;
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
