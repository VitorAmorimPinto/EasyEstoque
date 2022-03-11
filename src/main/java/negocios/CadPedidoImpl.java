/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Pedido;
import modelo.Produto;
import org.dizitart.no2.FindOptions;
import persistencia.PedidoDao;
import persistencia.PedidoDaoImpl;
import persistencia.ProdutoDao;
import persistencia.ProdutoDaoImpl;

/**
 *
 * @author vitor.pinto
 */
public class CadPedidoImpl implements CadPedido {
    PedidoDao pedidoDao = new PedidoDaoImpl();
    @Override
    public void adicionar(Pedido pedido) throws Exception {
        pedidoDao.insert(pedido);
    }
    @Override
    public void atualizar(Pedido pedido) throws Exception {
        pedidoDao.update(pedido);
    }

    @Override
    public List<Pedido> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
      return pedidoDao.pesquisarParametro(parametro, ordem, valorPesquisa);
    }
}