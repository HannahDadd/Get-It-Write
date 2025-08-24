package hannah.bd.getitwrite.views.commitments

//@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun CommitmentCTA() {
//    val context = LocalContext.current
//    val commitFlow = loadCommitment(context).collectAsState(initial = Triple(false, LocalTime.now().toSecondOfDay() * 1000L, emptySet()))
//    var (notifEnabled, timeMillis, daysSet) by remember { mutableStateOf(commitFlow.value) }
//
//    val notifPerm = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
//
//    LaunchedEffect(Unit) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notifPerm.status.isGranted)
//            notifPerm.launchPermissionRequest()
//    }
//
//    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//        Text("Weekly Writing Schedule", style = MaterialTheme.typography.titleLarge)
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("Writing notification")
//            Spacer(Modifier.weight(1f))
//            Switch(checked = notifEnabled, onCheckedChange = { enabled ->
//                notifEnabled = enabled
//                if (!enabled) AlarmScheduler.cancelAll(context)
//                saveCommitment(enabled, timeMillis, daysSet, context)
//                if (enabled) AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
//            })
//        }
//        if (notifEnabled) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                val time = remember { mutableStateOf(LocalTime.ofSecondOfDay(timeMillis / 1000L)) }
//                val showTimePicker = remember { mutableStateOf(false) }
//                Text("Time: ${time.value.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))}")
//                Spacer(Modifier.weight(1f))
//                Button(onClick = { showTimePicker.value = true }) { Text("Pick Time") }
//                if (showTimePicker.value) {
//                    TimePickerDialog(
//                        onTimeSelected = { h, m ->
//                            val newTime = LocalTime.of(h, m)
//                            time.value = newTime
//                            timeMillis = newTime.toSecondOfDay() * 1000L
//                            AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
//                            saveCommitment(notifEnabled, timeMillis, daysSet, context)
//                        },
//                        onDismissRequest = { showTimePicker.value = false }
//                    )
//                }
//            }
//            Text("Days:")
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                listOf("Mon","Tue","Wed","Th","Fri","Sat","Sun").forEach { d ->
//                    val selected = daysSet.contains(d)
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            imageVector = if (selected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
//                            contentDescription = d,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clickable {
//                                    daysSet = if (selected) daysSet - d else daysSet + d
//                                    AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
//                                    saveCommitment(notifEnabled, timeMillis, daysSet, context)
//                                }
//                        )
//                        Text(d)
//                    }
//                }
//            }
//        }
//    }
//
//    fun scheduleWeeklyWork(context: Context, days: Set<String>, hour: Int, minute: Int) {
//        val workManager = getInstance(context)
//        days.forEach { day ->
//            val delay = computeDelayToNext(day, hour, minute)
//            val request = PeriodicWorkRequestBuilder<WritingReminderWorker>(7, TimeUnit.DAYS)
//                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//                .addTag("writing_$day")
//                .build()
//            workManager.enqueueUniquePeriodicWork(
//                "writing_$day",
//                ExistingPeriodicWorkPolicy.REPLACE,
//                request
//            )
//        }
//    }
//
//    fun cancelWeeklyWork(context: Context, days: Set<String>) {
//        val workManager = getInstance(context)
//        days.forEach { day ->
//            workManager.cancelAllWorkByTag("writing_$day")
//        }
//    }
//
//}
//
//class WritingReminderWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
//    override fun doWork(): Result {
//        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle("Let's get writing!")
//            .setContentText("Open Get It Write and start your writing sprint now!")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//        NotificationManagerCompat.from(applicationContext).notify(Random.nextInt(), builder.build())
//        return Result.success()
//    }
//}