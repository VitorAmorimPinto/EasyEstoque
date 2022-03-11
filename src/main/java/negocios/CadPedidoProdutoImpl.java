/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import modelo.ProdutoPedido;
import persistencia.PedidoProdutoDao;
import persistencia.PedidoProdutoDaoImpl;

/**
 *
 * @author vitor.pinto
 */
public class CadPedidoProdutoImpl implements CadPedidoProduto {

    @Override
    public void adicionar(ProdutoPedido produtoPedido) throws Exception {
        PedidoProdutoDao pedidoPDao = new PedidoProdutoDaoImpl();
        pedidoPDao.insert(produtoPedido);
    }

    @Override
    public void delete(ProdutoPedido produtoPedido) throws Exception {
       PedidoProdutoDao pedidoPDao = new PedidoProdutoDaoImpl();
        pedidoPDao.delete(produtoPedido);
    }
   

    
    
   
}
