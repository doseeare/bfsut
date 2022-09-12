package pro.breez.bfsut.util

interface LifecycleEvent {
    fun onCreate()
    fun onResume()
    fun onStart()
    fun onPause()
    fun onStop()
    fun onDestroy()
}