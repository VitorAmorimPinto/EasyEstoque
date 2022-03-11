/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

/**
 *
 * @author Vitor
 */
public class Usuario{
    @Id
    public NitriteId id;
    public String usuario,senha;
    public Boolean administrador;
    public Pessoa pessoa;

    public Usuario() {
    }

    public Usuario(String usuario, String senha, Boolean administrador, Pessoa pessoa) {
        this.usuario = usuario;
        this.senha = senha;
        this.administrador = administrador;
        this.pessoa = pessoa;
    }

    
    
}
