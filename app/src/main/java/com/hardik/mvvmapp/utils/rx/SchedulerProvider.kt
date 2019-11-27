package com.hardik.mvvmapp.utils.rx

import io.reactivex.Scheduler

/**
 * Created by Hardik on 27/08/18.
 */
interface SchedulerProvider {

  fun computation(): Scheduler

  fun io(): Scheduler

  fun ui(): Scheduler
}