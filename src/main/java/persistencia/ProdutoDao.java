/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Item;
import modelo.Lote;
import modelo.Produto;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;

/**
 *
 * @author Priscilaa
 */
public interface ProdutoDao {
    //Cadastrar
    public void adicionar (Produto produto) throws Exception;
    //Visualizar os produtos
    public List<Produto> visualizar () throws Exception;
    //Selecionar um produto no cadastro de item
    public Produto pesquisarPorId (NitriteId produtoId) throws Exception;
    //Filtrar tabela
    public List<Produto> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
    //Verifica se o produto existe
    public List<Produto> produtoExiste(Produto produto) throws Exception;
    //Atualizar
    public void atualizar(Produto produto, List<Item> itensAtualizar, List<Lote> lotesAtualizar) throws Exception;
    //Apaga o produto
    public void excluir(Produto produto) throws Exception;;
}
