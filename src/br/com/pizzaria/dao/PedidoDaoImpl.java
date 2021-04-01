/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pizzaria.dao;

import br.com.pizzaria.modelo.Pedido;
import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Titione
 */
public class PedidoDaoImpl extends BaseDaoImpl<Pedido, Long> implements PedidoDao, Serializable{

    @Override
    public Pedido pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Pedido) sessao.get(Pedido.class, id);
    }

    
    
}


