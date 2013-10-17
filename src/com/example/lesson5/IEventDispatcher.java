package com.example.lesson5;

public interface IEventDispatcher
{
    void addEventListener(String type, IEventHadler listener);
    void removeEventListener(String type, IEventHadler listener);
    void dispatchEvent(Event e);
    boolean hasEventListener(String type);
}
