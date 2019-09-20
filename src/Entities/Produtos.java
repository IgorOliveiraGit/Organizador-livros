package Entities;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Produtos implements Serializable {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String nome;
    private double valor;
    private Date data;
    private String editora;

    public Produtos(String nome, double valor, Date data, String editora) {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.editora = editora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    @Override
    public String toString() {
        return "\nNome = " + nome  + "\n" +
                " || valor = " + "R$ " + valor + "\n" +
                " || data = " + sdf.format(data) + "\n" +
                " || editora = " + editora + "\n";
    }
}
