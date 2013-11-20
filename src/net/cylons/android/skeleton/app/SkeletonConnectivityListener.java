package net.cylons.android.skeleton.app;

import android.content.Context;
import net.cylons.android.skeleton.ws.SkeletonServices;

import com.smartnsoft.droid4me.app.SmartApplication;
import com.smartnsoft.droid4me.app.Smarted;
import com.smartnsoft.droid4me.download.BitmapDownloader;
import com.smartnsoft.droid4me.ext.app.ConnectivityListener;

/**
 * Responsible for propagating connectivity events to all application components.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
public final class SkeletonConnectivityListener
    extends ConnectivityListener
{

  public SkeletonConnectivityListener(Context context)
  {
    super(context);
  }

  @Override
  protected void notifyServices(boolean hasConnectivity)
  {
    if (SmartApplication.isOnCreatedDone() == false)
    {
      return;
    }
    SkeletonServices.getInstance().setConnected(hasConnectivity);
    for (int index = 0; index < BitmapDownloader.INSTANCES_COUNT; index++)
    {
      BitmapDownloader.getInstance(index).setConnected(hasConnectivity);
    }
  }

  @Override
  protected void updateActivity(Smarted<?> smartedActivity)
  {
  }

}