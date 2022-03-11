/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.ProdutoFLV;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;

/**
 *
 * @author Priscilaa
 */
public interface ProdutoFLVDao {
    //Cadastrar
    public void adicionar (ProdutoFLV produtoFLV) throws Exception;
    //Visualizar
    public List<ProdutoFLV> visualizar (FindOptions opcao) throws Exception;
    //Pesquisar
    public List<ProdutoFLV> pesquisar(String valorPesquisa, String tipoPesquisa, FindOptions opcao) throws Exception;
    //Verifica se o produtoFLV existe pelo código
    public List<ProdutoFLV> produtoFLVExiste(ProdutoFLV produtoFLV) throws Exception;
    //Verifica se o produtoFLV existe pelo nome
    public List<ProdutoFLV> produtoFLVExisteNome(ProdutoFLV produtoFLV) throws Exception;
    //Atualiza as informações do produto
    public void atualizar(ProdutoFLV produtoFLV) throws Exception;
    //Pesquisa por ID
    public ProdutoFLV pesquisarPorId(NitriteId id) throws Exception;
    //Apaga o produto FLV
    public void excluir(ProdutoFLV produtoFLV) throws Exception;
}
