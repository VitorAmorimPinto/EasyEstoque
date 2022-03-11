/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Mercado;
import org.dizitart.no2.FindOptions;
import persistencia.MercadoDao;
import persistencia.MercadoDaoImpl;

/**
 *
 * @author vitor.pinto
 */
public class CadMercadoImpl implements CadMercado{
    MercadoDao mercadoDao = new MercadoDaoImpl();
    @Override
    public void adicionar(Mercado mercado) throws Exception {
        mercadoDao.insert(mercado);
    }

    @Override
    public void atualizar(Mercado mercado) throws Exception {
        mercadoDao.update(mercado);
    }

    @Override
    public List<Mercado> visualizar(FindOptions opcao) throws Exception {
        return mercadoDao.visualizar(opcao);
    }
    
}
