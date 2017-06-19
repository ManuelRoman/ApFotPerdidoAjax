package pr04.modelo.beans;

import java.io.*;

/**
 * Encapsula la información del error que pueda producirse mientras
 * se ejecuta la aplicación.
 * @author Eduardo A. Ponce
 */
@SuppressWarnings("serial")
public class BeanError extends Exception implements Serializable {
	private int codError;
	private String mensError;
	private Exception excepcion = null;

	public BeanError(int codError, String mensError) {
		super(mensError);
		this.setCodError(codError);
		this.setMensError(mensError);
		setMensaje(codError);
	}

	public BeanError(String mensError) {
		super(mensError);
		this.setCodError(0);
		this.setMensError(mensError);
	}
	
	public BeanError(String mensError, Exception excepcion) {
		super(mensError);
		this.setCodError(0);
		this.setMensError(mensError);
		this.setExcepcion(excepcion);
	}
	
	public BeanError(int codError, String mensError, Exception excepcion) {
		super(mensError);
		this.setCodError(codError);
		this.setMensError(mensError);
		this.setExcepcion(excepcion);
	}

	private void setMensaje(int codError) {
		switch (codError) {
		case 1064:
			this.setMensError("Error de sintaxis MySQL");
			break;
		default:
			// this.setMensError(mensError);
		}
	}

	/**
	 * @param codError the codError to set
	 */
	private void setCodError(int codError) {
		this.codError = codError;
	}

	/**
	 * @return the codError
	 */
	@SuppressWarnings("unused")
	public int getCodError() {
		return codError;
	}

	/**
	 * @param mensError the mensError to set
	 */
	private void setMensError(String mensError) {
		this.mensError = mensError;
	}

	/**
	 * @return the mensError
	 */
	@SuppressWarnings("unused")
	public String getMensError() {
		return mensError;
	}

	/**
	 * @param excepcion the excepcion to set
	 */
	private void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	/**
	 * @return the excepcion
	 */
	@SuppressWarnings("unused")
	public Exception getExcepcion() {
		return excepcion;
	}

	/**
	 * Método que imprime la información del error
	 */
	public String toString() {
		return "Código error: " + this.codError + ", " + this.mensError;
	}

}
