package com.usuarios.usuarios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface iClientes extends CrudRepository <Pedidos, Integer>{

    Optional<Pedidos> findByNombre(String nombre);
    
}
