package com.zavier.licenses.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.zavier.licenses.utils.UserContextHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发策略
 */
public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy existConcurrencyStrategy;

    public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existConcurrencyStrategy) {
        this.existConcurrencyStrategy = existConcurrencyStrategy;
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return existConcurrencyStrategy != null
                ?
                existConcurrencyStrategy.getBlockingQueue(maxQueueSize)
                :
                super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return existConcurrencyStrategy != null
                ?
                existConcurrencyStrategy.getRequestVariable(rv)
                :
                super.getRequestVariable(rv);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return existConcurrencyStrategy != null
                ?
                existConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
                :
                super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return existConcurrencyStrategy != null
                ?
                existConcurrencyStrategy.wrapCallable(DelegatingUserContextCallable.create(callable, UserContextHolder.getContext()))
                :
                super.wrapCallable(DelegatingUserContextCallable.create(callable, UserContextHolder.getContext()));
    }


}
