package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel

import androidx.lifecycle.Lifecycle
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseFragmentLifecycleObserverTest
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceFragmentLifecycleObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Verifies behavior of the users lifecycle observer.
 *
 * @author John Sung, Geico
 */
@RunWith(MockitoJUnitRunner::class)
class AceUsersLifecycleObserverTest : AceBaseFragmentLifecycleObserverTest<AceUsersViewModel>() {

    @Mock
    private lateinit var mockViewModel: AceUsersViewModel


    override fun createLifecycleObserver(): AceFragmentLifecycleObserver<AceUsersViewModel> {
        return AceUserLifecycleObserver(mockViewModel)
    }

    @Test
    fun testOnCreate() {
        handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        verify(mockViewModel).initializeModel()
    }

    @Test
    fun testOnResume() {
        handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        verify(mockViewModel).registerListeners()
    }
}