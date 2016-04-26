package io.mulberry.sample.queue;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class FlexibleQueue<T> implements IFlexibleQueue<T> {

  final private TimeUnit timeunit = TimeUnit.MILLISECONDS;

  private long interval = 1000;
  private long duration = 5*60*1000;

  protected int size = (int)Math.ceil(duration/interval);
  private Queue<T> queue;

  public FlexibleQueue(){
    this(null);
  }

  public FlexibleQueue(Long interval){
    this(interval, null);
  }

  public FlexibleQueue(Long interval, Long duration){
    if(interval != null) {
      this.interval = interval;
    }
    if(duration != null){
      this.duration = duration;
    }

    init();
  }

  private void init(){
    this.size = (int)Math.ceil(this.duration/this.interval);
    this.queue = new ArrayBlockingQueue<T>(this.size);
  }

  public void resize(Long duration){
    if(duration != null){
      this.duration = duration;
    }
    this.size = (int)Math.ceil(this.duration/this.interval);
    if(this.queue!=null){
      synchronized (this.queue){
        Queue<T> tq = new ArrayBlockingQueue<T>(this.queue.size());
        this.queue = new ArrayBlockingQueue<T>(this.size);
        this.queue.addAll(tq);
      }
    }
  }

  @Override
  public void offer(T value){
    if(this.queue.size() >= this.size){
      this.queue.remove();
    }
    this.queue.offer(value);
  }

  @Override
  public T poll(){
    return this.queue.poll();
  }

  @Override
  public T peek(){
    return this.queue.peek();
  }

  @Override
  public Queue<T> getQueue(){
    return this.queue;
  }
}