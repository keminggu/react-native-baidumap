package com.yiyang.reactnativebaidumap;

import android.support.annotation.IntDef;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nullable;

/**
 * Created by gukeming on 2015/12/24.
 * Eamil:342802259@qq.com
 */

public class MapEvent extends Event<MapEvent> {

    @IntDef({ON_ERROR, ON_CHANGE, ON_PRESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface MapEventType {}

    // Currently ON_ERROR and ON_PROGRESS are not implemented, these can be added
    // easily once support exists in fresco.
    public static final int ON_ERROR = 1;
    public static final int ON_CHANGE = 2;
    public static final int ON_PRESS = 3;

    private final int mEventType;
    private @Nullable WritableMap mEvent;

    public MapEvent(int viewId, long timestampMs, @MapEventType int eventType) {
        super(viewId, timestampMs);
        mEventType = eventType;
    }

    public MapEvent(int viewId, long timestampMs, @MapEventType int eventType, @Nullable WritableMap event) {
        super(viewId, timestampMs);
        mEventType = eventType;
        mEvent = event;
    }

    public static String eventNameForType(@MapEventType int eventType) {
        switch(eventType) {
            case ON_ERROR:
                return "topError";
            case ON_CHANGE:
                return "topChange";
            case ON_PRESS:
                return "topPress";
            default:
                throw new IllegalStateException("Invalid event: " + Integer.toString(eventType));
        }
    }

    @Override
    public String getEventName() {
        return MapEvent.eventNameForType(mEventType);
    }

    @Override
    public short getCoalescingKey() {
        // Intentionally casting mEventType because it is guaranteed to be small
        // enough to fit into short.
        return (short) mEventType;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), mEvent);
    }
}
