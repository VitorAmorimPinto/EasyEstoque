/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Pedido;
import org.dizitart.no2.FindOptions;

/**
 *
 * @author vitor.pinto
 */
public interface CadPedido {
   public void adicionar(Pedido pedido) throws Exception;
   public void atualizar (Pedido pedido)throws Exception;
   public List<Pedido> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
}
