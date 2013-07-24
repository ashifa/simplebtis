/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telnet.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.telnet.TelnetClient;

/**
 * 
 * @author 305020571
 */
public class TelnetThread implements Callable<List<String>> {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private char prompt = ']';
	private String hostname;
	private String ip;
	private String userName;
	private String passWord;
	private Collection<String> CMDlist;

	public TelnetThread(String hostname, String ip, String userName,
			String passWord, String prompt, Collection<String> CMDlist) {
		this.hostname = hostname;
		this.ip = ip;
		this.userName = userName;
		this.passWord = passWord;
		this.prompt = prompt.charAt(0);
		this.CMDlist = CMDlist;
	}

	private boolean intconnect(String host, int port) {
		boolean retval = true;

		try {
/*			telnet.setSoTimeout(3000);	*/		
			telnet.connect(host, port);
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			telnet.sendAYT(1000);
			this.login();
		} catch (IOException e) {
			retval = false;
		} catch (IllegalArgumentException e) {
			retval = false;
			e.printStackTrace();
		} catch (InterruptedException e) {
			retval = false;
			e.printStackTrace();
		}

		return retval;
	}

	private void login() throws IOException {
		readUntil("login:");
		write(userName);
		readUntil("Password:");
		write(passWord);
		readUntil(prompt + "");
	}

	private String readUntil(String pattern) throws IOException {

		char lastChar = pattern.charAt(pattern.length() - 1);
		StringBuilder sb = new StringBuilder();
		char ch = (char) in.read();
		while (true) {
			sb.append(ch);
			if (ch == lastChar) {
				if (sb.toString().endsWith(pattern)) {
					return sb.toString();
				}
			}

			ch = (char) in.read();
		}

	}

	private void write(String value) {
		try {
			out.println(value);
			out.flush();
		} catch (Exception ex) {
			Logger.getLogger(TelnetThread.class.getName()).log(Level.SEVERE,
					this.ip, ex);
		}
	}

	private String sendCommand(String command) throws IOException {

		write(command);
		return readUntil(prompt + " ");

	}

	private void disconnect() {
		try {
			telnet.disconnect();
		} catch (Exception ex) {
			Logger.getLogger(TelnetThread.class.getName()).log(Level.SEVERE,
					this.ip, ex);
		}
	}

	private String execCMD(String CMD, String matchPattern) throws IOException {

		String output = this.sendCommand(CMD);
		if (output.indexOf("\r\n") != output.lastIndexOf("\r\n")) {
			output = output.substring(output.indexOf("\r\n") + 2,
					output.lastIndexOf("\r\n"));// remove first and last line
		}
		Pattern p = Pattern.compile(matchPattern, Pattern.MULTILINE);
		Matcher m = p.matcher(output);
		StringBuilder sb = new StringBuilder();
		while (true == m.find()) {
			sb.append(m.group().trim());
			sb.append("\n");
		}
		return sb.toString().replaceAll("\n\n\n", "\n");
	}

	@Override
	public List<String> call() {

		List<String> list = new ArrayList<String>();
		list.add(this.hostname);
		list.add(this.ip);

		if (this.intconnect(this.ip, 23) == true) {
			list.add("On");
		} else {
			list.add("Off");
			for (int n = 0; n < this.CMDlist.size(); n++) {
				list.add("");
			}
			return list;
		}

		try {

			for (String str : CMDlist) {// some command may not working, it
										// needs backup command.
				String[] cmds = str.split("#{3}");
				String execResult = "";
				for (String cmd : cmds) {
					String[] sub = cmd.split("\\|{3}");
					execResult = this.execCMD(sub[0], sub[1]);
					if (!execResult.isEmpty()) {
						break;
					}
				}
				list.add(execResult);
			}

		} catch (IOException ex) {
			list.add(ex.getMessage());
		} finally {
			this.disconnect();
		}
		return list;
	}

	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
		list.add("cd /usr/g/service/log/; ls  -1 *core*  |||^(?! cd).*(core|CORE).*$");
		list.add("testrecord|||.*_\\d{4}.*###getver|||.*_\\d{4}.*");
		// List<String> res = new TelnetThread("violet", "3.35.117.216", "sdc",
		// "adw2.0", "]", list).call();
		List<String> res = new TelnetThread("BJ bay5", "3.35.117.194", "sdc",
				"adw2.0", "]", list).call();
		System.out.println(res);

	}
}