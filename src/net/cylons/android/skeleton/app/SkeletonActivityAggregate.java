package net.cylons.android.skeleton.app;

import android.app.Activity;
import net.cylons.android.skeleton.SkeletonApplication;

/**
 * An aggregate which can be shared by all {@link Activity activities}.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
public final class SkeletonActivityAggregate
{

  private final Activity activity;

  public SkeletonActivityAggregate(Activity activity)
  {
    this.activity = activity;
  }

  public SkeletonApplication getApplication()
  {
    return (SkeletonApplication) activity.getApplication();
  }

}
