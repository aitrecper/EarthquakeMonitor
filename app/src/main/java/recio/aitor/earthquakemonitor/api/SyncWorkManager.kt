package recio.aitor.earthquakemonitor.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorkManager(appContext: Context, params: WorkerParameters):CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}