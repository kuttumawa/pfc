package uned.dlr.pfc.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import uned.dlr.pfc.model.Codigo;

public interface CustomerRepository extends CrudRepository<Codigo, Long> {

    List<Codigo> findByLastName(String lastName);

}