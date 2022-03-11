/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Baixa;

/**
 *
 * @author Priscilaa
 */
public interface CadBaixa {
    //Adiciona a baixa no banco
    public void adicionar(Baixa baixa) throws Exception;
    //Visualizar as baixas existentes
    public List<Baixa> visualizar(String tipoPesquisa) throws Exception;
}
