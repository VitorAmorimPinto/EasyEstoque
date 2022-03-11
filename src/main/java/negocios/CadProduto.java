/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

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
public interface CadProduto {
    //Cadastrar
    public void adicionar(Produto produto) throws Exception;
    //Visualizar
    public List<Produto> visualizarProdutos() throws Exception;
    //Selecionar produto no cadastro de item
    public Produto pesquisarPorId (NitriteId produtoId) throws Exception;
    //Filtrar a tabela de produtos
    public List<Produto> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
    //Verifica se o produto existe
    public List<Produto> produtoExiste(Produto produto) throws Exception;
    //Atualizar
    public void atualizar(Produto produto, List<Item> itensAtualizar, List<Lote> lotesAtualizar) throws Exception;
    //Apaga o produto
    public void excluir(Produto produto) throws Exception;
}
