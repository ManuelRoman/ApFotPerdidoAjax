package pr04.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Entidad de persistencia para la tabla fotogramas de la base de datos
 */
@Entity
@Table(name="fotogramas")
@NamedQueries({
@NamedQuery(name="Fotograma.findAll", query="SELECT f FROM Fotograma f"),
@NamedQuery(name="Fotograma.CountAll", query="SELECT COUNT(f) FROM Fotograma f"),
@NamedQuery(name="Fotograma.findAllId", query="SELECT f.idFotograma FROM Fotograma f")})
public class Fotograma implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * LLama a la consulta que obtiene la lista con todas la ids de los fotogramas
	 */
	public static final String BUSCAR_TODAS_IDS = "Fotograma.findAllId";
	/**
	 * Llama a la consulta que cuenta el total de fotogramas
	 */
	public static final String CONTAR_FOTOGRAMAS = "Fotograma.CountAll";

	/**
	 * Id del fotogramas
	 */
	@Id
	private int idFotograma;

	/**
	 * Año de estreno
	 */
	private int anyoEstreno;

	/**
	 * Nombre del archivo
	 */
	private String archivo;

	/**
	 * Nombre del director
	 */
	private String director;

	/**
	 * Título de la película
	 */
	private String titPelicula;

	/**
	 * Información del genero
	 * bi-directional many-to-one association to Genero
	 */
	@ManyToOne
	@JoinColumn(name="genero")
	private Genero generoBean;

	public Fotograma() {
	}

	//Getters y Setters
	public int getIdFotograma() {
		return this.idFotograma;
	}

	public void setIdFotograma(int idFotograma) {
		this.idFotograma = idFotograma;
	}

	public int getAnyoEstreno() {
		return this.anyoEstreno;
	}

	public void setAnyoEstreno(int anyoEstreno) {
		this.anyoEstreno = anyoEstreno;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getTitPelicula() {
		return this.titPelicula;
	}

	public void setTitPelicula(String titPelicula) {
		this.titPelicula = titPelicula;
	}

	public Genero getGeneroBean() {
		return this.generoBean;
	}

	public void setGeneroBean(Genero generoBean) {
		this.generoBean = generoBean;
	}

}