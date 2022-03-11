/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Lote;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import persistencia.LoteDao;
import persistencia.LoteDaoImpl;

/**
 *
 * @author Priscilaa
 */
public class CadLoteImpl implements CadLote{
    
    LoteDao loteDao = new LoteDaoImpl();
    
    @Override
    public void adicionar(Lote lote) throws Exception {
        loteDao.adicionar(lote);
    }

    @Override
    public Lote pesquisarPorId(NitriteId loteId) throws Exception {
        return loteDao.pesquisarPorId(loteId);
    }

    @Override
    public List<Lote> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        return loteDao.pesquisarParametro(parametro, ordem, valorPesquisa);
    }

    @Override
    public List<Lote> visualizar() throws Exception {
        return loteDao.visualizar();
    }

    @Override
    public List<Lote> loteExiste(Lote lote) throws Exception {
        return loteDao.loteExiste(lote);
    }

    @Override
    public void atualizar(Lote lote) throws Exception {
        loteDao.atualizar(lote);
    }

    @Override
    public void excluir(Lote lote) throws Exception {
        loteDao.excluir(lote);
    }
    
}
