package ir.geraked.nahj.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SqlLiteDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ger-nahj.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context mCtx;

    public SqlLiteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mCtx = context;
    }

    public ArrayList<Model> getDetails(int setCat, int setId, boolean setFav) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Model> modelList = new ArrayList<>();
        Cursor cursor;
        if (setId != 0) {
            cursor = db.rawQuery("SELECT * FROM nahj WHERE id = " + setId + " LIMIT 1", null);
        } else {
            if (setFav) {
                cursor = db.rawQuery("SELECT * FROM nahj WHERE fav = 1", null);
            } else {
                cursor = (setCat != 0) ? db.rawQuery("SELECT * FROM nahj WHERE cat = " + setCat + " ORDER BY num ASC", null) : db.rawQuery("SELECT * FROM nahj ORDER BY num ASC", null);
            }
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Model count = new Model(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
                modelList.add(count);
            }
            cursor.close();
            db.close();
        }
        return modelList;
    }

    public void updateFav(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fav", model.getFav());
        db.update("nahj", cv, "id = ?", new String[]{String.valueOf(model.getId())});
        db.close();
    }

    public void updateTitle(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", model.getTitle());
        db.update("nahj", cv, "id = ?", new String[]{String.valueOf(model.getId())});
        db.close();
    }

    public void updateCnt(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cnt", model.getcnt());
        db.update("nahj", cv, "id = ?", new String[]{String.valueOf(model.getId())});
        db.close();
    }

    private void CopyDatabaseFromAssets() throws IOException {
        InputStream myInput = mCtx.getAssets().open(DB_NAME);
        String outFileName = getDatabasePath();
        File f = new File(mCtx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private static String getDatabasePath() {
        return mCtx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLiteException {
        File dbFile = mCtx.getDatabasePath(DB_NAME);
        SharedPreferences sharedPref = mCtx.getSharedPreferences("ir.geraked.nahj.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int dbVersion = sharedPref.getInt("DB_VERSION", 1);
        if (dbFile.exists()) {
            if (DB_VERSION != dbVersion) {
                try {
                    dbFile.delete();
                    Toast.makeText(mCtx, "Deleted the old database", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(mCtx, "Error deleting the old database", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException("Error deleting the old database", e);
                }
            }
        }
        if (!dbFile.exists()) {
            try {
                CopyDatabaseFromAssets();
                editor.putInt("DB_VERSION", DB_VERSION);
                editor.apply();
//                Toast.makeText(mCtx, "Coppying database success from assets folder", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
//                Toast.makeText(mCtx, "Coppying database success from assets folder", Toast.LENGTH_SHORT).show();
//                throw new RuntimeException("Error coppying database success from assets folder", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}