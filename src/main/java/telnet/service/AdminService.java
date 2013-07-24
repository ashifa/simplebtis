package telnet.service;

import java.util.Map;

public interface AdminService {
	public void AddHost(String host, String ip);

	public void RemoveHost(String host);

	public void AddCMD(String CmdName, String CmdValue);

	public void RemoveCMD(String CMD);

	public Map<String, String> getCMDMap();

	public Map<String, String> getTargetMap();
}
