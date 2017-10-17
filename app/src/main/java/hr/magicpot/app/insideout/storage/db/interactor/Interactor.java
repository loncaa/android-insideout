package hr.magicpot.app.insideout.storage.db.interactor;

public interface Interactor {
    interface onDatabaseListener {
        void onMessage(String message);
    }
}
