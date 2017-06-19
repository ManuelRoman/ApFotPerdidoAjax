package pr04.modelo.beans;

/**
 * Clase que encapsula los datos del juego actual y realiza los cálculos para las estadísticas
 */
public class DatosJuego {
	
	/**
	 * Información del porcentaje global de fotogramas acertados
	 */
	private int porAciertosGlobal;
	
	/**
	 * Información del porcentaje de fotogramas acertados durante la sesión
	 */
	private int porAciertosSesion;

	/**
	 * Información del número de fotogramas acertados en todas las sesiones
	 */
	private int numGlobalAciertos;
	
	/**
	 * Información del número de fotogramas acertados en la sesión
	 */
	private int numAciertosSesion;

	/**
	 * Información de los puntos ganados en todas las sesiones de juego
	 */
	private int puntosGlobales;
	
	/**
	 * Información de los puntos ganados en todas la sesion de juego actual
	 */
	private int puntosSesion;

	/**
	 * Información de la posición en el ranking del jugador
	 */
	private int posicion;

	/**
	 * Información del número de fotogramas ofrecidos en todas las sesiones del juego
	 */
	private int numGlobalFotOfrecidos;
	
	/**
	 * Información del número de fotogramas ofrecidos en la sesión del juego
	 */
	private int numFotOfrecidosSesion;

	/**
	 * Constructor sin parámetros
	 */
	public DatosJuego() {
		this.posicion = 0;
		this.numGlobalAciertos = 0;
		this.numGlobalFotOfrecidos = 0;
		this.porAciertosGlobal = 0;
		this.porAciertosSesion = 0;
		this.puntosGlobales = 0;
		this.puntosSesion = 0;
		this.numAciertosSesion = 0;
		this.numFotOfrecidosSesion = 0;
	}
	
	/**
	 * Constructor con parámetros
	 * @param numGlobalFotOfrecidos
	 * @param numGlobalAciertos
	 * @param puntosGlobales
	 */
	public DatosJuego(int numGlobalFotOfrecidos, int numGlobalAciertos, int puntosGlobales) {
		this.posicion = 0;
		this.numGlobalAciertos = numGlobalAciertos;
		this.numGlobalFotOfrecidos = numGlobalFotOfrecidos;
		this.porAciertosGlobal = 0;
		this.porAciertosSesion = 0;
		this.puntosGlobales = puntosGlobales;
		this.puntosSesion = 0;
		this.numAciertosSesion = 0;
		this.numFotOfrecidosSesion = 0;
	}

	/**
	 * Realiza el cálculo del tanto porciento
	 * @return the porAciertosGlobal
	 */
	public int getPorAciertosGlobal() {
		try{
			porAciertosGlobal = Math.round((numGlobalAciertos*100)/numGlobalFotOfrecidos);
		} catch (ArithmeticException ae){
			this.porAciertosGlobal = 0;
		}
		return porAciertosGlobal;
	}

	public void setPorAciertosGlobal(int porAciertosGlobal) {
		this.porAciertosGlobal = porAciertosGlobal;
	}

	/**
	 * Realiza el cálculo del tanto porciento
	 * @return the porAciertosSesion
	 */
	public float getPorAciertosSesion() {
		try {
			this.porAciertosSesion = Math.round((numAciertosSesion*100)/numFotOfrecidosSesion);
		} catch (ArithmeticException ae){
			this.porAciertosSesion = 0;
		}
		return porAciertosSesion;
	}

	public void setPorAciertosSesion(int porAciertosSesion) {
		this.porAciertosSesion = porAciertosSesion;
	}

	/**
	 * Devuelve el número total de aciertos de todas las sesiones
	 * @return the numGlobalAciertos
	 */
	public int getNumGlobalAciertos() {
		return numGlobalAciertos;
	}

	/**
	 * Añade un acierto
	 * @param numGlobalAciertos
	 */
	public void setNumGlobalAciertos(int numGlobalAciertos) {
		this.numGlobalAciertos = this.numGlobalAciertos + numGlobalAciertos;
	}

	/**
	 * Devuelve los puntos totales
	 * @return the puntosGlobales
	 */
	public int getPuntosGlobales() {
		return puntosGlobales;
	}

	/**
	 * Añade un punto al total
	 * @param puntosGlobales
	 */
	public void setPuntosGlobales(int puntosGlobales) {
		this.puntosGlobales = this.puntosGlobales + puntosGlobales;
	}

	/**
	 * Devuelve los puntos de la sesión de juego
	 * @return the puntosSesion
	 */
	public int getPuntosSesion() {
		return puntosSesion;
	}

	/**
	 * Añade un punto a la sesión de juego y al total
	 * @param puntosSesion
	 */
	public void setPuntosSesion(int puntosSesion) {
		this.puntosSesion = this.puntosSesion + puntosSesion;
		setPuntosGlobales(puntosSesion);
	}

	/**
	 * Devueleve la posición en el ranking
	 * @return the posicion
	 */
	public int getPosicion() {
		return posicion;
	}

	/**
	 * Establece la posición en el ranking
	 * @param posicion
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * Devuelve el número total de fotogramas ofrecidos de todas las sesiones
	 * @return the numGlobalFotOfrecidos
	 */
	public int getNumGlobalFotOfrecidos() {
		return numGlobalFotOfrecidos;
	}

	/**
	 * Añade un fotograma al total de fotogramas ofrecidos
	 * @param numGlobalFotOfrecidos
	 */
	public void setNumGlobalFotOfrecidos(int numGlobalFotOfrecidos) {
		this.numGlobalFotOfrecidos = this.numGlobalFotOfrecidos + numGlobalFotOfrecidos;
	}

	/**
	 * Devuelve el nñumero de fotogramas acertados en la sesión de juego
	 * @return the numAciertosSesion
	 */
	public int getNumAciertosSesion() {
		return numAciertosSesion;
	}

	/**
	 * Añade un un fotograma acertado a la sesión y al total 
	 * @param numAciertosSesion
	 */
	public void setNumAciertosSesion(int numAciertosSesion) {
		this.numAciertosSesion = this.numAciertosSesion + numAciertosSesion;
		setNumGlobalAciertos(numAciertosSesion);
	}

	/**
	 * Devuelve el número de fotogramas ofrecidos en la sesión de juego
	 * @return the numFotOfrecidosSesion
	 */
	public int getNumFotOfrecidosSesion() {
		return numFotOfrecidosSesion;
	}

	/**
	 * Suma un fotograma a la sesión de juego y al total
	 * @param numFotOfrecidosSesion
	 */
	public void setNumFotOfrecidosSesion(int numFotOfrecidosSesion) {
		this.numFotOfrecidosSesion = this.numFotOfrecidosSesion + numFotOfrecidosSesion;
		setNumGlobalFotOfrecidos(numFotOfrecidosSesion);
	}

}
