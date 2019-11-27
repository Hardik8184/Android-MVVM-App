package com.hardik.mvvmapp.rx

import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val testScheduler: TestScheduler) :
    SchedulerProvider {

  override fun ui(): Scheduler = testScheduler
  override fun computation(): Scheduler = testScheduler
  override fun io(): Scheduler = testScheduler
}
