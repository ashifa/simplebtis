package telnet.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import telnet.dao.VisitorDao;
import telnet.model.Visitor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ListVisitorAction extends ActionSupport {

	private static final long serialVersionUID = -6436088653003631314L;

	@Resource
	private VisitorDao visitorDao;

	public List<Visitor> getVisitorList() {

		return visitorDao.findAll();
	}

	@Override
	public String execute() {
		return Action.SUCCESS;
	}
}
