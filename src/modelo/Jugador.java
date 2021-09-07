/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author guill
 */
public class Jugador {
    
    private int puntaje;
    private String nickname;
    private int puntajeRonda;

    public Jugador(String nickname) {
        this.nickname = nickname;
        puntaje = 0;
        puntajeRonda = 0;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    public void aumentarPuntaje(int puntaje){
        this.puntaje+=puntaje;
        this.puntajeRonda+=puntaje;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPuntajeRonda() {
        return puntajeRonda;
    }

    public void setPuntajeRonda(int puntajeRonda) {
        this.puntajeRonda = puntajeRonda;
    }

    @Override
    public String toString() {
        return "Jugador{" + "nickname=" + nickname + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.nickname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Jugador other = (Jugador) obj;
        return Objects.equals(this.nickname, other.nickname);
    }
    
    

}
