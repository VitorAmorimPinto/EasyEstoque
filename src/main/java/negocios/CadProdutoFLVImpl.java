/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.ProdutoFLV;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import persistencia.ProdutoFLVDao;
import persistencia.ProdutoFLVDaoImpl;

/**
 *
 * @author Priscilaa
 */
public class CadProdutoFLVImpl implements CadProdutoFLV{
    
    ProdutoFLVDao produtoFLVDao = new ProdutoFLVDaoImpl();
    
    @Override
    public void adicionar(ProdutoFLV produtoFLV) throws Exception {
        produtoFLVDao.adicionar(produtoFLV);
    }

    @Override
    public List<ProdutoFLV> visualizar(FindOptions opcao) throws Exception {
        return produtoFLVDao.visualizar(opcao);
    }

    @Override
    public List<ProdutoFLV> pesquisar(String valorPesquisa, String tipoPesquisa, FindOptions opcao) throws Exception {
        return produtoFLVDao.pesquisar(valorPesquisa, tipoPesquisa, opcao);
    }

    @Override
    public List<ProdutoFLV> produtoFLVExiste(ProdutoFLV produtoFLV) throws Exception {
        return produtoFLVDao.produtoFLVExiste(produtoFLV);
    }

    @Override
    public List<ProdutoFLV> produtoFLVExisteNome(ProdutoFLV produtoFLV) throws Exception {
        return produtoFLVDao.produtoFLVExisteNome(produtoFLV);

    }

    @Override
    public void atualizar(ProdutoFLV produtoFLV) throws Exception {
        produtoFLVDao.atualizar(produtoFLV);
    }

    @Override
    public ProdutoFLV pesquisarPorId(NitriteId id) throws Exception {
        return produtoFLVDao.pesquisarPorId(id);
    }

    @Override
    public void excluir(ProdutoFLV produtoFLV) throws Exception {
        produtoFLVDao.excluir(produtoFLV);
    }
}
