/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

/**
 *
 * @author Vitor
 */
@Indices({
 @Index(value = "cpf", type = IndexType.Unique)
})
public class Pessoa {
    @Id
    public NitriteId id;
    public String nome,sobrenome,cpf;
    public int idade;
    

    public Pessoa() {
    }
    
    public Pessoa( String nome, String sobrenome, String cpf, int idade) {
       
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", cpf=" + cpf + ", idade=" + idade + '}';
    }
    
    
}
