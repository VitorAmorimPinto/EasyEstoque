/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import modelo.ProdutoPedido;

/**
 *
 * @author vitor.pinto
 */
public interface CadPedidoProduto {
    public void adicionar(ProdutoPedido produtoPedido) throws Exception;
    public void delete(ProdutoPedido produtoPedido) throws Exception;

}
