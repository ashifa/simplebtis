package telnet.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import telnet.service.TelnetService;

;
@Controller
public class TelnetAction extends ActionSupport {
	private static final long serialVersionUID = 5704419762979642412L;

	@Resource
	private TelnetService telnetService;

	private String customizedCMD;
	private List<List<String>> tblist;

	public Set<String> getSelectedCMD() {
		return this.telnetService.getSelectedCMD();
	}

	public void setSelectedCMD(Set<String> selectedCMD) {
		this.telnetService.setSelectedCMD(selectedCMD);
	}

	@SuppressWarnings("unchecked")
	public Set<String> getSelectedTargetRegion() {
		return (Set<String>) ActionContext.getContext().getSession()
				.get("selectedTargetRegion");
	}

	public void setSelectedTargetRegion(Set<String> selectedTargetRegion) {
		ActionContext.getContext().getSession()
				.put("selectedTargetRegion", selectedTargetRegion);
	}

	public TelnetService getTelnetService() {
		return telnetService;
	}

	public void setTelnetService(TelnetService telnetService) {
		this.telnetService = telnetService;
	}

	public String getCustomizedCMD() {
		return customizedCMD;
	}

	public void setCustomizedCMD(String customizedCMD) {
		this.customizedCMD = customizedCMD;
	}

	public Map<String, String> getTargetMap() {
		return this.telnetService.getTargetMap();
	}

	public void setTargetMap(Map<String, String> targetMap) {
		this.telnetService.setTargetMap(targetMap);
	}

	public Map<String, String> getCMDMap() {
		return this.telnetService.getCMDMap();
	}

	public void setCMDMap(Map<String, String> cMDMap) {
		this.telnetService.setCMDMap(cMDMap);
	}

	public void setTblist(List<List<String>> tblist) {
		this.tblist = tblist;
	}

	public List<List<String>> getTblist() {
		return this.tblist;
	}

	@Override
	public String execute() {

		if (ActionContext.getContext().getSession().get("selectedTargetRegion") == null) {
			this.setSelectedTargetRegion(this.telnetService
					.getSelectedTargetRegion());
		}
		System.out.println("read from DB");
		ActionContext.getContext().put("isOnline", false);
		List<List<String>> list = telnetService.getOldVersion();
		this.setTblist(list);
		return Action.SUCCESS;
	}

	public String onlineQuery() {
		System.out.println(this.getSelectedCMD());
		ActionContext.getContext().put("isOnline", true);
		if (!this.customizedCMD.isEmpty()) {
			String customizedCMD = this.customizedCMD.contains("|||") ? this.customizedCMD
					: this.customizedCMD + "|||.*";
			this.getCMDMap().put("customizedCMD", customizedCMD);
			this.getSelectedCMD().add("customizedCMD");
		} else {
			this.getSelectedCMD().remove("customizedCMD");
		}
		if (!this.getSelectedCMD().contains(" Software Version")) {
			this.getSelectedCMD().add(" Software Version");// must go first

		}

		List<List<String>> list = telnetService.getNewVersion();
		this.setTblist(list);

		return Action.SUCCESS;
	}
}
