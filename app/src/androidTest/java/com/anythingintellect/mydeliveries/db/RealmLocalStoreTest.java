package com.anythingintellect.mydeliveries.db;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.anythingintellect.mydeliveries.model.Delivery;
import com.anythingintellect.mydeliveries.util.MockData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by ishan.dhingra on 03/09/17.
 */

@RunWith(AndroidJUnit4.class)
public class RealmLocalStoreTest {

    private RealmLocalStore localStore;
    private Realm realm;

    @Before
    public void setup() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RealmConfiguration.Builder configBuilder = new RealmConfiguration.Builder();
                configBuilder.inMemory();
                realm = Realm.getInstance(configBuilder.build());
                localStore = new RealmLocalStore(realm);
            }
        });

    }

    private void runOnUiThread(Runnable runnable) {
        InstrumentationRegistry
                .getInstrumentation()
                .runOnMainSync(runnable);
    }

    // Should save deliveries
    @Test
    public void testSaveDeliveries_ShouldSaveDeliveriesToRealm() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final List<Delivery> deliveries = MockData.getNDeliveries(5);

                localStore.saveDeliveries(deliveries);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Delivery> realmResults = realm.where(Delivery.class).findAll();
                        assertNotEquals(null, realmResults);
                        assertEquals(deliveries.size(), realmResults.size());
                    }
                });
            }
        });

    }
    // Should return saved deliveries

    // Should close realm on close call

}
