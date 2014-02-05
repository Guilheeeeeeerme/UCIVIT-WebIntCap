package usaproxy.domchanges;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * A list of elements with the data structure representing an element containing
 * the necessary actions to perform to transform the previous DOM into the
 * current one. There are two possibilities: INSERT and DELETE (INSERT,
 * “charIndexStart”, “insertionText”) (DELETE, “charIndexStart”, “charIndexEnd”)
 * The second parameter "charIndexEndORinsertionText" will contain the
 * charIndexEnd in the case of DELETE and the insertionText in the case of
 * INSERT It also provides serialization and deserialization functionality via
 * GSON library (https://code.google.com/p/google-gson/)
 */
public class DOMChangesLogList {

	public String timestamp;
	private String timestampms;

	private String sessionstartms ="";

	private String sessionstartparsed ="";

	private String usertimezoneoffset ="";
	
	public String sd;
	public String sid;

	public String clientIP;

	private String url;
	
	/**
	 * Client's browser
	 */
	private String browser;
	
	/**
	 * Client's operating system
	 */
	private String platform;

	public ArrayList<DOMChangeLogElement> list = new ArrayList<DOMChangeLogElement>();

	/**
	 * Adds the necessary context information to the DOM change list element
	 * 
	 * @param domBean
	 *            , {@link DOMBean} with all the necessary information
	 */
	
	public void setContextInfo(DOMBean domBean) {
		this.timestamp = domBean.getTimestamp();
		this.timestampms = domBean.getTimestampms();
		
		this.sessionstartms = domBean.getSessionstartms();
		this.sessionstartparsed = domBean.getSessionstartparsed();
		this.usertimezoneoffset = domBean.getUsertimezoneoffset();

		this.sd = domBean.getSd();
		this.sid = domBean.getSid();
		this.clientIP = domBean.getClientIP();
		this.url = domBean.getUrl();
		this.browser= domBean.getBrowser();
		this.platform = domBean.getPlatform();
	}

	/**
	 * The data structure representing an element containing the necessary
	 * actions to perform to transform the previous DOM into the current one.
	 * There are two possibilities: INSERT and DELETE (INSERT, “charIndexStart”,
	 * “insertionText”) (DELETE, “charIndexStart”, “charIndexEnd”) The second
	 * parameter "charIndexEndORinsertionText" will contain the charIndexEnd in
	 * the case of DELETE and the insertionText in the case of INSERT
	 */
	class DOMChangeLogElement {
		private String operation;
		private String charIndexStart;
		private String charIndexEndORinsertionText;

		/**
		 * Constructor: creates a n element describing one DOM change operation
		 * 
		 * @param Operation
		 *            is the operation to store (INSERT or DELETE)
		 * @param charIndexStart
		 *            indicates the index where the change starts
		 * @param charIndexEndORinsertionText
		 *            can be one of these two values: the index where the change
		 *            ends in the case of DELETE or the insertionText in the
		 *            case of INSERT
		 */
		DOMChangeLogElement(String operation, String charIndexStart,
				String charIndexEndORinsertionText) {
			this.operation = operation;
			this.charIndexStart = charIndexStart;

			this.charIndexEndORinsertionText = charIndexEndORinsertionText;

//			try {
//				ErrorLogging.logError(
//						"DOMChangesLogList.java:DOMChangeLogElement",
//						"The following string should not be encoded: \n"
//								+ charIndexEndORinsertionText, null);
//
//				this.charIndexEndORinsertionText = URLEncoder.encode(
//						charIndexEndORinsertionText, "UTF-8");
//
//			} catch (UnsupportedEncodingException e) {
//				ErrorLogging
//						.logError(
//								"DOMChangesLogList.java:DOMChangeLogElement",
//								"There was an error trying to encode the String content",
//								e);
//			}
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public int getCharIndexStart() {
			try {
				return Integer.parseInt(charIndexStart);
			} catch (Exception e) {
				return -1;
			}
		}

		public void setCharIndexStart(String charIndexStart) {
			this.charIndexStart = charIndexStart;
		}

		public int getCharIndexEnd() {
			try {
				return Integer.parseInt(charIndexEndORinsertionText);
			} catch (Exception e) {
				// in case "charIndexEndORinsertionText" is a string and not a
				// number
				return -1;
			}
		}

		public String getInsertionText() {
//			String decodedInsertionText = charIndexEndORinsertionText;
//			try {
//				decodedInsertionText = URLDecoder.decode(
//						charIndexEndORinsertionText, "UTF-8");
//
//			} catch (UnsupportedEncodingException e) {
//				ErrorLogging.logError(
//						"DOMChangesLogList.java:getInsertionText()",
//						"There was an error trying to decode the String content: "
//								+ charIndexEndORinsertionText, e);
//			}
//			return decodedInsertionText;
			return charIndexEndORinsertionText;
		}

		public void setCharIndexEndORinsertionText(
				String charIndexEndORinsertionText) {
//			try {
//				ErrorLogging.logError(
//						"DOMChangesLogList.java:setCharIndexEndORinsertionText",
//						"The following string should not be encoded: \n"
//								+ charIndexEndORinsertionText, null);
//
//				this.charIndexEndORinsertionText = URLEncoder.encode(
//						charIndexEndORinsertionText, "UTF-8");
//
//			} catch (UnsupportedEncodingException e) {
//				ErrorLogging
//						.logError(
//								"DOMChangesLogList.java:setCharIndexEndORinsertionTextt",
//								"There was an error trying to encode the String content",
//								e);
//			}
			this.charIndexEndORinsertionText = charIndexEndORinsertionText;
		}

		public String printInfo() {
			return (this.operation + "," + this.charIndexStart + "," + this.charIndexEndORinsertionText);
		}

	}

	public void addDOMChangeElement(String operation, String charIndexStart,
			String charIndexEndORinsertionText) {
		list.add(new DOMChangeLogElement(operation, charIndexStart,
				charIndexEndORinsertionText));
	}

	/**
	 * Serialise the class into a JSON, and returns the String containing it
	 * 
	 * @return serialised class in JSON
	 */

	public String toGson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}

	/**
	 * Deserialise given JSON and assigns the resulting class list to
	 * "domChangesLogList"
	 * 
	 * @param serialised
	 *            class in JSON
	 */

	public void fromGson(String json) {
		Gson gson = new Gson();
		this.list = gson.fromJson(json, DOMChangesLogList.class).list;

	}

}