/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Usuario;
import org.dizitart.no2.FindOptions;
import persistencia.UserDao;
import persistencia.UserDaoImpl;

/**
 *
 * @author Vitor
 */
public class CadUserImpl implements CadUser{
    UserDao usuarioDao = new UserDaoImpl();
    @Override
    public void adicionar(Usuario usuario) throws Exception {
        usuarioDao.insert(usuario);
    }

    @Override
    public void atualizar(Usuario usuario) throws Exception {
      usuarioDao.update(usuario);
    }

    @Override
    public List<Usuario> visualizar(FindOptions opcao) throws Exception {
        return usuarioDao.visualizar(opcao);
    }
    
    
}
