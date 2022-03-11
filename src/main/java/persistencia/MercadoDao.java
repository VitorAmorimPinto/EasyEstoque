/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Marca;
import modelo.Mercado;
import org.dizitart.no2.FindOptions;

/**
 *
 * @author vitor.pinto
 */
public interface MercadoDao {
  public void insert (Mercado mercado) throws Exception;
  public void update (Mercado mercado)throws Exception;
   public List<Mercado> visualizar (FindOptions opcao) throws Exception;
}
