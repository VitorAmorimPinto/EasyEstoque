/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.ProdutoPedido;

/**
 *
 * @author vitor.pinto
 */
public interface PedidoProdutoDao {
      public void insert (ProdutoPedido produtoPedido) throws Exception;  
      public void delete (ProdutoPedido produtoPedido) throws Exception;  

}
