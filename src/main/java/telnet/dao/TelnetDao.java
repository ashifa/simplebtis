package telnet.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;
@Repository
public class TelnetDao {



	private Properties config = new Properties();
	private Map<String, String> targetMap = new TreeMap<String, String>();
	private Map<String, String> CMDMap = new TreeMap<String, String>();
	private Set<String> selectedTargetRegion = new HashSet<String>();
	private File file;

	public Set<String> getSelectedTargetRegion() {
		return selectedTargetRegion;
	}

	public void setSelectedTargetRegion(Set<String> selectedTargetRegion) {
		this.selectedTargetRegion = selectedTargetRegion;
	}

	public String getUserName() {
		return this.config.getProperty("userName");
	}

	public String getPassWord() {
		return this.config.getProperty("passWord");
	}
	
	public String getPrompt() {
		return this.config.getProperty("prompt");
	}

	public Map<String, String> getTargetMap() {
		return targetMap;
	}

	public void setTargetMap(Map<String, String> targetMap) {
		this.targetMap = targetMap;
	}

	public Map<String, String> getCMDMap() {
		return CMDMap;
	}

	public void setCMDMap(Map<String, String> cMDMap) {
		CMDMap = cMDMap;
	}

	public void AddHost(String host, String ip) {
		this.config.put(host, ip);
		this.saveConfig();
	}

	public void RemoveHost(String host) {
		this.config.remove(host);
		this.saveConfig();
	}

	public void AddCMD(String CmdName, String CmdValue) {
		this.config.put(CmdName, CmdValue);
		this.saveConfig();
	}

	public void RemoveCMD(String CMD) {
		this.config.remove(CMD);
		this.saveConfig();
	}

	public TelnetDao() {
		System.out.println("in constructor of " + this.getClass());
		if (false == readConfig()) {
			System.exit(1);
		}
		parseConfig();
	}

	private boolean readConfig() {
		try {
			URL targetInfo = Thread.currentThread().getContextClassLoader()
					.getResource("config.xml");

			if (targetInfo != null) {
				file = new File(targetInfo.getFile());
			} else {
				file = new File("config.xml");
			}
			this.config.loadFromXML(new FileInputStream(file));

		} catch (IOException ex) {
			Logger.getLogger(TelnetDao.class.getName()).log(Level.SEVERE, null,
					ex);
			return false;
		}
		return true;

	}

	private void parseConfig() {
		// extract the CMD and HOST
		for (String str : config.stringPropertyNames()) {
			if (str.startsWith("CMD")) {
				this.CMDMap.put(str.substring(4), config.getProperty(str));
			} else if (str.startsWith("HOST_")) {
				this.targetMap.put(str.substring(5), config.getProperty(str));
			}
		}
		String[] tmp = config.getProperty("selectedTargetRegion").split(",");
		Collections.addAll(this.selectedTargetRegion, tmp);

	}

	private void saveConfig() {
		try {
			this.config.storeToXML(new FileOutputStream(file), "update");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

/*	public Map<Object,Object> getConfig() {
		return config;
	}*/


}
