/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.waterme.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.waterme.model.Plant
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

class WorkManagerWaterRepository(context: Context) : WaterRepository {
    // Lấy instance của WorkManager
    private val workManager = WorkManager.getInstance(context)
    override val plants: List<Plant> = DataSource.plants
    override fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String
    ) {

        //Tạo đối tượng Data để truyền dữ liệu cho Worker
        val data = Data.Builder()
            .putString(WaterReminderWorker.nameKey, plantName)
            .build()

        // Tạo Yêu cầu Công việc (WorkRequest)
        val reminderRequest = OneTimeWorkRequest.Builder(WaterReminderWorker::class.java)
            .setInitialDelay(duration, unit)
            .setInputData(data)
            .build()

        // Gửi Yêu cầu vào Hàng đợi của WorkManager
        val uniqueWorkName = "$plantName$duration"

        workManager.enqueueUniqueWork(
            uniqueWorkName,
            ExistingWorkPolicy.REPLACE,
            reminderRequest
        )
    }
}
