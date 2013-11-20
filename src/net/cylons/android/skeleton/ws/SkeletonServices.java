package net.cylons.android.skeleton.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import net.cylons.android.skeleton.Constants;

import com.smartnsoft.droid4me.ws.WebServiceCaller;

/**
 * A single point of access to the web services.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
public final class SkeletonServices
    extends WebServiceCaller
    implements WebServiceCaller.ReuseHttpClient
{

  private static volatile SkeletonServices instance;

  // We accept the "out-of-order writes" case
  public static SkeletonServices getInstance()
  {
    if (instance == null)
    {
      synchronized (SkeletonServices.class)
      {
        if (instance == null)
        {
          instance = new SkeletonServices();
        }
      }
    }
    return instance;
  }

  private SkeletonServices()
  {
  }

  @Override
  protected String getContentEncoding()
  {
    return Constants.WEBSERVICES_CONTENT_ENCODING;
  }

  @Override
  protected String getUrlEncoding()
  {
    return Constants.WEBSERVICES_HTML_ENCODING;
  }

  @Override
  protected final HttpClient computeHttpClient()
  {
    final HttpClient httpClient = super.computeHttpClient();
    final HttpParams httpParams = httpClient.getParams();
    HttpConnectionParams.setConnectionTimeout(httpParams, Constants.HTTP_CONNECTION_TIMEOUT_IN_MILLISECONDS);
    HttpConnectionParams.setSoTimeout(httpParams, Constants.HTTP_SOCKET_TIMEOUT_IN_MILLISECONDS);
    // We ask for the compressed flow
    // This is explained at http://code.google.com/intl/fr/appengine/kb/general.html#compression and
    // http://code.google.com/intl/fr/appengine/docs/java/runtime.html#Responses
    HttpProtocolParams.setUserAgent(httpParams, "gzip");
    httpParams.setParameter("Accept-Encoding", "gzip");
    return httpClient;
  }

  @Override
  protected final InputStream getContent(String uri, CallType callType, HttpResponse response)
      throws IOException
  {
    // Handles the compressed flow
    final Header[] contentEncodingHeaders = response.getHeaders(HTTP.CONTENT_ENCODING);
    if (contentEncodingHeaders != null && contentEncodingHeaders.length >= 1)
    {
      if (contentEncodingHeaders[0].getValue().equals("gzip") == true)
      {
        final InputStream inputStream = response.getEntity().getContent();
        final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        return gzipInputStream;
      }
    }
    return super.getContent(uri, callType, response);
  }

}
