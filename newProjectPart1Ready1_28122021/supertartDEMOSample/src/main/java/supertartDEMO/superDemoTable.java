package supertartDEMO;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.MouseInputAdapter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.events.MouseEvent;

public class superDemoTable implements ActionListener {

	//
	private SwingWorker<Void, String> workerList;
	//

	String tester_tool = "Yes";
	final JTextField inptBugUser = new JTextField();
	final JPasswordField inptBugPassword = new JPasswordField();
	final JCheckBox check_remember_me = new JCheckBox("Remember me");
	final JTextArea textJiraQuerie = new JTextArea();
	static JTextArea textJiraQuerie2 = new JTextArea();
	final JButton btnSearchButton = new JButton("Search List");
	final JButton btnCancelButton = new JButton("Cancel");

	// Global CredentiasBugzilla
	String bug_user_value;
	String bug_pass_value;
	String jira_query_value;
	public static WebDriver driver;

	superDemoTable() {

		// InputProperies Part Start//////////////////////////////////////////////////
		String bug_user_value_gui_loading;
		String bug_pass_valuee_gui_loading;

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(
					"C:\\Users\\GEORGE\\eclipse-workspace\\test2\\src\\main\\java\\test2\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bug_user_value_gui_loading = prop.getProperty("bugzilla_user_name");
		bug_pass_valuee_gui_loading = prop.getProperty("bugzilla_pass");
		// InputProperies Part End//////////////////////////////////////////////////////

		final JFrame frmSupertable = new JFrame();
		frmSupertable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSupertable.setTitle("SuperTable1");
		frmSupertable.getContentPane().setLayout(null);
		frmSupertable.setSize(new Dimension(600, 400));
		/////////////////////////////////////////////////////////////
		// Input of Bugzilla email
		JLabel lblBugUserName = new JLabel("BugUserName");
		inptBugUser.setBounds(126, 24, 119, 20);
		frmSupertable.getContentPane().add(inptBugUser);
		// Installation of Popup Menu
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setPopupSize(50, 60);
		JMenuItem cutItem = new JMenuItem("Cut");
		popupMenu.add(cutItem);
		JMenuItem copyItem = new JMenuItem("Copy");
		popupMenu.add(copyItem);
		JMenuItem cutPaste = new JMenuItem("Paste");
		popupMenu.add(cutPaste);
		/////////////////////////////////////////////////////////////
		// Label of bugzilla email
		lblBugUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblBugUserName.setBounds(23, 24, 90, 20);
		frmSupertable.getContentPane().add(lblBugUserName);
		// Label of bugzilla password
		JLabel lblBugPassword = new JLabel("BugPassword");
		lblBugPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblBugPassword.setBounds(260, 24, 90, 20);
		frmSupertable.getContentPane().add(lblBugPassword);
		// Input of bugzilla password

		inptBugPassword.setBounds(360, 24, 119, 20);
		frmSupertable.getContentPane().add(inptBugPassword);

		inptBugPassword.setText(bug_pass_valuee_gui_loading);
		inptBugUser.setText(bug_user_value_gui_loading);
		///////////////////////////////////////////////////////
		// Save me Label

		check_remember_me.setHorizontalAlignment(SwingConstants.LEFT);
		check_remember_me.setBounds(360, 56, 119, 23);
		frmSupertable.getContentPane().add(check_remember_me);
		///////////////////////////////////////////////////////
		// Jira Input
		JLabel lblJiraQuerie = new JLabel("Please enter the Jira Query");
		lblJiraQuerie.setVerticalAlignment(SwingConstants.BOTTOM);
		lblJiraQuerie.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblJiraQuerie.setHorizontalAlignment(SwingConstants.CENTER);
		lblJiraQuerie.setBounds(33, 119, 342, 29);
		frmSupertable.getContentPane().add(lblJiraQuerie);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(33, 147, 342, 64);
		frmSupertable.getContentPane().add(scrollPane_1);
		// textJiraQuerie2.setEditable(false);
		scrollPane_1.setViewportView(textJiraQuerie);
		textJiraQuerie.setLineWrap(true);
		textJiraQuerie.setWrapStyleWord(true);
		///////////////////////////////////////////////////////
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(33, 259, 446, 74);
		frmSupertable.getContentPane().add(scrollPane_2);
		textJiraQuerie2.setEditable(false);
		scrollPane_2.setViewportView(textJiraQuerie2);
		textJiraQuerie2.setLineWrap(true);
		textJiraQuerie2.setWrapStyleWord(true);
		JLabel lblResultsAndLinks = new JLabel("Results and Links");
		lblResultsAndLinks.setVerticalAlignment(SwingConstants.BOTTOM);
		lblResultsAndLinks.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultsAndLinks.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblResultsAndLinks.setBounds(33, 222, 446, 36);
		frmSupertable.getContentPane().add(lblResultsAndLinks);
		///////////////////////////////////////////////////////

		btnSearchButton.setBounds(385, 151, 99, 23);
		frmSupertable.getContentPane().add(btnSearchButton);
		btnCancelButton.setEnabled(false);
		btnSearchButton.addActionListener((ActionListener) this);

		btnCancelButton.setBounds(490, 151, 89, 23);
		// frmSupertable.getContentPane().add(btnCancelButton);
		// btnSearchButton.addActionListener((ActionListener) this);
		////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////

		frmSupertable.setVisible(true);

	}
//////////////////////////////////Testing Area Start

//////////////////////////////////Testing Area End

////////////////////////////////////////////////////////////////////////////////
	//////////////// Action Listener Start/////////////////
////////////////////////////////////////////////////////////////////////////////	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSearchButton) {
			btnSearchButton.setEnabled(false);
			btnCancelButton.setEnabled(true);

			Boolean rememberMe = check_remember_me.isSelected();

			bug_user_value = inptBugUser.getText();
			bug_pass_value = inptBugPassword.getText();
			jira_query_value = textJiraQuerie.getText();
			inptBugUser.setEditable(false);
			inptBugPassword.setEditable(false);
			textJiraQuerie.setEditable(false);

			// If TickBox="Yes" ->UpdateProp Start////////////////////
			if (rememberMe) {

				try {
					updatePropMethod();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// If TickBox="Yes" ->UpdateProp End////////////////////

			workerList = new SwingWorker<Void, String>() {

				@Override
				/////////// Bugzilla Check Credentials
				/////////// Start////////////////////////////////////////
				protected Void doInBackground() throws Exception {
					int numberOfJiras = 0;
					String bugzillaTextGross = "";
					String bugzillaTextClean = "";
					textJiraQuerie2.setEditable(true);
					textJiraQuerie2.setText("Verifying Bugzilla Credentials, Please Wait....");
					textJiraQuerie2.setEditable(false);
					Boolean jiraCheckResult = null;

					String bugzillaCheckValue = null;
					// TODO Auto-generated method stub
					// CredentialsChecking_Start
					try {
						bugzillaCheckValue = checkBugCredentials(bug_user_value, bug_pass_value);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					System.out.println(bugzillaCheckValue);

					if (bugzillaCheckValue.equals("correctCredentials")) {
						textJiraQuerie2.setEditable(true);
						textJiraQuerie2.setText("Credentials Verified." + "\n" + "Please Wait....");
						textJiraQuerie2.setEditable(false);
					} else {
						textJiraQuerie2.setEditable(true);
						textJiraQuerie2.setText("Credentials does not match." + "\n" + "Please try again");
						textJiraQuerie2.setEditable(false);
						btnSearchButton.setEnabled(true);

						inptBugUser.setEditable(true);
						inptBugPassword.setEditable(true);
						textJiraQuerie.setEditable(true);

					}

					/////////// Bugzilla Check Credentials End///////////////////////

					////////// Jira Check Start//////////////////////

					if (bugzillaCheckValue.equals("correctCredentials")) {

						if (!jira_query_value.equals("")) {
							textJiraQuerie2.setText("Verifying Jira Query, Please Wait....");
							jiraCheckResult = checkJiraQuery(jira_query_value);
							System.out.println(jiraCheckResult);
							////////////////////////////////////
							if (jiraCheckResult) {
								textJiraQuerie2.setEditable(true);
								textJiraQuerie2.setText("SuperTable starts scrapping" + "\n" + "Please wait....");
								textJiraQuerie2.setEditable(false);
								////////////////////////////////////
								// How many items the query has Start
								int jiraQueryItems = totalNumberScrapper(jira_query_value);
								textJiraQuerie2.setEditable(true);
								textJiraQuerie2.setText("SuperTable will return " + jiraQueryItems + " items.");
								textJiraQuerie2.setEditable(false);
								// How many items the query has End
								////////////////////////////////////

								// How Return Bugzilla String
								bugzillaTextGross = superTableAnalysis(jiraQueryItems, jira_query_value);
								bugzillaTextClean = superTableAnalysisCleaner(bugzillaTextGross);

								/////////

								textJiraQuerie2.setEditable(true);
								textJiraQuerie2
										.setText("Please type the string in Bugzilla:" + "\n" + bugzillaTextClean);
								textJiraQuerie2.setEditable(false);

								btnSearchButton.setEnabled(true);

								inptBugUser.setEditable(true);
								inptBugPassword.setEditable(true);
								textJiraQuerie.setEditable(true);

							} else {
								textJiraQuerie2.setEditable(true);
								textJiraQuerie2
										.setText("Jira Query does not return any result." + "\n" + "Please try again.");
								textJiraQuerie2.setEditable(false);
								btnSearchButton.setEnabled(true);

								inptBugUser.setEditable(true);
								inptBugPassword.setEditable(true);
								textJiraQuerie.setEditable(true);

							}

						} else {
							textJiraQuerie2.setEditable(true);
							textJiraQuerie2.setText("Jira Query is Empty." + "\n" + "Please try again.");
							textJiraQuerie2.setEditable(false);
							btnSearchButton.setEnabled(true);

							inptBugUser.setEditable(true);
							inptBugPassword.setEditable(true);
							textJiraQuerie.setEditable(true);

						}

					} else {
						textJiraQuerie2.setEditable(true);
						textJiraQuerie2.setText("Wrong Bugzilla Credentials." + "\n" + "Please try again.");
						textJiraQuerie2.setEditable(false);
						btnSearchButton.setEnabled(true);

						inptBugUser.setEditable(true);
						inptBugPassword.setEditable(true);
						textJiraQuerie.setEditable(true);

					}

					return null;

				}

			};
			workerList.execute();
			////////////////////////////////////////////////////////////////

		}

		/*
		 * if (e.getSource() == btnCancelButton) { btnCancelButton.setEnabled(false);
		 * btnSearchButton.setEnabled(true); driver.close();
		 * 
		 * System.out.println("test"); try { Thread.sleep(5000); } catch
		 * (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } workerList.cancel(true); }
		 */
	}
////////////////////////////////////////////////////////////////////////////////
////////////////Action Listener End/////////////////
////////////////////////////////////////////////////////////////////////////////

	public void updatePropMethod() throws IOException {
		OutputStream output = null;
		Properties prop = new Properties();
		String bug_user_valueGUIString = inptBugUser.getText();
		String bug_pass_valueGUI = inptBugPassword.getText();

		try {
			output = new FileOutputStream(
					"C:\\Users\\GEORGE\\eclipse-workspace\\test2\\src\\main\\java\\test2\\config.properties");
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		prop.setProperty("bugzilla_user_name", bug_user_valueGUIString);
		prop.setProperty("bugzilla_pass", bug_pass_valueGUI);

		try {
			prop.store(output, (String) null);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////
	////// SeleniumPartStart
	///////////////////////////////////////////////////////////////////////////////////////

	// WebDriver Ignition Start
	public static WebDriver initDriver(String browserName) throws IOException, SQLException {
		if (browserName.equals("Google_Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\GEORGE\\chromeDriver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito", "--window-size=1920,1200", "--ignore-certificate-errors",
					"--allow-running-insecure-content", "--disable-gpu");
			WebDriver driver = new ChromeDriver(options); // , "--headless",

			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return driver;

		}

		else if (browserName.equals("Google_Chrome_headless")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\GEORGE\\chromeDriver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito", "--window-size=1920,1200", "--ignore-certificate-errors",
					"--allow-running-insecure-content", "--disable-gpu", "--headless");
			WebDriver driver = new ChromeDriver(options); // , "--headless",

			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return driver;
		}

		return driver;

	}
	// WebDriver Ignition End

	// CheckBugzillaCredentialStart
	public static String checkBugCredentials(String buguser2, String bugpassvalue) throws IOException, SQLException {

		String checkCredentials;
		driver = initDriver("Google_Chrome_headless");
		driver.manage().window().maximize();
		driver.get("http://bugzilla.SAMPLE.com/query.cgi");
		Actions action = new Actions(driver);
		String bugzilla_login = "//*[@id='Bugzilla_login']";
		String bugzilla_password = "//*[@id='Bugzilla_password']";
		driver.findElement(By.xpath(bugzilla_login)).sendKeys(new CharSequence[] { buguser2 });
		driver.findElement(By.xpath(bugzilla_password)).sendKeys(new CharSequence[] { bugpassvalue });
		driver.findElement(By.xpath("//*[@id='log_in']")).click();
		try {
			driver.findElement(By.xpath("//*[@id='tab_advanced']")).click();
		} catch (Exception e) {
			checkCredentials = "wrong_credentials";
			return checkCredentials;

		}
		checkCredentials = "correctCredentials";
		driver.close();
		return checkCredentials;

	}
	// CheckBugzillaCredentialEnd

	// CheckJiraQuery Start
	public static Boolean checkJiraQuery(String jiraQuery) throws IOException, SQLException {
		Boolean isQueryCorrect = null;
		driver = initDriver("Google_Chrome_headless");
		driver.manage().window().maximize();
		driver.get("https://eppo-collaboration.SAMPLE.com/jira/secure/Dashboard.jspa");
		driver.findElement(By.xpath("//*[@id='login-form-username']")).sendKeys(new CharSequence[] { "SAMPLE" });
		driver.findElement(By.xpath("//*[@id='login-form-password']")).sendKeys(new CharSequence[] { "SAMPLE" });
		driver.findElement(By.xpath("//*[@id='remembermelabel']")).click();

		driver.findElement(By.xpath("//*[@id='login']")).click();

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			delay(2000);
			driver.switchTo().defaultContent();
		} catch (Exception e) {

		}

		delay(2000);
		System.out.println("check");
		driver.findElement(By.xpath("//*[@id='find_link']")).click();
		driver.findElement(By.xpath("//*[@id='issues_new']")).click();
		delay(1500);
		// Enter to Search Part
		try {
			driver.findElement(
					By.xpath("//*[@id='content']/div[1]/div[3]/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/div/a[1]"))
					.click();

		} catch (Exception e) {
			System.out.println("Advanced is allready selected");
		}

		delay(1500);
		driver.findElement(By.xpath("//*[@id='layout-switcher-button']")).click();
		delay(1500);
		driver.findElement(By.xpath("//*[@id='layout-switcher-options']/div/ul/li[2]/a")).click();
		delay(4000);

		// Type Query
		driver.findElement(By.xpath("//*[@id='advanced-search']")).sendKeys(new CharSequence[] { jiraQuery });
		delay(1500);

		// Click Search
		driver.findElement(
				By.xpath("//*[@id='content']/div[1]/div[3]/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/button"))
				.click();

		try {
			String checkForError = driver
					.findElement(By.xpath("//*[@id='content']/div[1]/div[3]/div/div[2]/div/div/div/div/h2")).getText();
			isQueryCorrect = false;
		} catch (Exception e) {
			isQueryCorrect = true;

		}
		driver.close();

		return isQueryCorrect;

	}
	// ChecjJiraQuery End

	// DelayMethod Start
	public static void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}
	// DelayMethod End

	// jira total number Start
	public static int totalNumberScrapper(String jiraQuery) throws IOException, SQLException {
		int scrappedNumber = 0;
		String scrappedNumberStr = null;
		driver = initDriver("Google_Chrome_headless");
		driver.manage().window().maximize();
		driver.get("https://eppo-collaboration.SAMPLE.com/jira/secure/Dashboard.jspa");
		driver.findElement(By.xpath("//*[@id='login-form-username']")).sendKeys(new CharSequence[] { "SAMPLE" });
		driver.findElement(By.xpath("//*[@id='login-form-password']")).sendKeys(new CharSequence[] { "SAMPLE" });
		driver.findElement(By.xpath("//*[@id='remembermelabel']")).click();

		driver.findElement(By.xpath("//*[@id='login']")).click();

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			delay(2000);
			driver.switchTo().defaultContent();
		} catch (Exception e) {

		}

		delay(2000);
		System.out.println("check");
		driver.findElement(By.xpath("//*[@id='find_link']")).click();
		driver.findElement(By.xpath("//*[@id='issues_new']")).click();
		delay(1500);
		// Enter to Search Part
		try {
			driver.findElement(
					By.xpath("//*[@id='content']/div[1]/div[3]/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/div/a[1]"))
					.click();

		} catch (Exception e) {
			System.out.println("Advanced is allready selected");
		}

		delay(1500);
		driver.findElement(By.xpath("//*[@id='layout-switcher-button']")).click();
		delay(1500);
		driver.findElement(By.xpath("//*[@id='layout-switcher-options']/div/ul/li[2]/a")).click();
		delay(2000);

		// Type Query
		driver.findElement(By.xpath("//*[@id='advanced-search']")).sendKeys(new CharSequence[] { jiraQuery });
		delay(1500);

		// Click Search
		driver.findElement(
				By.xpath("//*[@id='content']/div[1]/div[3]/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/button"))
				.click();
		delay(2500);
		scrappedNumberStr = driver
				.findElement(By.xpath(
						"//*[@id='content']/div[1]/div[3]/div/div[2]/div/div/div/div/div[1]/div[1]/span/span[3]"))
				.getText();
		driver.close();
		System.out.println(scrappedNumberStr + "   11111");

		scrappedNumber = Integer.parseInt(scrappedNumberStr);

		return scrappedNumber;

	}

	public static String superTableAnalysis(int jiraQueryItems, String jiraQuery) throws IOException, SQLException {
		String grossData = null;
		String fullPage = null;
		driver = initDriver("Google_Chrome_headless");
		driver.manage().window().maximize();
		driver.get("https://eppo-collaboration.SAMPLE.com/jira/secure/Dashboard.jspa");
		driver.findElement(By.xpath("//*[@id='login-form-username']")).sendKeys(new CharSequence[] { "SAMPLE" });
		driver.findElement(By.xpath("//*[@id='login-form-password']")).sendKeys(new CharSequence[] { "SAMPLE" });
		driver.findElement(By.xpath("//*[@id='remembermelabel']")).click();

		driver.findElement(By.xpath("//*[@id='login']")).click();

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			delay(2000);
			driver.switchTo().defaultContent();
		} catch (Exception e) {

		}

		delay(2000);
		System.out.println("check");
		driver.findElement(By.xpath("//*[@id='find_link']")).click();
		driver.findElement(By.xpath("//*[@id='issues_new']")).click();
		delay(1500);
		// Enter to Search Part
		try {
			driver.findElement(
					By.xpath("//*[@id='content']/div[1]/div[3]/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/div/a[1]"))
					.click();

		} catch (Exception e) {
			System.out.println("Advanced is allready selected");
		}

		delay(1500);
		driver.findElement(By.xpath("//*[@id='layout-switcher-button']")).click();
		delay(1500);
		driver.findElement(By.xpath("//*[@id='layout-switcher-options']/div/ul/li[2]/a")).click();
		delay(4000);

		// Type Query
		driver.findElement(By.xpath("//*[@id='advanced-search']")).sendKeys(new CharSequence[] { jiraQuery });
		delay(1500);

		// Click Search
		driver.findElement(
				By.xpath("//*[@id='content']/div[1]/div[3]/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/button"))
				.click();
		/////////////////////////////////////
		/////////////////////////////////////
		int pagesNumber = jiraQueryItems / 50;
		int i = 0;
		int k = 1;
		System.out.println(pagesNumber);
		pagesNumber++;
		if (pagesNumber == 1) {
			delay(3000);
			grossData = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[3]/div/div[2]/div/div/div"))
					.getText();
			delay(2000);
			driver.close();

		} else {
			while (i != pagesNumber) {

				if (i > 0) {
					String change_page_path = "//a[@data-page='" + k + "']";
					System.out.println(change_page_path);
					delay(1000);
					driver.findElement(By.xpath(change_page_path)).click();
				}
				delay(4000);
				fullPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[3]/div/div[2]/div/div/div"))
						.getText();
				grossData = grossData + fullPage;
				i++;
				k++;

			}
			driver.close();
		}
		return grossData;
	}

	public static String superTableAnalysisCleaner(String bugzillaTextGross) throws IOException, SQLException {
		Scanner scanner = new Scanner(bugzillaTextGross);
		int number_of_characters = 0;
		String bugzillaTextClean = "";

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int number_of_index = line.indexOf("-");
			number_of_characters = line.length();

			if (number_of_index > -1 && number_of_characters <= 10) {
				bugzillaTextClean = String.valueOf(bugzillaTextClean) + "  " + line;
			}
		}
		scanner.close();

		return bugzillaTextClean;
	}

	// public static String cleanerBugzilla(int jiraQueryItems, String jiraQuery)
	// throws IOException, SQLException {

	// jira total number End

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		superDemoTable demo = new superDemoTable();

	}

}
