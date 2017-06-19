package pr04.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import pr04.modelo.acciones.AccionIndex;
import pr04.modelo.beans.BeanError;
import pr04.utilidades.LeePropiedades;

/**
 * Instancia objetos de tipo Acción.
 * Es una clase abstracta que impide que se puedan instanciar objetos de ella,
 * pero permite que se obtengan clases derivadas.
 * Se encarga de obtener los objetos Acción específicos para una determinada acción.
 */
public abstract class FactoriaAcciones {
	
	/**
	 * Información de la lista de acciones disponibles
	 */
	private static HashMap<String, Accion> listaAcciones = null;
	
	/**
	 * Devuelve objetos de tipo Accion en función del parámetro de acción proporcionado.
	 * @param accion Cadena que representa la acción que se desea llevar a cabo
	 * @return Objeto de tipo Accion, que encapsula el proceso a llevar a cabo.
	 */
	@SuppressWarnings("unchecked")
	public static Accion creaAccion(String accion, String archivoAcciones) {
		System.out.println("Acceso a creaAccion, acción: " + accion);
		// Solo se accede la primera vez que se ejecuta la aplicación al archivo de propiedades
		if (listaAcciones == null) {
			System.out.println("Accede al archivo propiedades");
			Properties propiedades = null;
			try {
				propiedades = LeePropiedades.getPropiedades(archivoAcciones);
			} catch (FileNotFoundException e2) {
				System.out.println("Archivo de acciones no encontrado: " + archivoAcciones);
				e2.printStackTrace();
			} catch (IOException e2) {
				System.out.println("Error al leer del archivo de acciones: " + archivoAcciones);
				e2.printStackTrace();
			}
			listaAcciones = new HashMap<String, Accion>();
			Enumeration e = propiedades.keys();
			while (e.hasMoreElements()) {
				String clave = (String) e.nextElement();
				String valorAccion = (String) propiedades.get(clave);
				Class clase = null;
				try {
					clase = Class.forName(valorAccion);
					Accion valor = (Accion) clase.newInstance();
					listaAcciones.put(clave, valor);
				} catch (ClassNotFoundException e1) {
					System.out.println("Clase acción no encontrada: " + clave);
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
		// Acción por defecto. Conduce a index.html.
		Accion accionSeleccionada = new AccionIndex();
		if (accion!= null){
			accionSeleccionada = listaAcciones.get(accion);
		}
		return accionSeleccionada;
	}

}
