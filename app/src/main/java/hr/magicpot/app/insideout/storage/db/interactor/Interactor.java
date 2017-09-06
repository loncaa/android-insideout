package hr.magicpot.app.insideout.storage.db.interactor;

/**
 * Created by Antonio on 5.9.2017..
 */

public interface Interactor {
    interface onDatabaseListener {
        void onMessage(String message);
    }
}
