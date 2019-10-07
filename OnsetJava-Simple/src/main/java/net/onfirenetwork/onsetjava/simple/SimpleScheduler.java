package net.onfirenetwork.onsetjava.simple;

import lombok.AllArgsConstructor;
import net.onfirenetwork.onsetjava.api.Scheduler;

import java.util.HashMap;
import java.util.Map;

public class SimpleScheduler implements Scheduler {

    private int nextId = 0;
    private Map<Integer, SchedulerTask> tasks = new HashMap<>();

    public void start(){
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    tick();
                } catch (Exception ex){}
            }
        }).start();
    }

    private void tick(){
        for(int id : tasks.keySet()){
            long tick = System.currentTimeMillis();
            SchedulerTask task = tasks.get(id);
            if(task.delay != -1){
                if(tick >= task.lastTick + task.delay){
                    task.runnable.run();
                    task.lastTick = tick;
                    task.delay = -1;
                    if(task.interval == -1){
                        tasks.remove(id);
                    }
                }
            }else{
                if(tick >= task.lastTick + task.interval){
                    task.runnable.run();
                    task.lastTick = tick;
                }
            }
        }
    }

    public void unschedule(int id){
        tasks.remove(id);
    }

    public int schedule(long delay, Runnable run){
        int id = nextId;
        nextId++;
        tasks.put(id, new SchedulerTask(System.currentTimeMillis(), delay, -1, run));
        return id;
    }

    public int scheduleRepeating(long delay, long interval, Runnable run){
        int id = nextId;
        nextId++;
        tasks.put(id, new SchedulerTask(System.currentTimeMillis(), delay, interval, run));
        return id;
    }

    @AllArgsConstructor
    private class SchedulerTask {
        long lastTick;
        long delay;
        long interval;
        Runnable runnable;
    }

}
