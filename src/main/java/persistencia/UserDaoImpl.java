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
public class UserDaoImpl implements UserDao{

    @Override
    public void insert(Usuario usuario) throws Exception {
        BaseDados.rUsuarios.insert(usuario);
    }

    @Override
    public void update(Usuario usuario) throws Exception {
        BaseDados.rUsuarios.update(usuario);
    }

    @Override
    public List<Usuario> visualizar(FindOptions opcao) throws Exception {
        List<Usuario> usuarios = BaseDados.rUsuarios.find(opcao).toList();
        return usuarios;
    }
    
}
