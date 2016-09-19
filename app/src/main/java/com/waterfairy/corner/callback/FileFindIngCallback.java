package com.waterfairy.corner.callback;

/**
 * Created by shui on 2016/9/19.
 */
public interface FileFindIngCallback<T> {
    void onSuccess(T t);

    void onFailed(String msg);

    void onFinding();

}
