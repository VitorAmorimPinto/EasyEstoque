/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Mercado;
import modelo.Pessoa;

/**
 *
 * @author Vitor
 */
public interface PessoaDao {
   public void insert (Pessoa pessoa) throws Exception;
   public void update (Pessoa pessoa)throws Exception;
}
