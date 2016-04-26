package io.mulberry.sample.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by yeongeon on 2/23/16.
 */
public class QueueMain<T> {
  private static Logger LOG = LoggerFactory.getLogger(QueueMain.class);


  protected final Integer size;
  private final Queue<T> queue;

  public QueueMain(Integer size){
    this.size = size;
    this.queue = new ArrayBlockingQueue<T>(this.size);

  }

  public void offer(T value){
    System.out.println("+ offer: "+ value);
    this.queue.offer(value);
    if(this.queue.size() >= this.size){
      System.out.println("!! Overflowed this.queue");
      this.queue.remove();
      System.out.println(">> after remove queue: "+ this.queue.size());
    }
  }

  public T poll(){
    return this.queue.poll();
  }

  public T peek(){
    return this.queue.peek();
  }

  public Queue<T> getQueue(){
    return this.queue;
  }

  public static void main(String[] args){
    Integer size = 5;
    QueueMain<Long> qm = new QueueMain<Long>(size);
    qm.offer(1L);
    qm.offer(2L);
    qm.offer(3L);
    qm.offer(4L);
    qm.offer(5L);
    qm.offer(6L);
    qm.offer(7L);
    qm.offer(8L);

    Iterator<Long> iterator = qm.getQueue().iterator();
    while (iterator.hasNext()){
      System.out.println(""+ iterator.next());
    }


  }

}
