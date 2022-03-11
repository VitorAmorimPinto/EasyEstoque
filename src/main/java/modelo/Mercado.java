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
 * @author Priscilaa
 */
public class Mercado {
    @Id
    public NitriteId id;
    public String nome, endereco, telefone;

    public Mercado() {
    }

    public Mercado(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Mercado{" + "nome=" + nome + ", endereco=" + endereco + ", telefone=" + telefone + '}';
    }
    
    
}
