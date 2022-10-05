package com.grupo1.FlipFloppin.services;

import java.util.List;

public interface BaseService<E> {
    List<E> findAll() throws Exception;
    E findById(Long id) throws Exception;
    E update(E entity, Long id) throws Exception;
    E sava(E entity) throws Exception;
    boolean deleteById(Long id) throws Exception;
}
