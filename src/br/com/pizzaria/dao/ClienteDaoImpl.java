/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pizzaria.dao;

import br.com.pizzaria.modelo.Cliente;
import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Titione
 */
public class ClienteDaoImpl extends BaseDaoImpl<Cliente, Long> implements ClienteDao, Serializable{

    @Override
    public Cliente pesquisaPorTelefone(String telefone, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Cliente c where c.telefone like :telefone");
        consulta.setParameter("telefone", "%" + telefone + "%");
        
        return (Cliente) consulta.uniqueResult();
    }
    
    @Override
    public Cliente pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Cliente) sessao.get(Cliente.class, id);
    }

    @Override
    public List<Cliente> pesquisaPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Cliente where nome like :nome");
        consulta.setParameter("nome", "%" + nome  + "%");
        return consulta.list();
                
    }

    @Override
    public Cliente pesquisaPorIdComEndereco(Long id, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Cliente c join fetch c.enderecos where c.id = :id");
        consulta.setParameter("id", id);
        
        return (Cliente) consulta.uniqueResult();
    }
    
}


