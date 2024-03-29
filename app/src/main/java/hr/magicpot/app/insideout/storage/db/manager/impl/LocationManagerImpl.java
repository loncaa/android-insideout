package hr.magicpot.app.insideout.storage.db.manager.impl;

import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import hr.magicpot.app.insideout.MainApplication;
import hr.magicpot.app.insideout.storage.db.DBHelper;
import hr.magicpot.app.insideout.storage.db.manager.LocationManager;
import hr.magicpot.app.insideout.storage.db.model.Location;

/**
 * https://developer.android.com/training/multiple-threads/communicate-ui.html
 * */
public class LocationManagerImpl implements LocationManager {
    private final DBHelper helper;
    private final Handler handler;

    public LocationManagerImpl() {
        this.helper = MainApplication.getDbHelper();
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void checkDatabase(final LatLng latLng, final onDatabaseConnection listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Location> lists  = helper.getLocationDao().query(
                                    helper.getLocationDao().queryBuilder()
                                            .where().eq("lng", latLng.longitude).and().eq("lat", latLng.latitude)
                                            .prepare());

                            if(lists.size() > 0)
                                listener.onFetchSuccess(lists.get(0));

                        } catch (SQLException e) {
                            listener.onMessage("Storing data failed.");
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void store(final LatLng latng, final onDatabaseConnection listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Location> lists  = helper.getLocationDao().queryForAll();

                            if(lists.size() > 0){
                                listener.onMessage("The location is already set.");
                                return;
                            }

                            Location location = new Location();
                            location.setLat(latng.latitude);
                            location.setLng(latng.longitude);

                            Dao.CreateOrUpdateStatus status = helper.getLocationDao().createOrUpdate(location);
                            if(status.isCreated()){
                                listener.onStoreSuccess(location);
                            }

                        } catch (SQLException e) {
                            listener.onMessage("Storing data failed.");
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).run();
    }

    @Override
    public void fetchLocation(final onDatabaseConnection listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Location> lists = helper.getLocationDao().queryForAll();
                            if(lists.size() == 0 || lists.get(0) == null){
                                listener.onMessage("Long tap on map to set location.");
                                return;
                            }

                            listener.onFetchSuccess(lists.get(0));
                        } catch (SQLException e) {
                            listener.onMessage("Fetching data failed");
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void deleteLocations(final onDatabaseConnection listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Location> locations = helper.getLocationDao().queryForAll();
                            helper.getLocationDao().delete(locations);
                            listener.onDeleteSuccess();
                        } catch (SQLException e) {
                            listener.onMessage("Fetching data failed");
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}
