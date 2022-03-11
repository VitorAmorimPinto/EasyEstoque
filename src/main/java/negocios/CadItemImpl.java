/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Item;
import modelo.Lote;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import persistencia.ItemDao;
import persistencia.ItemDaoImpl;

/**
 *
 * @author Priscilaa
 */
public class CadItemImpl implements CadItem {
    
    ItemDao itemDao = new ItemDaoImpl();
    
    @Override
    public void adicionar(Item item) throws Exception {
        
        itemDao.adicionar(item);
    }

    @Override
    public List<Item> pesquisar(Item item) throws Exception {
        return itemDao.pesquisar(item);
    }

    @Override
    public List<Item> visualizarItens() throws Exception {
       return itemDao.visualizar();
    }

    @Override
    public Item pesquisarPorId(NitriteId itemId) throws Exception {
        return itemDao.pesquisarPorId(itemId);
    }

    @Override
    public List<Item> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        return itemDao.pesquisarParametro(parametro, ordem, valorPesquisa);
    }

    @Override
    public void atualizar(Item item, List<Lote> lotesAtualizar) throws Exception{
        itemDao.atualizar(item, lotesAtualizar);
    }

    @Override
    public void excluir(Item item) throws Exception {
        itemDao.excuir(item);
    }
    
}
