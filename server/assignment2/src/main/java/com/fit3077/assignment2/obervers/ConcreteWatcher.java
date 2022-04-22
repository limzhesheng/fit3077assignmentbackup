package com.fit3077.assignment2.obervers;

public class ConcreteWatcher implements Watcher
{
    @Override
    public void update(String str)
    {
        System.out.println(str);
    }
}