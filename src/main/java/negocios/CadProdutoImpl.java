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
import persistencia.ProdutoDao;
import persistencia.ProdutoDaoImpl;

/**
 *
 * @author Priscilaa
 */
public class CadProdutoImpl implements CadProduto {

    ProdutoDao produtoDao = new ProdutoDaoImpl();
    
    @Override
    public void adicionar(Produto produto) throws Exception {
        produtoDao.adicionar(produto);
    }

    @Override
    public List<Produto> visualizarProdutos() throws Exception {
        return produtoDao.visualizar();
    } 

    @Override
    public Produto pesquisarPorId(NitriteId produtoId) throws Exception {
        return produtoDao.pesquisarPorId(produtoId);
    }

    @Override
    public List<Produto> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        return produtoDao.pesquisarParametro(parametro, ordem, valorPesquisa);
    }

    @Override
    public List<Produto> produtoExiste(Produto produto) throws Exception {
        return produtoDao.produtoExiste(produto);
    }

    @Override
    public void atualizar(Produto produto, List<Item> itensAtualizar, List<Lote> lotesAtualizar) throws Exception {
        produtoDao.atualizar(produto, itensAtualizar, lotesAtualizar);
    }

    @Override
    public void excluir(Produto produto) throws Exception {
        produtoDao.excluir(produto);
    }
}
