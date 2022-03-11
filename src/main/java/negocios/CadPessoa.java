/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import modelo.Mercado;
import modelo.Pessoa;

/**
 *
 * @author Vitor
 */
public interface CadPessoa {
  public void adicionar(Pessoa pessoa) throws Exception;
  public void atualizar (Pessoa pessoa)throws Exception;
}
