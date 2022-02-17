package org.escorpion;

public class Calculadora {

    private double[] numeros = { 0, 0 };

    public double[] getNumeros() {
        return numeros;
    }

    public double getNumero(int posicao){
        return this.numeros[posicao];
    }

    public void setNumeros(double numero, int posicao) {
        this.numeros[posicao] = numero;
    }

    public double somar() {
        this.numeros[0] += this.numeros[1];
        return this.numeros[0];
    }
    public double subtrair() {
        this.numeros[0] -= this.numeros[1];
        return this.numeros[0];
    }
    public double multiplicar() {
        this.numeros[0] *= this.numeros[1];
        return this.numeros[0];
    }

    public double dividir(){
        if(this.numeros[1] == 0){
            throw new RuntimeException("Não é possivel dividir por 0");
        }
        this.numeros[0] /= this.numeros[1];
        return this.numeros[0];
    }
}
