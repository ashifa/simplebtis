package telnet.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import telnet.model.Visitor;

public interface VisitorDao extends JpaRepository<Visitor, Integer> {


}
