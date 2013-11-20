package net.cylons.android.skeleton;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import net.cylons.android.skeleton.app.SkeletonActivityAggregate;

import com.smartnsoft.droid4me.LifeCycle.BusinessObjectsRetrievalAsynchronousPolicyAnnotation;
import com.smartnsoft.droid4me.app.SmartActivity;

/**
 * The starting screen of the application.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
@BusinessObjectsRetrievalAsynchronousPolicyAnnotation
public final class HomeActivity
    extends SmartActivity<SkeletonActivityAggregate>
{

  public void onRetrieveDisplayObjects()
  {
    setContentView(R.layout.home);
    // TODO Auto-generated method stub

  }

  public void onRetrieveBusinessObjects()
      throws BusinessObjectUnavailableException
  {
    // TODO Auto-generated method stub

  }

  public void onFulfillDisplayObjects()
  {
    // TODO Auto-generated method stub

  }

  public void onSynchronizeDisplayObjects()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    super.onCreateOptionsMenu(menu);

    menu.add(Menu.NONE, R.string.Home_menu_settings, Menu.NONE, R.string.Home_menu_settings).setIcon(android.R.drawable.ic_menu_preferences).setOnMenuItemClickListener(
        new MenuItem.OnMenuItemClickListener()
        {
          @Override
          public boolean onMenuItemClick(MenuItem item)
          {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
          }
        });
    menu.add(Menu.NONE, R.string.Home_menu_about, Menu.NONE, R.string.Home_menu_about).setIcon(android.R.drawable.ic_menu_info_details).setOnMenuItemClickListener(
        new MenuItem.OnMenuItemClickListener()
        {
          @Override
          public boolean onMenuItemClick(MenuItem item)
          {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
          }
        });

    return true;
  }

}
