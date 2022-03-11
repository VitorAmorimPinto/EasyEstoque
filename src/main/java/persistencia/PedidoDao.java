/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Pedido;
import modelo.Produto;
import org.dizitart.no2.FindOptions;

/**
 *
 * @author vitor.pinto
 */
public interface PedidoDao {
  public void insert (Pedido pedido) throws Exception;
  public void update (Pedido pedido)throws Exception;
   public List<Pedido> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
}
