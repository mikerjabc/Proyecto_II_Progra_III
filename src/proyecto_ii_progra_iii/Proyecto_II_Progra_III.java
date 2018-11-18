package proyecto_ii_progra_iii;

import Control.ControllerLogin;
import Modelo.ModeloLogin;
import Vista.VistaLogin;
import java.sql.SQLException;
 
public class Proyecto_II_Progra_III{

    
    public static void main(String[] args) throws SQLException {

        VistaLogin vista = new VistaLogin();
        
        ControllerLogin control = new ControllerLogin(new ModeloLogin(), vista);
        
        vista.setVisible(true);
        
        //Jose
    }

}