package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Khushi Travels", appName)
  }

  @Test
  fun `verify application bus drawables exist`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val urbaniaRes = context.resources.getDrawable(R.drawable.img_bus_urbania, context.theme)
    val volvoRes = context.resources.getDrawable(R.drawable.img_bus_volvo, context.theme)
    val maharajaRes = context.resources.getDrawable(R.drawable.img_bus_maharaja, context.theme)
    val heroRes = context.resources.getDrawable(R.drawable.img_hero_mountain, context.theme)

    assertNotNull(urbaniaRes)
    assertNotNull(volvoRes)
    assertNotNull(maharajaRes)
    assertNotNull(heroRes)
  }
}
