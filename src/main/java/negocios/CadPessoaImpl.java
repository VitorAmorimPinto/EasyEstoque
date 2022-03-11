/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import modelo.Pessoa;
import persistencia.PessoaDao;
import persistencia.PessoaDaoImpl;

/**
 *
 * @author Vitor
 */
public class CadPessoaImpl implements CadPessoa{
    PessoaDao pessoaDao = new PessoaDaoImpl();
    @Override
    public void adicionar(Pessoa pessoa) throws Exception {
       pessoaDao.insert(pessoa);
    }

    @Override
    public void atualizar(Pessoa pessoa) throws Exception {
      pessoaDao.update(pessoa);
    }
    
}
