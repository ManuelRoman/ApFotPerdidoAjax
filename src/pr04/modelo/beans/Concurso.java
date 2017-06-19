package pr04.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entidad de persistencia para la tabla concurso de la base de datos
 */
@Entity
@Table(name="concurso")
@NamedQuery(name="Concurso.findAll", query="SELECT c FROM Concurso c")
public class Concurso implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Login del usuario
	 */
	@Id
	private String login;

	/**
	 * Total de aciertos de todas las sesiones
	 */
	private int numGlobalAciertos;

	/**
	 * Total de fotogramas ofrecidos de todas la sesiones
	 */
	private int numGlobalFotOfrecidos;

	/**
	 * bi-directional one-to-one association to Usuario
	 * el usuario al que está asociada la entidad
	 */
	@OneToOne
	@JoinColumn(name="login")
	private Usuario usuario;

	public Concurso() {
	}
	
	public Concurso(String login){
		this.login = login;
	}

	// Getters y Setters
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getNumGlobalAciertos() {
		return this.numGlobalAciertos;
	}

	public void setNumGlobalAciertos(int numGlobalAciertos) {
		this.numGlobalAciertos = numGlobalAciertos;
	}

	public int getNumGlobalFotOfrecidos() {
		return this.numGlobalFotOfrecidos;
	}

	public void setNumGlobalFotOfrecidos(int numGlobalFotOfrecidos) {
		this.numGlobalFotOfrecidos = numGlobalFotOfrecidos;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}