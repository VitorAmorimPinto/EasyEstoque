/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Baixa;
import org.dizitart.no2.objects.filters.ObjectFilters;

/**
 *
 * @author Priscilaa
 */
public class BaixaDaoImpl implements BaixaDao{

    @Override
    public void adicionar(Baixa baixa) throws Exception {
        BaseDados.rBaixas.insert(baixa);
    }

    @Override
    public List<Baixa> visualizar(String tipoPesquisa) throws Exception {
        
        List<Baixa> baixas = BaseDados.rBaixas.find().toList();
        
        if(tipoPesquisa.equals("Todos os motivos")){
            baixas = BaseDados.rBaixas.find().toList();
        }else{
            baixas = BaseDados.rBaixas.find(ObjectFilters.regex("motivo", ".*(?i)(" + tipoPesquisa + ").*")).toList();   
        }
        
        return baixas;
    }
    
}
