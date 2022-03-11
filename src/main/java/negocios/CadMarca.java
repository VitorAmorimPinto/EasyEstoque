/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Marca;
import org.dizitart.no2.FindOptions;
/**
 *
 * @author Priscilaa
 */
public interface CadMarca {
    //Cadastrar
    public void adicionar(Marca marca) throws Exception;
    //Visualizar todas as marcas
     public List<Marca> visualizar(FindOptions opcao) throws Exception;
     //Pesquisar uma marca pelo nome
     public List<Marca> pesquisar(String nomeMarca, FindOptions opcao) throws Exception;
     //Verifica se a marca existe
     public List<Marca> marcaExiste(Marca marca) throws Exception;
}
