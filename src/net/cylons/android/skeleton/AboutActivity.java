package net.cylons.android.skeleton;

import net.cylons.android.skeleton.app.SkeletonActivityAggregate;
import android.webkit.WebView;

import com.smartnsoft.droid4me.LifeCycle.BusinessObjectsRetrievalAsynchronousPolicyAnnotation;
import com.smartnsoft.droid4me.app.SmartActivity;

/**
 * The "about" screen.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
@BusinessObjectsRetrievalAsynchronousPolicyAnnotation
public final class AboutActivity
    extends SmartActivity<SkeletonActivityAggregate>
{

  private WebView webView;

  public void onRetrieveDisplayObjects()
  {
    setContentView(R.layout.about);
    webView = (WebView) findViewById(R.id.webView);
    webView.getSettings().setSupportMultipleWindows(false);
    webView.getSettings().setSupportZoom(false);
    webView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
  }

  public void onRetrieveBusinessObjects()
      throws BusinessObjectUnavailableException
  {
  }

  public void onFulfillDisplayObjects()
  {
  }

  public void onSynchronizeDisplayObjects()
  {
  }

}
