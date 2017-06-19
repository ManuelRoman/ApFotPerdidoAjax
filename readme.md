## Enunciado:
- Desarrollar la aplicación del fotograma perdido empleando el patrón MVC adaptado a AJAX visto en clase. La aplicación debe permitir registrar nuevos usuarios, llevar un ranking de las puntuaciones de cada jugador, consultar el ranking de los diez mejores y jugar al fotograma perdido con un número fijo de 10 fotogramas en cada juego. Si un jugador decide jugar dos veces, la segunda vez no se le presentarán los fotogramas que acertó en el primero juego.

## Nombre del datasource: 
dspr04

## Nombre de la base de datos: 
pr04

## Acceso a la base de datos: 
usuario/clave → pr04/pr04

## Se pide: 
- Proyecto en formato eclipse, que posibilite ejecutarse sobre un servidor de aplicaciones WildFly. Deberán incorporarse los contenidos dados en clase, desde la UD1 a la UD4 (scriptlets, sesiones, reenvíos, uso de los parámetros de la petición, javabeans, Jquery, AJAX, JSON, patrón MVC con AJAX visto en clase, JSTL, Datasources, Bean DAO, etc).

## Nombre del proyecto: 
PR04

## DataSource:
                <xa-datasource jndi-name="java:jboss/datasources/dspr04" pool-name="dspr4" enabled="true" use-ccm="true">
                    <xa-datasource-property name="ServerName">
                        localhost
                    </xa-datasource-property>
                    <xa-datasource-property name="DatabaseName">
                        pr04
                    </xa-datasource-property>
                    <driver>mysql5</driver>
                    <security>
                        <user-name>root</user-name>
                        <password>mysql</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
                        <background-validation>true</background-validation>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
                    </validation>
                </xa-datasource>