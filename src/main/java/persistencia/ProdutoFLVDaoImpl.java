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
import org.dizitart.no2.objects.filters.ObjectFilters;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 *
 * @author Priscilaa
 */
public class ProdutoFLVDaoImpl implements ProdutoFLVDao{
    public void adicionar(ProdutoFLV produtoFLV) throws Exception {
        BaseDados.rProdutosFLV.insert(produtoFLV);
    }

    @Override
    public List<ProdutoFLV> visualizar(FindOptions opcao) throws Exception {
        List<ProdutoFLV> produtosFLV = BaseDados.rProdutosFLV.find(opcao).toList();
        return produtosFLV;
    }

    @Override
    public List<ProdutoFLV> pesquisar(String valorPesquisa, String tipoPesquisa, FindOptions opcao) throws Exception {
        
        List<ProdutoFLV> produtosFLV = BaseDados.rProdutosFLV.find(ObjectFilters.regex("nome", "(?i).*(" + valorPesquisa + ").*"),opcao).toList();
        
        switch(tipoPesquisa){
           case"Nome":
            produtosFLV = BaseDados.rProdutosFLV.find(ObjectFilters.regex("nome", "(?i).*(" + valorPesquisa + ").*"),opcao).toList();
            break;
            case"Código":
            produtosFLV = BaseDados.rProdutosFLV.find(ObjectFilters.regex("codigo", "(?i).*(" + valorPesquisa + ").*"),opcao).toList();
            break;
       }
        
        return produtosFLV;
    }

    @Override
    public List<ProdutoFLV> produtoFLVExiste(ProdutoFLV produtoFLV) throws Exception {
        List<ProdutoFLV> produtoFLVExiste = BaseDados.rProdutosFLV.find(eq("codigo", produtoFLV.codigo)).toList();
        return produtoFLVExiste;
    }

    @Override
    public List<ProdutoFLV> produtoFLVExisteNome(ProdutoFLV produtoFLV) throws Exception {
        List<ProdutoFLV> produtoFLVExiste = BaseDados.rProdutosFLV.find(ObjectFilters.regex("nome", "(?i)(" + produtoFLV.nome + ")")).toList();
        return produtoFLVExiste;
    }

    @Override
    public void atualizar(ProdutoFLV produtoFLV) throws Exception {
        BaseDados.rProdutosFLV.update(produtoFLV);
    }

    @Override
    public ProdutoFLV pesquisarPorId(NitriteId id) throws Exception {
        return BaseDados.rProdutosFLV.getById(id);
    }

    @Override
    public void excluir(ProdutoFLV produtoFLV) throws Exception {
        BaseDados.rProdutosFLV.remove(produtoFLV);
    }
}
