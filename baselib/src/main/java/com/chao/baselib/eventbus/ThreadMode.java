package com.chao.baselib.eventbus;

public enum ThreadMode {
    POSTING,
    MAIN,
    BACKGROUND,
    ASYNC;

    private ThreadMode() {
    }
}