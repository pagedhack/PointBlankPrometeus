package com.usuarios.usuarios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface iInventarios extends CrudRepository <Inventario, Integer>{
    
    Optional<Pedidos> findByNombre(String nombre);
    
}
