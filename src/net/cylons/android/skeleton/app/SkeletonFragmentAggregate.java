package net.cylons.android.skeleton.app;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import net.cylons.android.skeleton.SkeletonApplication;

/**
 * An aggregate which can be shared by all {@link android.app.Fragment fragments}.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
public final class SkeletonFragmentAggregate
{

  private final android.app.Fragment fragment;

  private final Fragment supportFragment;

  public SkeletonFragmentAggregate(Object fragment)
  {
    if ("android.app.Fragment".equals(fragment.getClass().getName()) == true)
    {
      this.fragment = (android.app.Fragment) fragment;
      this.supportFragment = null;
    }
    else
    {
      this.fragment = null;
      this.supportFragment = (Fragment) fragment;
    }
  }

  @SuppressLint("NewApi")
  public SkeletonApplication getApplication()
  {
    if (fragment != null)
    {
      return (SkeletonApplication) fragment.getActivity().getApplication();
    }
    else
    {
      return (SkeletonApplication) supportFragment.getActivity().getApplication();
    }
  }

}
