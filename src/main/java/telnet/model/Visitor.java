package telnet.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int visitorId;
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false, length = 45)
	private String ip;
	@Column(nullable = false, length = 45)
	private String hostName;
	@Column(nullable = true, length = 45)
	private String user;
	@Column(nullable = false)
	private int port;
	@OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Cmd> cmd;

	public int getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Cmd> getCmd() {
		return cmd;
	}

	public void setCmd(Set<Cmd> cmd) {
		this.cmd = cmd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;

	}

	@Override
	public String toString() {
		return "{\nHostName:" + this.getHostName() + "\nIp:" + this.getIp()
				+ "\nDate:" + this.getDate() + "\nCMD:" + this.getCmd()
				+ "\nuser:" + this.getUser() + "\nport:" + this.getPort()
				+ "\n}";
	}

}
