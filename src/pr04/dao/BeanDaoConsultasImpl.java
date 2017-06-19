package pr04.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.Fotacertado;
import pr04.modelo.beans.FotacertadoPK;
import pr04.modelo.beans.Fotograma;
import pr04.modelo.beans.Ranking;
import pr04.modelo.beans.Usuario;

/**
 * Encapsula las consultas con la base de datos
 */
public class BeanDaoConsultasImpl extends BeanDaoConexionImpl implements BeanDaoConsultas{
	
	/**
	 * Constructor recibe la unidad e persistencia y llama la clase padre (BeanDaoConsultas)
	 * @param unidadPersistencia
	 */
	public BeanDaoConsultasImpl(String unidadPersistencia){
		super(unidadPersistencia);
	}

	/**
	 * Método que obtiene los datos del usuario de la base de datos
	 * @param login, el login del usuario
	 * @param clave, la clave del usuario
	 * @return Usuario, el usuario
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws BeanError
	 */
	@Override
	public Usuario getUsuario(String login, String clave)
			throws Exception, IllegalStateException, IllegalArgumentException, BeanError {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		Usuario usuario = new Usuario();
		try {
			usuario = em.find(Usuario.class, login);
			if (usuario != null ){
				if(!usuario.getClave().equals(clave)){
					throw new BeanError(2, "La clave no coincide.");
				}
			} else {
				throw new BeanError(1, "El login no existe.");
			} 
		} finally {
			if (conexionNula) {
				close();
			}
		}
		return usuario;
	}

	/**
	 * Método que comprueba si un login/nombre de usuario, ya está registrado.
	 * @param login, el login que se quiere registrar
	 * @return, true o false
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean existeUsuario(String login) throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		boolean existe = false;
		Usuario usuario = new Usuario();
		try {
			usuario = em.find(Usuario.class, login);
			if (usuario != null ){
				existe = true;
			} 
		} finally {
			if (conexionNula) {
				close();
			}
		}
		return existe;
	}

	/**
	 * Método que obtiene el ranking de los 10 usuarios con la puntuación más alta.
	 * @return List<Ranking>
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public List<Ranking> getRanking10() throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		List<Ranking> listaRanking = new ArrayList();
		try {
			Query query = em.createNamedQuery(Ranking.TODOS);
			listaRanking = query.setMaxResults(10).getResultList();
		} finally {
			if (conexionNula) {
				close();
			}
		}
		return listaRanking;
	}

	/**
	 * Método que devuelve los id de todos los fotogramas de la base de datos.
	 * @return List<Integer> con todos los id
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public List<Integer> getIdFotogramas() throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		List<Integer> listaIdFotogramas = new ArrayList<Integer>();
		Query query = null;
		try {
			query = em.createNamedQuery(Fotograma.BUSCAR_TODAS_IDS);
			listaIdFotogramas = query.getResultList();
		} finally {
			if (conexionNula) {
				close();
			}
		}
		return listaIdFotogramas;
	}

	/**
	 * Método que devuelve la lista de los id de los fotogramas no acertados del usuario
	 * @param login, del usuario
	 * @param numFotSesion, cantidad de fotogramas que tendrá la sesión de juego
	 * @return List<Integer>, la lista con los ids de  los fotogramas
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public List<Integer> listaFotNoAcertados(String login, int numFotSesion)
			throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		List<Integer> listaFotAcertados = new ArrayList<Integer>();
		Fotacertado fotacertado = new Fotacertado();
		Query query = null;
		try {
			fotacertado = em.find(Fotacertado.class, new FotacertadoPK(login));
			if (fotacertado!= null){
				String sql = "SELECT idFotograma FROM fotacertados WHERE login =:nombre AND acertado = false";
				query = em.createNativeQuery(sql);
				query.setParameter("nombre", login);
				listaFotAcertados = query.setMaxResults(numFotSesion).getResultList();
			} else {
				
				listaFotAcertados = null;
			}
		} finally {
			if (conexionNula) {
				close();
			}
		}
		
		return listaFotAcertados;
	}

	/**
	 * Obtiene el título de la película de un fotograma
	 * @param idFotograma, del fotograma
	 * @return String, con el nombre de la película
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public String obtenerTituloFotograma(int idFotograma)
			throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		String titulo = "";
		Fotograma fotograma = new Fotograma();
		try {
			fotograma = em.find(Fotograma.class, idFotograma);
			titulo = fotograma.getTitPelicula(); 
		} finally {
			if (conexionNula) {
				close();
			}
		}
		return titulo;
	}

	/**
	 * Devuelve un objeto fotograma.
	 * @param idFotograma, id del fotograma
	 * @return Fotograma
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public Fotograma obtenerFotograma(int idFotograma)
			throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		Fotograma fotograma = new Fotograma();
		try {
			fotograma = em.find(Fotograma.class, idFotograma); 
		} finally {
			if (conexionNula) {
				close();
			}
		}
		return fotograma;
	}

	/**
	 * Obtiene la posición de un usuario en el ranking de puntuación
	 * @param usuario, usuario a buscar
	 * @return Integer con la posición.
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	@Override
	public Integer getPosicion(Usuario usuario) throws Exception, IllegalStateException, IllegalArgumentException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		Integer posicion = 0;
		List<Ranking> listaRanking = new ArrayList();
		try {
			Query query = em.createNamedQuery(Ranking.TODOS);
			listaRanking = query.getResultList();
		} finally {
			if (conexionNula) {
				close();
			}
		}
		posicion = listaRanking.indexOf(usuario.getRanking())+1;
		return posicion;
	}

}
