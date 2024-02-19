package com.example.androidtechnicalproject

import com.example.androidtechnicalproject.roomDatabase.RoomDatabaseTest
import com.example.androidtechnicalproject.apiTest.TestApiIsolation
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    RoomDatabaseTest::class,
    TestApiIsolation::class,
)
class AndroidTestMain