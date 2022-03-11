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
import org.dizitart.no2.objects.filters.ObjectFilters;
import persistencia.BaseDados;
import persistencia.PedidoDao;

/**
 *
 * @author vitor.pinto
 */
public class PedidoDaoImpl implements PedidoDao {

    @Override
    public void insert(Pedido pedido) throws Exception {
        BaseDados.rPedidos.insert(pedido);
    }
    
    public void update(Pedido pedido) throws Exception {
        BaseDados.rPedidos.update(pedido);
    }
    
    public List<Pedido> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        List<Pedido> pedidos = BaseDados.rPedidos.find(ObjectFilters.regex("fornecedor", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
        
        switch (parametro) {
            case "Fornecedor":
                pedidos = BaseDados.rPedidos.find(ObjectFilters.regex("fornecedor", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Id Pedido":
                pedidos = BaseDados.rPedidos.find(ObjectFilters.regex("idPedido", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Status":
            pedidos = BaseDados.rPedidos.find(ObjectFilters.regex("status", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
            break;
        }
        
        return pedidos;
    }
    
}
