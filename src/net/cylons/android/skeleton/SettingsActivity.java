package net.cylons.android.skeleton;

import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.Preference;
import net.cylons.android.skeleton.app.SkeletonActivityAggregate;

import com.smartnsoft.droid4me.LifeCycle.BusinessObjectsRetrievalAsynchronousPolicyAnnotation;
import com.smartnsoft.droid4me.app.SmartPreferenceActivity;

/**
 * The activity which enables to tune the application.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
@BusinessObjectsRetrievalAsynchronousPolicyAnnotation
public final class SettingsActivity
    extends SmartPreferenceActivity<SkeletonActivityAggregate>
{

  @SuppressWarnings("deprecation")
  public void onRetrieveDisplayObjects()
  {
    addPreferencesFromResource(R.xml.settings);
    {
      final Preference versionPreference = findPreference("version");
      try
      {
        versionPreference.setSummary(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
      }
      catch (NameNotFoundException exception)
      {
        if (log.isErrorEnabled())
        {
          log.error("Cannot determine the application version name", exception);
        }
        versionPreference.setSummary("???");
      }
    }
  }

}
