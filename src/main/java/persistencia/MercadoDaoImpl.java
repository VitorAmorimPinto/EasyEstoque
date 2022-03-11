/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Mercado;
import org.dizitart.no2.FindOptions;

/**
 *
 * @author vitor.pinto
 */
public class MercadoDaoImpl implements MercadoDao{

    @Override
    public void insert(Mercado mercado) throws Exception {
        BaseDados.rMercado.insert(mercado);
    }

    @Override
    public void update(Mercado mercado) throws Exception {
        BaseDados.rMercado.update(mercado);

    }

    @Override
    public List<Mercado> visualizar(FindOptions opcao) throws Exception {
        List<Mercado> marcas = BaseDados.rMercado.find(opcao).toList();
        return marcas;
    }
    
}
