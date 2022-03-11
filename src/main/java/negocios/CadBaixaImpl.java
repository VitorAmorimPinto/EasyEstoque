/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Baixa;
import persistencia.BaixaDao;
import persistencia.BaixaDaoImpl;

/**
 *
 * @author Priscilaa
 */
public class CadBaixaImpl implements CadBaixa{

    BaixaDao baixaDao = new BaixaDaoImpl();
    
    @Override
    public void adicionar(Baixa baixa) throws Exception {
        baixaDao.adicionar(baixa);
    }

    @Override
    public List<Baixa> visualizar(String tipoPesquisa) throws Exception {
        return baixaDao.visualizar(tipoPesquisa);
    }
    
}
