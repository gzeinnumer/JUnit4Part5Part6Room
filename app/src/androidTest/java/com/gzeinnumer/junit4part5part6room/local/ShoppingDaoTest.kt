package com.gzeinnumer.junit4part5part6room.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Root
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.gzeinnumer.junit4part5part6room.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//todo 5
@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: ShoppingItemDatabase
    lateinit var dao: ShoppingDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertShoopingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)

        dao.insertShoppingItem(shoppingItem)

        val  allShopingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShopingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingitems() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)

        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observerTotalPerSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name1", 1, 1f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name2", 2, 2f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name3", 3, 3f, "url", id = 3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo((1*1f)+(2*2f)+(3*3f))
    }
}