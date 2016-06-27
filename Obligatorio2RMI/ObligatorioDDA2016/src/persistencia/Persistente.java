/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Moi
 */
public interface Persistente {
    public int getOid();
    public void setOid(int oid);

    public ArrayList<String> getSqlInsert();
    public ArrayList<String> getSqlUpdate();
    public ArrayList<String> getSqlDelete();
    public String getSqlSelect();

    public void leer(ResultSet rs);

    public void crearNuevo();

    public Object getObjeto();
}
