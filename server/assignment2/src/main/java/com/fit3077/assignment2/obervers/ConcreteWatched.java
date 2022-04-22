package com.fit3077.assignment2.obervers;

import java.util.ArrayList;
import java.util.List;
public class ConcreteWatched implements Watched
{
    //Depository observer
    private List<Watcher> list = new ArrayList<Watcher>();
    @Override
    public void addWatcher(Watcher watcher)
    {
        list.add(watcher);
    }
    @Override
    public void removeWatcher(Watcher watcher)
    {
        list.remove(watcher);
    }
    @Override
    public void clearAllWatchers()
    {
        list.clear();
    }
    @Override
    public void notifyWatchers(String str)
    {
        //The auto-invocation is actually invoked by the topic
        for (Watcher watcher : list)
        {
            watcher.update(str);
        }
    }
}
