package com.reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving extentTestNGIReporter events. The class
 * that is interested in processing a extentTestNGIReporter event implements
 * this interface, and the object created with that class is registered with a
 * component using the component's <code>addExtentTestNGIReporterListener<code>
 * method. When the extentTestNGIReporter event occurs, that object's
 * appropriate method is invoked.
 *
 * @see ExtentTestNGIReporterEvent
 */
public class ExtentTestNGIReporterListener implements IReporter {

	/** The Constant OUTPUT_FOLDER. */
	private static final String OUTPUT_FOLDER = "extentReports/";

	/** The sub test. */
	private static ThreadLocal<ExtentTest> subTest = new ThreadLocal<ExtentTest>();

	/** The test. */
	public static ExtentTest test = null;

	/** The sb test. */
	public static ExtentTest sbTest = null;

	/** The sb child test. */
	public static ExtentTest sbChildTest = null;

	/** The extent. */
	public static ExtentReports extent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.IReporter#generateReport(java.util.List, java.util.List,
	 * java.lang.String)
	 */
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		init(suites.get(0).getName());
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				for (ITestNGMethod iTestNGMethod : context.getAllTestMethods()) {
					buildTestNodes(context.getFailedConfigurations(), iTestNGMethod, Status.FATAL);
					buildTestNodes(context.getFailedTests(), iTestNGMethod, Status.FAIL);
					buildTestNodes(context.getSkippedTests(), iTestNGMethod, Status.SKIP);
					buildTestNodes(context.getPassedTests(), iTestNGMethod, Status.PASS);
				}
			}
		}
		for (String s : Reporter.getOutput()) {
			extent = new ExtentReports();
			extent.setTestRunnerOutput(s);
		}

		extent.flush();
	}

	/**
	 * Inits the.
	 *
	 * @param fileName
	 *            the file name
	 */
	private void init(String fileName) {

		File theDir = new File(OUTPUT_FOLDER);
		if (!theDir.exists()) {
			try {
				theDir.mkdir();
			} catch (SecurityException se) {
			}
		}

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(theDir + "/AutomationTests-Report.html");
		htmlReporter.config().setDocumentTitle("Automation Tests Report");
		htmlReporter.config().setReportName("Automation Tests Execution Details");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setEncoding("UTF-8");
		htmlReporter.config().setChartVisibilityOnOpen(false);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);
	}

	/**
	 * Builds the test nodes.
	 *
	 * @param tests
	 *            the tests
	 * @param iTestNGMethod
	 *            the i test NG method
	 * @param status
	 *            the status
	 */
	private void buildTestNodes(IResultMap tests, ITestNGMethod iTestNGMethod, Status status) {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getResults(iTestNGMethod)) {

				if (result.getParameters().length > 0) {
					sbTest = extent.createTest(iTestNGMethod.getMethodName() + "<font color=\"purple\" size=\"2\">"
							+ Arrays.deepToString(result.getParameters()) + "</font>");
				} else {
					sbTest = extent.createTest(iTestNGMethod.getMethodName());
				}
				subTest.set(sbTest);
				if (result.getStatus() == ITestResult.FAILURE) {
					if (result.getThrowable().getMessage() != null)
						((ExtentTest) subTest.get())
								.fail("<font color=\"red\">" + "TEST FAILED: </font><font color=\"purple\">"
										+ result.getThrowable().getMessage().toString() + "</font>");
					else
						((ExtentTest) subTest.get())
								.fail("<font color=\"red\">" + "TEST FAILED: </font><font color=\"purple\"></font>");

					
				} else if (result.getStatus() == ITestResult.SKIP) {
					if (result.getThrowable().getMessage() != null)
						((ExtentTest) subTest.get())
								.skip("<font color=\"red\">" + "TEST SKIPPED: </font><font color=\"purple\">"
										+ result.getThrowable().getMessage().toString() + "</font>");
					else
						((ExtentTest) subTest.get())
								.skip("<font color=\"red\">" + "TEST SKIPPED: </font><font color=\"purple\"></font>");
				} else {
					((ExtentTest) subTest.get()).pass("Test passed");

				}

				String fileName = null;

				if (result.getParameters().length > 0) {
					String paramsString = Arrays.deepToString(result.getParameters());
					String[] params = paramsString.substring(1, paramsString.length() - 1).split(", ");
					for (int j = 0; j < params.length; j++) {
						fileName = fileName + params[j];
					}
					fileName = fileName.substring(4, fileName.length());
				} else {
					fileName = iTestNGMethod.getMethodName();
				}
				fileName = fileName.replaceAll("/", "+");
				File fileCheck = new File("./TestResultFiles/" + fileName + ".html");
				if(fileCheck.exists())
					((ExtentTest) subTest.get())
						.info(Utils.returnFileContentAsString("./TestResultFiles/" + fileName + ".html"));
				else
					((ExtentTest) subTest.get()).info("Log is not generated as something went wrong with file creation");
				
				try {
					((ExtentTest) subTest.get()).addScreenCaptureFromPath("Screenshots/" + fileName+"screenshot.png");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public static String getTimeStamp() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-YYYY-hh-mm-ss");
		String time = dateFormat.format(now);
		return time;
		// File dir = new File(time);
		// dir.mkdir();
	}

	/**
	 * The main method.
	 *
	 * @param a
	 *            the arguments
	 */
	public static void main(String a[]) {
		getTimeStamp();
	}
}
