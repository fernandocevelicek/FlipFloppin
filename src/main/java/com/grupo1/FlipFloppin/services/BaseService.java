package com.grupo1.FlipFloppin.services;

import java.util.List;

public interface BaseService<E> {
    List<E> findAll() throws Exception;
    E findById(Long id) throws Exception;
    E update(E dto, Long id) throws Exception;
    E save(E dtp) throws Exception;
    boolean deleteById(Long id) throws Exception;
}