/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Vitor
 */
public class NotaFiscal {
    public double valorTotal;
    public int quantidade;
    public String emissor,destinatario;
    public Mercado mercado;

    public NotaFiscal() {
    }

    public NotaFiscal(double valorTotal, int quantidade, String emissor, String destinatario) {
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
        this.emissor = emissor;
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "NotaFiscal{" + "valorTotal=" + valorTotal + ", quantidade=" + quantidade + ", emissor=" + emissor + ", destinatario=" + destinatario + ", mercado=" + mercado + '}';
    }

    
}
