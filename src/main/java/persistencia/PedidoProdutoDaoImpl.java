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
public class PedidoProdutoDaoImpl implements PedidoProdutoDao {

    @Override
    public void insert(ProdutoPedido produtoPedido) throws Exception {
        BaseDados.rPedidoProduto.insert(produtoPedido);
    }

    @Override
    public void delete(ProdutoPedido produtoPedido) throws Exception {
        BaseDados.rPedidoProduto.remove(produtoPedido);
    }
    
    
}
