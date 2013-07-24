package telnet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telnet.model.TargetInfo;

public interface TargetInfoDao extends JpaRepository<TargetInfo, String>{

}
