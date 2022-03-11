/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Marca;
import org.dizitart.no2.FindOptions;
import persistencia.MarcaDao;
import persistencia.MarcaDaoImpl;

/**
 *
 * @author Priscilaa
 */
public class CadMarcaImpl implements CadMarca {

    MarcaDao marcaDao = new MarcaDaoImpl();
    
    @Override
    public void adicionar(Marca marca) throws Exception {
        marcaDao.adicionar(marca);
    }

    @Override
    public List<Marca> visualizar(FindOptions opcao) throws Exception {
       return marcaDao.visualizar(opcao);
    }

    @Override
    public List<Marca> pesquisar(String nomeMarca, FindOptions opcao) throws Exception {
        return marcaDao.pesquisar(nomeMarca, opcao);
    }

    @Override
    public List<Marca> marcaExiste(Marca marca) throws Exception {
        return marcaDao.marcaExiste(marca);
    }
    
}
