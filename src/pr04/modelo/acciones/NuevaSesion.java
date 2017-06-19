package pr04.modelo.acciones;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import pr04.controlador.Accion;
import pr04.dao.BeanDaoConsultasImpl;
import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.ModeloAjax;
import pr04.modelo.beans.Usuario;

/**
 * Encapsula el proceso de iniciar una nueva sesión de juego, elimina todas los objetos de la sesión y actualiza los datos del usuario
 * pidiendolos de nuevo a la base de datos
 */
public class NuevaSesion implements Accion{

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
		System.out.println("Se ha solicitado una nueva sesión.");
		ModeloAjax modelo = new ModeloAjax();
		String json = null;
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		BeanDaoConsultasImpl daoConsultas = (BeanDaoConsultasImpl) sesion.getAttribute("daoConsultas");
		// objeto que obtiene los datos actualizados de la bb.dd.
		Usuario usuarioActualizado = null;
		try {
			daoConsultas.getConexion();
			usuarioActualizado = daoConsultas.getUsuario(usuario.getLogin(), usuario.getClave());
		} catch (IllegalStateException e) {
			estado = false;
			error = new BeanError("La base de datos no está operativa, intentelo más tarde");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			estado = false;
			error = new BeanError("La base de datos no está operativa, intentelo más tarde",e);
			e.printStackTrace();
		} catch (BeanError e) {
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
		sesion.removeAttribute("sinFotogramasBD");
		sesion.removeAttribute("sinFotogramasSesion");
		sesion.removeAttribute("sinFotogramas");
		sesion.removeAttribute("listaIdFotTotal");
		sesion.removeAttribute("listaIdFotAcertadosSesion");
		sesion.removeAttribute("listaIdFotSesion");
		sesion.removeAttribute("fotogramaSelec");
		sesion.removeAttribute("datosJuego");
		// Cambia el usuario anterior y actualiza los datos, ya que luego serán recogidos en datosJuego.
		sesion.setAttribute("usuario", usuarioActualizado);
		modelo.setContentType("application/json;charset=UTF-8");
		json = new Gson().toJson(estado);
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
