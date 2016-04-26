package io.mulberry.sample.queue;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yeongeon on 2/23/16.
 */
/*
  {
    interval: 1000,
    duration: 1000,
    timeunit: java.util.concurrent.TimeUnit.MILLISECONDS.name(),
    lasttime: System.currentTimeMillis(),
    metrics : {
      key1 : [10, 20, 30, 10, 20, 40, 50],
      key2 : [10, 20, 30, 10, 20, 40, 50],
      key3 : [10, 20, 30, 10, 20, 40, 50],
      key4 : [10, 20, 30, 10, 20, 40, 50],
      key5 : [10, 20, 30, 10, 20, 40, 50],
      key6 : [10, 20, 30, 10, 20, 40, 50]
    }
  }

  ThreadableMetricsChunk {
    int interval;       // 1000
    int duration;       // 1000
    TimeUnit timeunit;  // java.util.concurrent.TimeUnit.MILLISECONDS.name()
    long lasttime;      // System.currentTimeMillis()
    Map<String, Queue<?>> metrics;    // new LinkedList();
    public addMetric(String key, ? value);
  }
*/
public class ThreadableMetricsChunk implements Closeable {

  private long interval = 1000;
  private long duration = 5*60*1000;
  private TimeUnit timeunit = java.util.concurrent.TimeUnit.MILLISECONDS;
  private long lasttime = System.currentTimeMillis();
  private Map<String, FlexibleQueue<Long>> metrics = new ConcurrentHashMap<String, FlexibleQueue<Long>>();

  protected AtomicBoolean stop = new AtomicBoolean(false);
  private Thread collectorThread;

  private class Collector<T> implements Runnable {
    final private T obj;

    private Collector(T obj) {
      this.obj = obj;
    }

    @Override
    public void run() {
      while(!stop.get()){
        try {
          Thread.sleep(interval);

          obj.hashCode();
          String key = "";


          FlexibleQueue<Long> q;
          if(metrics.containsKey(key)){
            q = metrics.get(key);
          } else {
            q = new FlexibleQueue<Long>(interval, duration);
          }
          q.offer(0L);
        } catch (InterruptedException e) {
        }
      }
    }
  }

  public ThreadableMetricsChunk(Long interval){
    this(interval, null);
  }

  public ThreadableMetricsChunk(Long interval, Long duration){
    if(interval != null) {
      this.interval = interval;
    }
    if(duration != null){
      this.duration = duration;
    }
  }

  public void start(){
    Object obj = new Object();
    collectorThread = new Thread(new Collector<Object>(obj));
    stop.set(false);
    collectorThread.start();
  }

  public Map<String, FlexibleQueue<Long>> getMetrics(){
    return this.metrics;
  }

  public void restart(Long interval) throws Exception {
    this.restart(interval, null);
  }

  public void restart(Long interval, Long duration) throws Exception {
    if(interval != null) {
      this.interval = interval;
    }
    if(duration != null){
      this.duration = duration;
    }
  }

  @Override
  public void close() throws IOException {
    stop.set(true);
    collectorThread.interrupt();
    try{
      collectorThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
