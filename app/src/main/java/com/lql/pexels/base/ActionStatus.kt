package com.lql.pexels.base

sealed class ActionStatus {
    class Start: ActionStatus()
    class Failed(val error: Throwable): ActionStatus()
    class Completed: ActionStatus()
}
