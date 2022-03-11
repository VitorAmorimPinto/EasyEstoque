/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Item;
import modelo.Lote;
import modelo.Produto;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.filters.ObjectFilters;
import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 *
 * @author Priscilaa
 */
public class ProdutoDaoImpl implements ProdutoDao {

    @Override
    public void adicionar(Produto produto) throws Exception {
        BaseDados.rProdutos.insert(produto);
    }

    @Override
    public List<Produto> visualizar() throws Exception {
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        List<Produto> produtos = BaseDados.rProdutos.find(opcoes).toList();
        return produtos;
    }

    @Override
    public Produto pesquisarPorId(NitriteId produtoId) throws Exception {
        return BaseDados.rProdutos.getById(produtoId);
    }

    @Override
    public List<Produto> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        List<Produto> produtos = BaseDados.rProdutos.find(ObjectFilters.regex("nome", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
        
        switch (parametro) {
            case "Nome":
                produtos = BaseDados.rProdutos.find(ObjectFilters.regex("nome", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Marca":
                produtos = BaseDados.rProdutos.find(ObjectFilters.regex("marca.marca", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
        }
        
        return produtos;
    }

    @Override
    public List<Produto> produtoExiste(Produto produto) throws Exception {
        List<Produto> produtoExiste = BaseDados.rProdutos.find(and(ObjectFilters.regex("nome", "(?i)(" + produto.nome + ")"),eq("marca.marca", produto.marca.marca))).toList();
        return produtoExiste;
    }

    @Override
    public void atualizar(Produto produto, List<Item> itensAtualizar, List<Lote> lotesAtualizar) throws Exception {
        
        for (Item i : itensAtualizar) {
            i.produto = produto;
            BaseDados.rItens.update(i);
        }
        
        for (Lote l : lotesAtualizar) {
            l.item.produto = produto;
            BaseDados.rLotes.update(l);
        }
        
        BaseDados.rProdutos.update(produto);
    }

    @Override
    public void excluir(Produto produto) throws Exception {
        BaseDados.rProdutos.remove(produto);
    }

}
