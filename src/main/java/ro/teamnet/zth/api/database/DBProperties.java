package ro.teamnet.zth.api.database;

/**
 * Created by Raluca.Russindilar on 02.07.2016.
 */
public interface DBProperties {
    //TODO de inlocuit cu IP-ul din Docker
    String IP = "localhost";
    String PORT = "1521";
    //TODO de inlocuit cu utilizatorul vostru
    String USER = "sys as sysdba";
    //TODO de inlocuit cu parola voastra
    String PASS = "123@qwert";
    String SID = "xe";
    String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";

}
