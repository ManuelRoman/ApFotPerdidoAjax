package pr04.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.Concurso;
import pr04.modelo.beans.DatosJuego;
import pr04.modelo.beans.Fotacertado;
import pr04.modelo.beans.FotacertadoPK;
import pr04.modelo.beans.Fotograma;
import pr04.modelo.beans.Ranking;
import pr04.modelo.beans.Usuario;

/**
 * Encapsula los procesos de inserción y actualización con la base de datos.
 */
public class BeanDaoInsercionImpl extends BeanDaoConexionImpl implements BeanDaoInsercion{

	public BeanDaoInsercionImpl(String unidadPersistencia) {
		super(unidadPersistencia);
	}

	/**
	 * Proceso que inserta un usuario en la base de datos, en la tabla usuarios, fotacertado y concurso
	 * @param usuario, el usuario a insertar
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws EntityExistsException
	 * @throws RollbackException
	 * @throws BeanError
	 */
	@Override
	public void insertaUsuario(Usuario usuario) throws Exception, IllegalStateException, RollbackException, EntityExistsException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		usuario.setConcurso(new Concurso(usuario.getLogin()));
		usuario.setRanking(new Ranking(usuario.getLogin()));
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(usuario);
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
			}
			if (conexionNula) {
				close();
			}
		}
		
		
	}

	/**
	 * Proceso que inserta un usuario en la tabla que almacena el estado (acertados o no) de un fotograma dado para un usuario
	 * @param login, del usuario
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws EntityExistsException
	 * @throws RollbackException
	 */
	@Override
	public void insertaFotAcertados(String login) throws Exception, IllegalStateException, EntityExistsException, RollbackException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		int numFotogramas = 0;
		Query query = null;
		Fotacertado fotacertado = null;
		EntityTransaction tx = em.getTransaction();
		try {
			query = em.createNamedQuery(Fotograma.CONTAR_FOTOGRAMAS);
			numFotogramas = Integer.parseInt(query.getSingleResult().toString());
			
			tx.begin();
			for(int i=1; i<=numFotogramas; i++){
				fotacertado = new Fotacertado(new FotacertadoPK(login, i-1));
				em.persist(fotacertado);
			}
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
			}
			if (conexionNula) {
				close();
			}
		}
		
	}

	/**
	 * Actualiza el estado de un fotograma, pasaondo de false a true, cuando es acertado
	 * @param idFotAcertado, del fotograma
	 * @param login, del usuario
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws RollbackException
	 */
	@Override
	public void actualizaFotAcertado(int idFotAcertado, String login) throws Exception, IllegalStateException, IllegalArgumentException, RollbackException {
		boolean conexionNula = false;
		Fotacertado fotacertado = null;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
		    fotacertado = em.find(Fotacertado.class, new FotacertadoPK(login, idFotAcertado));
		    fotacertado.setAcertado((byte) 1);
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
			}
			if (conexionNula) {
				close();
			}
		}	
	}

	/**
	 * Proceso que almacena los nuevos datos del juego del usuario 
	 * @param usuario, usuario que está jugando
	 * @param datosJuego, del juego actual
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws RollbackException
	 */
	@Override
	public void actualizaDatosJuego(Usuario usuario, DatosJuego datosJuego) throws Exception, IllegalStateException, IllegalArgumentException, RollbackException {
		boolean conexionNula = false;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		Concurso concurso = null;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			concurso = em.find(Concurso.class, usuario.getLogin());
		    concurso.setNumGlobalFotOfrecidos(datosJuego.getNumGlobalFotOfrecidos());
		    concurso.setNumGlobalAciertos(datosJuego.getNumGlobalAciertos());;
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
			}
			if (conexionNula) {
				close();
			}
		}	
	}

	/**
	 * Actualiza el ranking del usuario
	 * @param usuario
	 * @param datosJuego, está la nueva puntuación.
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws RollbackException
	 */
	@Override
	public void actualizaRanking(Usuario usuario, DatosJuego datosJuego) throws Exception, IllegalStateException, IllegalArgumentException, RollbackException {
		boolean conexionNula = false;
		Ranking ranking = null;
		if (em == null) {
			getConexion();
			conexionNula = true;
		}
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			ranking = em.find(Ranking.class, usuario.getRanking().getLogin());
			ranking.setPuntos(datosJuego.getPuntosGlobales());
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
			}
			if (conexionNula) {
				close();
			}
		}	
		
	}

}
