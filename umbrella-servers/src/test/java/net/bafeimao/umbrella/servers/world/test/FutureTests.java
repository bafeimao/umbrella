/*
 * Copyright 2002-2015 by bafeimao.net, The umbrella Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bafeimao.umbrella.servers.world.test;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/11.
 *
 * @author gukaitong
 * @since 1.0
 */
public class FutureTests {
    @Test
    public void testGetResult() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5); // 模拟任务执行时间
                return "success";
            }
        });

        String result = future.get();

        System.out.println(result); // success
        System.out.println(future.isCancelled()); // false;
        System.out.println(future.isDone()); // true
    }

    @Test(expected = TimeoutException.class)
    public void testGetResultWithTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                return "success";
            }
        });

        // 指定时间内，任务还没有返回，因此该方法返回TimeoutException
        String result = future.get(3, TimeUnit.SECONDS);
        System.out.println(result);
    }

    @Test
    public void testCancelFuture() throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                return "success";
            }
        });

        // 开启一个线程，该线程启动后3秒后将future取消掉，这将导致future.get方法执行得到一个CancellationException
        new Thread() {
            @Override
            public void run() {
                System.out.println("Future will be cancelled after 3 seconds." + future.isCancelled());

                try {
                    TimeUnit.SECONDS.sleep(3);
                    future.cancel(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            String result = future.get();

            // 不会被执行，因为future任务被另一个线程终止
            System.out.println(result);
        } catch (CancellationException e) {

            // cancel后的future的isDone()和isCancelled()都返回true
            System.out.println(future.isCancelled()); // true
            System.out.println(future.isDone()); // true,
        }
    }
}
