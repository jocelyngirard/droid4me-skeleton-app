package net.cylons.android.skeleton.app;

import android.app.Activity;

import com.smartnsoft.droid4me.app.ActivityController;
import com.smartnsoft.droid4me.app.Smartable;

/**
 * Is responsible for intercepting life-cycle events.
 * 
 * @author Jocelyn Girard
 * @since 2013.11.20
 */
public final class SkeletonInterceptor
    implements ActivityController.Interceptor
{

  @SuppressWarnings("unchecked")
  @Override
  public void onLifeCycleEvent(Activity activity, Object component, InterceptorEvent interceptorEvent)
  {
    if (component != null)
    {
      // It's a Fragment
      final Smartable<SkeletonFragmentAggregate> smartableFragment = (Smartable<SkeletonFragmentAggregate>) component;
      if (interceptorEvent == InterceptorEvent.onSuperCreateBefore)
      {
        smartableFragment.setAggregate(new SkeletonFragmentAggregate(smartableFragment));
      }
    }
    else
    {
      // It's an Activity
      final Smartable<SkeletonActivityAggregate> smartableActivity = (Smartable<SkeletonActivityAggregate>) activity;
      if (interceptorEvent == InterceptorEvent.onSuperCreateBefore)
      {
        smartableActivity.setAggregate(new SkeletonActivityAggregate(activity));
      }
    }
  }

}
