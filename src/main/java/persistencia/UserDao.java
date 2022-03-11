/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Usuario;
import org.dizitart.no2.FindOptions;

/**
 *
 * @author Vitor
 */
public interface UserDao {
    public void insert(Usuario usuario) throws Exception;
    public void update (Usuario usuario)throws Exception;
    public List<Usuario> visualizar(FindOptions opcao) throws Exception;
}
