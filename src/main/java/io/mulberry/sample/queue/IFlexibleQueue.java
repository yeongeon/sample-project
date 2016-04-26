package io.mulberry.sample.queue;

import java.util.Collection;
import java.util.Queue;

/**
 * Created by yeongeon on 2/23/16.
 */
public interface IFlexibleQueue<T> {
  void offer(T value);
  T poll();
  T peek();
  Queue<T> getQueue();
}
