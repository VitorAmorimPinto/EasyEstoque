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

/**
 *
 * @author Priscilaa
 */
public interface ItemDao {
    //Cadastrar
    public void adicionar(Item item) throws Exception;
    //Encontrar um item específico
    public List<Item> pesquisar(Item item) throws Exception;
    //Visualizar itens
    public List<Item> visualizar() throws Exception;
    //Selecionar item na tabela de cadastro de lote
    public Item pesquisarPorId (NitriteId itemId) throws Exception;
    //Filtrar tabela
    public List<Item> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
    //Atualiza os itens
    public void atualizar(Item item, List<Lote> lotesAtualizar) throws Exception;
    //Apaga o item
    public void excuir(Item item) throws Exception;
}
