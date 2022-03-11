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

/**
 *
 * @author Priscilaa
 */
public interface CadItem {
    //Método para cadastrar item
    public void adicionar(Item item) throws Exception;
    //Método para pesquisar a existência de um item específico
    public List<Item> pesquisar(Item item) throws Exception;
    //Método para visualizar os itens no cadastro do lote
    public List<Item> visualizarItens() throws Exception;
    //Método utilizado na seleção de um item da tabela no cadastro de lotes
    public Item pesquisarPorId (NitriteId itemId) throws Exception;
    //Método utilizado para filtrar tabelas
    public List<Item> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
    //Atualiza os itens
    public void atualizar(Item item, List<Lote> lotesAtualizar) throws Exception;

    public void excluir(Item item) throws Exception;
}
