package pr04.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Entidad de persistencia para la tabla usuarios de la base de datos
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Login del usario
	 */
	@Id
	private String login;

	/**
	 * Clave del usaurio
	 */
	private String clave;

	/**
	 * bi-directional one-to-one association to Concurso
	 * Información del concurso del usuario
	 */
	@OneToOne (mappedBy="usuario", cascade=CascadeType.ALL)
	@JoinColumn(name="login", insertable=true, updatable=true)
	private Concurso concurso;

	/**
	 * bi-directional one-to-one association to Ranking
	 * Información del ranking de usaurios
	 */
	@OneToOne (mappedBy="usuario", cascade=CascadeType.ALL)
	@JoinColumn(name="login", insertable=true, updatable=true)
	private Ranking ranking;

	public Usuario() {
	}
	
	public Usuario(String login, String clave) {
		this.login = login;
		this.clave = clave;
	}

	//Getters y Setters
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Concurso getConcurso() {
		return this.concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Ranking getRanking() {
		return this.ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

}