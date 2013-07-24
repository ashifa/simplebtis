package telnet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TargetInfo {
	@Id
	@Column(nullable = false, length = 45)
	private String hostName;
	@Column(nullable = false, length = 45)
	private String iP;
	@Column(nullable = false, length = 1000)
	private String version;


	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getiP() {
		return iP;
	}

	public void setiP(String iP) {
		this.iP = iP;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
