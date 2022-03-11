/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Pessoa;

/**
 *
 * @author Vitor
 */
public class PessoaDaoImpl implements PessoaDao{

    @Override
    public void insert(Pessoa pessoa) throws Exception {
        BaseDados.rPessoas.insert(pessoa);
    }

    @Override
    public void update(Pessoa pessoa) throws Exception {
        BaseDados.rPessoas.update(pessoa); 
    }
    
    
}
