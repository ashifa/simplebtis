package telnet.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TelnetService {
	public List<List<String>> getNewVersion();

	public List<List<String>> getOldVersion();

	public Map<String, String> getTargetMap();

	public void setTargetMap(Map<String, String> targetMap);

	public Map<String, String> getCMDMap();

	public void setCMDMap(Map<String, String> cMDMap);

	public Set<String> getSelectedTargetRegion();

	public void setSelectedTargetRegion(Set<String> selectedTargetRegion);

	public Set<String> getSelectedCMD();

	public void setSelectedCMD(Set<String> selectedCMD);
}
