package com.coepzest.zest23;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventsViewModel extends ViewModel {

    private final MutableLiveData<String> selectedEvent=new MutableLiveData<String>();

    public void setSelectedEvent(String EventName){
        selectedEvent.setValue(EventName);
    }

    public LiveData<String> getSelectedEvent(){
        return selectedEvent;
    }
}
