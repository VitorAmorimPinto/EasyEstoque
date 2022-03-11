/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Item;
import modelo.Lote;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.filters.ObjectFilters;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 *
 * @author Priscilaa
 */
public class ItemDaoImpl implements ItemDao{

    @Override
    public void adicionar(Item item) throws Exception {
        BaseDados.rItens.insert(item);
    }

    @Override
    public List<Item> pesquisar(Item item) throws Exception {
        return BaseDados.rItens.find(eq("codigoDeBarras", item.codigoDeBarras)).toList();
    }

    @Override
    public List<Item> visualizar() throws Exception {
        FindOptions opcoes = FindOptions.sort("item.produto.nome", SortOrder.Ascending);
        return BaseDados.rItens.find().toList();
    }

    @Override
    public Item pesquisarPorId(NitriteId itemId) throws Exception {
        return BaseDados.rItens.getById(itemId);
    }

    @Override
    public List<Item> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        List<Item> itens = BaseDados.rItens.find(ObjectFilters.regex("produto.nome", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
        
        switch (parametro) {
            case "Nome":
                itens = BaseDados.rItens.find(ObjectFilters.regex("produto.nome", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Marca":
                itens = BaseDados.rItens.find(ObjectFilters.regex("produto.marca.marca", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Código de Barras":
                itens = BaseDados.rItens.find(ObjectFilters.regex("codigoDeBarras", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
        }
        
        return itens;
    }

    @Override
    public void atualizar(Item item, List<Lote> lotesAtualizar) throws Exception{
        for (Lote l : lotesAtualizar) {
            l.item = item;
            BaseDados.rLotes.update(l);
        }
        
        BaseDados.rItens.update(item);
    }

    @Override
    public void excuir(Item item) throws Exception {
        BaseDados.rItens.remove(item);
    }
    
}
