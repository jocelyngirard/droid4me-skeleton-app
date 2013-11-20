package net.cylons.android.skeleton;

import org.apache.http.protocol.HTTP;

import android.util.Log;

/**
 * Gathers in one place the constants of the application.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
public abstract class Constants
{

  /**
   * Indicates the application development status.
   */
  static final boolean DEVELOPMENT_MODE = "".equals("");

  /**
   * The logging level of the application and of the droid4me framework.
   */
  static final int LOG_LEVEL = Constants.DEVELOPMENT_MODE ? Log.DEBUG : Log.WARN;

  /**
   * Indicates whether the analytics are enabled.
   */
  public static final boolean ANALYTICS_ENABLED = Constants.DEVELOPMENT_MODE ? false : true;

  /**
   * Indicates whether ACRA is enabled.
   */
  public static final boolean ACRA_ENABLED = Constants.DEVELOPMENT_MODE ? false : true;

  /**
   * The BugSense application identifier.
   */
  public static final String BUG_SENSE_APPLICATION_ID = "";

  /**
   * The e-mail that will receive error reports.
   */
  public static final String REPORT_LOG_RECIPIENT_EMAIL = "android@smartnsoft.com";

  /**
   * The name of the folder which will be used to store contents related to the application.
   */
  public static final String APPLICATION_FOLDER_NAME = "Skeleton";

  /**
   * The encoding used for decoding the contents of HTTP requests.
   */
  public static final String WEBSERVICES_CONTENT_ENCODING = HTTP.UTF_8;

  /**
   * The encoding used for wrapping the URL of the HTTP requests.
   */
  public static final String WEBSERVICES_HTML_ENCODING = HTTP.ISO_8859_1;

  // The HTTP requests server side response time-out
  public static final int HTTP_CONNECTION_TIMEOUT_IN_MILLISECONDS = 5000;

  // The HTTP requests socket connection time-out
  public static final int HTTP_SOCKET_TIMEOUT_IN_MILLISECONDS = 5000;

}
