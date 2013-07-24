package telnet.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import telnet.service.AdminService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TelnetAdminAction extends ActionSupport {

	private static final long serialVersionUID = 220817334781599319L;
	@Resource
	private AdminService adminService;

	private String editKey;
	private String editValue;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String getEditKey() {
		return editKey;
	}

	public void setEditKey(String editKey) {
		this.editKey = editKey;
	}

	public String getEditValue() {
		return editValue;
	}

	public void setEditValue(String editValue) {
		this.editValue = editValue;
	}

	public Map<String, String> getCMDMap() {
		return this.adminService.getCMDMap();
	}

	public Map<String, String> getTargetMap() {
		return this.adminService.getTargetMap();
	}

	@Override
	public String execute() {

		return Action.SUCCESS;
	}

	public String remove() {
		System.out.println("remove" + this.editKey);
		this.adminService.RemoveCMD(editKey);
		this.editKey = "";
		return execute();
	}

	public String edit() {

		this.adminService.RemoveCMD(editKey);
		this.adminService.AddCMD(editKey, editKey);
		this.editKey = "";
		this.editValue = "";

		return execute();
	}

}
