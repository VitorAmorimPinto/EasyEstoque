/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Vitor
 */
public class Baixa {
 public Date dataBaixa;
 public int quantidade;
 public String motivo;
 public Produto produto;

 public Baixa() {
 }

    public Baixa(Date dataBaixa, int quantidade, String motivo, Produto produto) {
        this.dataBaixa = dataBaixa;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Baixa{" + "dataBaixa=" + dataBaixa + ", quantidade=" + quantidade + ", motivo=" + motivo + ", produto=" + produto + '}';
    }

  
}
