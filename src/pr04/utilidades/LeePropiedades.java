package pr04.utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * Encapsula el precoceso de leer de un archivo
 */
public abstract class LeePropiedades {
	
	/**
	 * Lee un archivo de propiedades
	 * @param fichero, el fichero a leer
	 * @return Properties
	 * @throws IOException, FileNotFoundException 
	 */
	public static Properties getPropiedades(String fichero) throws IOException, FileNotFoundException {
		Properties propiedades = new Properties();
		FileInputStream entrada = null;
		entrada = new FileInputStream(fichero);
		propiedades.load(entrada);
		return propiedades;
	}

}
