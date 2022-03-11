/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Marca;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.objects.filters.ObjectFilters;

/**
 *
 * @author Priscilaa
 */
public class MarcaDaoImpl implements MarcaDao{

    @Override
    public void adicionar(Marca marca) throws Exception {
       BaseDados.rMarcas.insert(marca);
    }

    @Override
    public List<Marca> visualizar(FindOptions opcao) throws Exception {
        List<Marca> marcas = BaseDados.rMarcas.find(opcao).toList();
        return marcas;
    }

    @Override
    public List<Marca> pesquisar(String nomeMarca, FindOptions opcao) throws Exception {
        List<Marca> marcas = BaseDados.rMarcas.find(ObjectFilters.regex("marca", ".*(?i)(" + nomeMarca + ").*"), opcao).toList();
        return marcas;
    }

    @Override
    public List<Marca> marcaExiste(Marca marca) throws Exception {
        List<Marca> marcaExiste = BaseDados.rMarcas.find(ObjectFilters.regex("marca", "(?i)(" + marca.marca + ")")).toList();
        return marcaExiste;
    }
    
}
