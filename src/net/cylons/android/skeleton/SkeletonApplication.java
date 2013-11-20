package net.cylons.android.skeleton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import net.cylons.android.skeleton.app.SkeletonConnectivityListener;
import net.cylons.android.skeleton.app.SkeletonInterceptor;

import com.smartnsoft.droid4me.LifeCycle.BusinessObjectUnavailableException;
import com.smartnsoft.droid4me.app.ActivityController;
import com.smartnsoft.droid4me.app.ActivityController.AbstractExceptionHandler;
import com.smartnsoft.droid4me.app.ActivityController.SystemServiceProvider;
import com.smartnsoft.droid4me.app.Droid4mizer;
import com.smartnsoft.droid4me.app.SmartApplication;
import com.smartnsoft.droid4me.bo.Business.InputAtom;
import com.smartnsoft.droid4me.cache.DbPersistence;
import com.smartnsoft.droid4me.cache.Persistence;
import com.smartnsoft.droid4me.download.BasisDownloadInstructions;
import com.smartnsoft.droid4me.download.BitmapDownloader;
import com.smartnsoft.droid4me.download.DownloadInstructions;
import com.smartnsoft.droid4me.download.DownloadSpecs;
import com.smartnsoft.droid4me.ext.app.ConnectivityListener;
import com.smartnsoft.droid4me.ext.view.SmartLayoutInflater;
import com.smartnsoft.droid4me.ext.view.SmartLayoutInflater.OnViewInflatedListener;
import com.smartnsoft.droid4me.ext.widget.SmartRowListAdapter;
import com.smartnsoft.droid4me.ws.WebServiceCaller;

/**
 * The entry point of the application.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
@ReportsCrashes(formUri = "http://www.bugsense.com/api/acra?api_key=" + Constants.BUG_SENSE_APPLICATION_ID, formKey = "")
public final class SkeletonApplication
    extends SmartApplication
{

  public static class CacheInstructions
      extends DownloadInstructions.AbstractInstructions
  {

    @Override
    public Bitmap hasTemporaryBitmap(View imageView, String imageUid, Object imageSpecs)
    {
      if (imageSpecs instanceof DownloadSpecs.TemporaryImageSpecs)
      {
        final DownloadSpecs.TemporaryImageSpecs temporaryImageSpecs = (DownloadSpecs.TemporaryImageSpecs) imageSpecs;
        return temporaryImageSpecs.imageResourceId != -1 ? BitmapFactory.decodeResource(imageView.getContext().getResources(),
            temporaryImageSpecs.imageResourceId) : null;
      }
      return null;
    }

    @Override
    public void onBindTemporaryBitmap(View imageView, Bitmap bitmap, String imageUid, Object imageSpecs)
    {
      ((ImageView) imageView).setImageBitmap(bitmap);
    }

    @Override
    public boolean onBindBitmap(boolean downloaded, View imageView, Bitmap bitmap, String imageUid, Object imageSpecs)
    {
      ((ImageView) imageView).setImageBitmap(bitmap);
      return true;
    }

    @Override
    public InputStream getInputStream(String imageUid, Object imageSpecs, String url, BasisDownloadInstructions.InputStreamDownloadInstructor downloadInstructor)
        throws IOException
    {
      final InputAtom inputAtom = Persistence.getInstance(SkeletonApplication.IMAGES_IMAGES_PERSISTENCE_INSTANCE).extractInputStream(url);
      return inputAtom == null ? null : inputAtom.inputStream;
    }

    @Override
    public InputStream onInputStreamDownloaded(String imageUid, Object imageSpecs, String url, InputStream inputStream)
    {
      final InputAtom inputAtom = Persistence.getInstance(SkeletonApplication.IMAGES_IMAGES_PERSISTENCE_INSTANCE).flushInputStream(url,
          new InputAtom(new Date(), inputStream));
      return inputAtom == null ? null : inputAtom.inputStream;
    }

  }

  public static final int DATA_PERSISTENCE_INSTANCE = 0;

  public static final int IMAGES_IMAGES_PERSISTENCE_INSTANCE = 1;

  public final static DownloadInstructions.Instructions CACHE_IMAGE_INSTRUCTIONS = new SkeletonApplication.CacheInstructions();

  private ConnectivityListener connectivityListener;

  public ConnectivityListener getConnectivityListener()
  {
    return connectivityListener;
  }

  @Override
  protected int getLogLevel()
  {
    return Constants.LOG_LEVEL;
  }

  @Override
  protected SmartApplication.I18N getI18N()
  {
    return new SmartApplication.I18N(getText(R.string.problem), getText(R.string.unavailableItem), getText(R.string.unavailableService), getText(R.string.connectivityProblem), getText(R.string.connectivityProblemRetry), getText(R.string.unhandledProblem), getString(R.string.applicationName), getText(R.string.dialogButton_unhandledProblem), getString(R.string.progressDialogMessage_unhandledProblem));
  }

  @Override
  protected String getLogReportRecipient()
  {
    return Constants.REPORT_LOG_RECIPIENT_EMAIL;
  }

  @Override
  protected void onSetupExceptionHandlers()
  {
    // We initialize ACRA
    ACRA.init(this);
    ACRA.getACRASharedPreferences().edit().putBoolean(ACRA.PREF_ENABLE_ACRA, Constants.ACRA_ENABLED).commit();
  }

  @Override
  public void onCreateCustom()
  {
    // We tune droid4me's logging level and other components log level
    if (Constants.DEVELOPMENT_MODE == true)
    {
      Droid4mizer.ARE_DEBUG_LOG_ENABLED = true;
      WebServiceCaller.ARE_DEBUG_LOG_ENABLED = true;
      SmartLayoutInflater.DEBUG_LOG_ENABLED = true;
      SmartRowListAdapter.DEBUG_LOG_ENABLED = true;
    }

    super.onCreateCustom();

    logDeviceInformation();

    // We listen to the network connectivity events
    connectivityListener = new SkeletonConnectivityListener(getApplicationContext());

    // We initialize the persistence: the storage directory should be locale-independent!
    final String directoryName = Constants.APPLICATION_FOLDER_NAME;
    final File contentsDirectory = new File(Environment.getExternalStorageDirectory(), directoryName);
    Persistence.CACHE_DIRECTORY_PATHS = new String[] { contentsDirectory.getAbsolutePath(), contentsDirectory.getAbsolutePath() };
    DbPersistence.FILE_NAMES = new String[] { DbPersistence.DEFAULT_FILE_NAME, DbPersistence.DEFAULT_FILE_NAME };
    DbPersistence.TABLE_NAMES = new String[] { "data", "images" };
    Persistence.INSTANCES_COUNT = 2;
    Persistence.IMPLEMENTATION_FQN = DbPersistence.class.getName();

    // We set the BitmapDownloader instances
    BitmapDownloader.IS_DEBUG_TRACE = false;
    BitmapDownloader.IS_DUMP_TRACE = false;
    BitmapDownloader.INSTANCES_COUNT = 1;
    BitmapDownloader.HIGH_LEVEL_MEMORY_WATER_MARK_IN_BYTES = new long[] { 3 * 1024 * 1024 };
    BitmapDownloader.LOW_LEVEL_MEMORY_WATER_MARK_IN_BYTES = new long[] { 1 * 1024 * 1024 };
    BitmapDownloader.setPreThreadPoolSize(4);
    BitmapDownloader.setDownloadThreadPoolSize(4);
  }

  @Override
  protected ActivityController.Redirector getActivityRedirector()
  {
    return new ActivityController.Redirector()
    {
      public Intent getRedirection(Activity activity)
      {
        if (SkeletonSplashScreenActivity.isInitialized(SkeletonSplashScreenActivity.class) == null)
        {
          // We re-direct to the splash screen activity if the application has not been yet initialized
          if (activity instanceof SkeletonSplashScreenActivity)
          {
            return null;
          }
          else
          {
            return new Intent(activity, SkeletonSplashScreenActivity.class);
          }
        }
        return null;
      }
    };
  }

  @Override
  protected ActivityController.Interceptor getInterceptor()
  {
    final SkeletonInterceptor applicationInterceptor = new SkeletonInterceptor();
    return new ActivityController.Interceptor()
    {
      public void onLifeCycleEvent(Activity activity, Object component, ActivityController.Interceptor.InterceptorEvent event)
      {
        applicationInterceptor.onLifeCycleEvent(activity, component, event);
        connectivityListener.onLifeCycleEvent(activity, component, event);
      }
    };
  }

  @Override
  protected ActivityController.ExceptionHandler getExceptionHandler()
  {
    return new DefaultExceptionHandler(getI18N())
    {

      @Override
      public boolean onBusinessObjectAvailableException(Activity activity, Object component, BusinessObjectUnavailableException exception)
      {
        if (AbstractExceptionHandler.isAConnectivityProblem(exception) == false)
        {
          ACRA.getErrorReporter().handleSilentException(exception);
        }
        return super.onBusinessObjectAvailableException(activity, component, exception);
      }

      @Override
      public boolean onContextException(Context context, Object component, Throwable throwable)
      {
        if (AbstractExceptionHandler.isAConnectivityProblem(throwable) == false)
        {
          ACRA.getErrorReporter().handleSilentException(throwable);
        }
        return super.onContextException(context, component, throwable);
      }

      @Override
      public boolean onOtherException(Activity activity, Object component, Throwable throwable)
      {
        if (AbstractExceptionHandler.isAConnectivityProblem(throwable) == false)
        {
          ACRA.getErrorReporter().handleSilentException(throwable);
        }
        return super.onOtherException(activity, component, throwable);
      }

    };
  }

  @Override
  protected SystemServiceProvider getSystemServiceProvider()
  {
    final OnViewInflatedListener onViewInflatedListener = new OnViewInflatedListener()
    {

      @Override
      public void onViewInflated(Context context, View view, AttributeSet attrs)
      {
      }

    };
    return new ActivityController.SystemServiceProvider()
    {

      @Override
      public Object getSystemService(final Activity activity, String name, Object defaultService)
      {
        if (LAYOUT_INFLATER_SERVICE.equals(name) == true)
        {
          // if (log.isDebugEnabled())
          // {
          // log.debug("Asking for the layout system service");
          // }
          final LayoutInflater defaultLayoutInflater = (LayoutInflater) defaultService;
          return SmartLayoutInflater.getLayoutInflater(defaultLayoutInflater, activity, onViewInflatedListener);
        }
        return defaultService;
      }

    };
  }

}
