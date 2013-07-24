package telnet.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import telnet.dao.TargetInfoDao;
import telnet.dao.TelnetDao;
import telnet.dao.VisitorDao;
import telnet.model.Cmd;
import telnet.model.TargetInfo;
import telnet.model.Visitor;

@Service
@Transactional
public class TelnetServiceDbImp implements TelnetService {
	/*
	 * private EntityManager em;
	 * 
	 * @PersistenceContext public void setEntityManager(EntityManager em) {
	 * this.em = em; }
	 */

	@Resource
	private TelnetDao telnetDao;

	@Resource
	private VisitorDao visitorDao;

	@Resource
	private TargetInfoDao targetInfoDao;

	@Override
	public Map<String, String> getTargetMap() {

		return this.telnetDao.getTargetMap();
	}

	@Override
	public void setTargetMap(Map<String, String> targetMap) {

		this.telnetDao.setTargetMap(targetMap);
	}

	@Override
	public Map<String, String> getCMDMap() {

		return this.telnetDao.getCMDMap();
	}

	@Override
	public void setCMDMap(Map<String, String> cMDMap) {
		this.telnetDao.setCMDMap(cMDMap);

	}

	public Set<String> getSelectedTargetRegion() {
		return this.telnetDao.getSelectedTargetRegion();
	}

	public void setSelectedTargetRegion(Set<String> selectedTargetRegion) {
		this.telnetDao.setSelectedTargetRegion(selectedTargetRegion);
	}

	private Set<String> selectedCMD = new TreeSet<String>();

	public Set<String> getSelectedCMD() {
		return selectedCMD;
	}

	public void setSelectedCMD(Set<String> selectedCMD) {
		this.selectedCMD.clear();
		this.selectedCMD.addAll(selectedCMD);
	}

	public TelnetServiceDbImp() {
		System.out.println("in constructor of " + this.getClass());
		this.getSelectedCMD().add(" Software Version");
	}

	private void saveVersion(List<List<String>> listArg) {
		for (List<String> strList : listArg) {
			if (strList.get(2).equals("On")) {
				TargetInfo targInfo = new TargetInfo();
				targInfo.setHostName(strList.get(0));
				targInfo.setiP(strList.get(1));
				targInfo.setVersion(strList.get(3));// need the third one to be
													// version info
				this.targetInfoDao.save(targInfo);
			}
		}
	}

	private List<Future<List<String>>> connectTarget(Collection<String> CMDlist) {
		ExecutorService es = Executors.newFixedThreadPool(this.telnetDao
				.getTargetMap().size());
		List<Future<List<String>>> futureList = new ArrayList<Future<List<String>>>();
		@SuppressWarnings("unchecked")
		Set<String> regions = (Set<String>) ActionContext.getContext()
				.getSession().get("selectedTargetRegion");

		for (String hostname : this.telnetDao.getTargetMap().keySet()) {
			for (String region : regions) {
				if (hostname.contains(region)) {
					Future<List<String>> rs = es.submit(new TelnetThread(
							hostname, this.telnetDao.getTargetMap().get(
									hostname), this.telnetDao.getUserName(),
							this.telnetDao.getPassWord(), this.telnetDao
									.getPrompt(), CMDlist));
					futureList.add(rs);
				}
			}

		}
		es.shutdown();

		try {
			es.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			Logger.getLogger(TelnetServiceDbImp.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		es.shutdownNow();
		return futureList;
	}

	private List<List<String>> buildRaw(List<Future<List<String>>> futureList) {
		List<List<String>> retList = new ArrayList<List<String>>();
		for (Future<List<String>> fl : futureList) {
			try {
				retList.add(fl.get());
			} catch (InterruptedException ex) {
				Logger.getLogger(TelnetServiceDbImp.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (ExecutionException ex) {
				Logger.getLogger(TelnetServiceDbImp.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		this.saveVersion(retList);
		return retList;
	}

	public List<List<String>> getNewVersion() {
		// start up thread based on these host name
		recordVisitor();
		List<String> CMDs = new ArrayList<String>();
		for (String str : this.getSelectedCMD()) {
			CMDs.add(this.getCMDMap().get(str));
		}
		List<Future<List<String>>> futureList = connectTarget(CMDs);
		return buildRaw(futureList);

	}

	public List<List<String>> getOldVersion() {
		recordVisitor();
		/*
		 * Query query = this.em.createQuery("select p FROM TargetInfo p");
		 * List<TargetInfo> list = query.getResultList();
		 */
		List<TargetInfo> list = this.targetInfoDao.findAll();
		List<List<String>> listRet = new ArrayList<List<String>>();
		for (TargetInfo targInfo : list) {
			List<String> tmp = new ArrayList<String>();
			tmp.add(targInfo.getHostName());
			tmp.add(targInfo.getiP());
			tmp.add(targInfo.getVersion());
			listRet.add(tmp);
		}
		return listRet;

	}

	private void recordVisitor() {
		HttpServletRequest request = org.apache.struts2.ServletActionContext
				.getRequest();

		Visitor vis = new Visitor();
		vis.setIp(request.getRemoteAddr());
		vis.setHostName(request.getRemoteHost());
		vis.setDate(new Date());
		vis.setPort(request.getRemotePort());
		vis.setUser(request.getRemoteUser());

		HashSet<Cmd> set = new HashSet<Cmd>();

		Cmd cmd;
		for (String str : this.getSelectedCMD()) {
			cmd = new Cmd();
			cmd.setName(str);
			cmd.setValue(this.getCMDMap().get(str));
			cmd.setVisitor(vis);
			set.add(cmd);
		}
		vis.setCmd(set);

		this.visitorDao.save(vis);
		/*
		 * Query query = this.em.createQuery("select p FROM Visitor p");
		 * 
		 * @SuppressWarnings("unchecked") List<Visitor> list =
		 * query.getResultList(); for (Visitor itr : list) {
		 * System.out.println(itr);
		 * 
		 * }
		 */
	}
}
