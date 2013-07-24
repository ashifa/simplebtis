package telnet.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import telnet.dao.TelnetDao;
@Service
public class AdminServiceImp implements AdminService {
	@Resource
	private TelnetDao telnetDAO ;

	@Override
	public void AddHost(String host, String ip) {
		this.telnetDAO.AddHost(host, ip);
	}

	@Override
	public void RemoveHost(String host) {
		this.telnetDAO.RemoveHost(host);

	}

	@Override
	public void AddCMD(String CmdName, String CmdValue) {
		this.telnetDAO.AddCMD(CmdName, CmdValue);

	}

	@Override
	public void RemoveCMD(String CMD) {
		this.telnetDAO.RemoveCMD(CMD);
	}

	@Override
	public Map<String, String> getCMDMap() {
		return this.telnetDAO.getCMDMap();
	}

	@Override
	public Map<String, String> getTargetMap() {
		return this.telnetDAO.getTargetMap();
	}

}
