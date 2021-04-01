/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pizzaria.modelo;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Titione
 */

@Entity
@Table(name = "fornecedor")
public class Fornecedor extends Pessoa{

    @Column(name = "cnpj")
    private String cnpj;
    
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;
    
    

    public Fornecedor() {
    }

    public Fornecedor(Long id, String nome, String email, String telefone, String cnpj, String inscricaoEstadual) {
        super(id, nome, email, telefone);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    

    
    
}
