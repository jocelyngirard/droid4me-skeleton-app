package net.cylons.android.skeleton;

import net.cylons.android.skeleton.app.SkeletonActivityAggregate;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

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

  private DrawerLayout drawerLayout;

  private ActionBarDrawerToggle drawerToggle;

  @Override
  public void onRetrieveDisplayObjects()
  {
    setContentView(R.layout.home);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.Home_menu_about, R.string.Home_menu_settings);
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerLayout.setDrawerListener(drawerToggle);
    drawerToggle.syncState();
    setActionBarBehavior();
  }

  @Override
  public void onRetrieveBusinessObjects()
      throws BusinessObjectUnavailableException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void onFulfillDisplayObjects()
  {
    // TODO Auto-generated method stub

  }

  @Override
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

  private void setActionBarBehavior()
  {

    if (getActionBar() != null)
    {
      drawerToggle.setDrawerIndicatorEnabled(true);
      getActionBar().setDisplayHomeAsUpEnabled(true);
      getActionBar().setDisplayShowHomeEnabled(true);
      getActionBar().setHomeButtonEnabled(true);
      getActionBar().setDisplayShowTitleEnabled(true);

      // getActionBar().setDisplayShowTitleEnabled(false);
      // getActionBar().setDisplayUseLogoEnabled(false);
      // getActionBar().setDisplayHomeAsUpEnabled(true);
      // getActionBar().setDisplayShowHomeEnabled(true);
      // getActionBar().setHomeButtonEnabled(true);
      // final LogoIconTitleBehavior logoIconTitleBehavior = parameters.logoIconTitleBehavior();
      // if (logoIconTitleBehavior == LogoIconTitleBehavior.UseLogo)
      // {
      // getActionBar().setDisplayShowTitleEnabled(false);
      // getActionBar().setDisplayUseLogoEnabled(true);
      // getActionBar().setLogo(R.drawable.bar_accor_logo);
      // }
      // else if (logoIconTitleBehavior == LogoIconTitleBehavior.UseIcon)
      // {
      // getActionBar().setIcon(R.drawable.ic_bar_menu);
      // }
      // else if (logoIconTitleBehavior == LogoIconTitleBehavior.UseTitle)
      // {
      // getActionBar().setDisplayShowTitleEnabled(true);
      // getActionBar().setIcon(android.R.color.transparent);
      // }
      //
      // if (parameters.menuBehavior() == MenuBehavior.ShowUp)
      // {
      // drawerToggle.setDrawerIndicatorEnabled(false);
      // getActionBar().setDisplayShowHomeEnabled(false);
      // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
      // }
      // else if (parameters.menuBehavior() == MenuBehavior.ShowDrawer)
      // {
      // drawerToggle.setDrawerIndicatorEnabled(true);
      // }
      // else
      // {
      // getActionBar().setDisplayShowHomeEnabled(false);
      // getActionBar().setDisplayHomeAsUpEnabled(false);
      // drawerToggle.setDrawerIndicatorEnabled(false);
      // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
      // }
    }
  }

  public void toggleDrawer()
  {
    if (drawerToggle.isDrawerIndicatorEnabled() == true)
    {
      if (drawerLayout.isDrawerOpen(Gravity.LEFT) == false)
      {
        drawerLayout.openDrawer(Gravity.LEFT);
      }
      else
      {
        drawerLayout.closeDrawers();
      }
    }
    else
    {
      onBackPressed();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    /**
     * Set custom action on ActionBar home button to open SlidingMenu.
     */
    switch (item.getItemId())
    {
    case android.R.id.home:
      toggleDrawer();
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }

}
