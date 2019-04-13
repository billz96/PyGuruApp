package gr.example.zografos.vasileios.pythonguru;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PyGuruHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "PyGuru.db";

    public static String[] bad = {
            "You don't know the basics!\n See Lesson 1 and try again!",
            "You aren't very good at list and tuple processing!\n See Lesson 2 and try again!",
            "You aren't very good at dictionaries!\n See Lesson 3 and try again!",
            "You aren't very good at while loops!\n See Lesson 4 and try again!",
            "You don't know about conditional statements!\n See Lesson 5 and try again!",
            "You are not good at string processing! See Lesson 6 and try again!",
            "You aren't good at algorithmic thinking in Python!\nSee all the lessons and try again when you're able to at least pass every test"
    };
    public static String good = "Good job!";
    public static String veryGood = "Very good! Keep it up!";
    public static String excellent = "Excellent!";

    public PyGuruHelper(Context context) {
        super(context, DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Students(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR, marks TEXT)"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Lessons(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, title TEXT, visits INTEGER DEFAULT 0)"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Questions(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, quizNo INTEGER, answers TEXT)"
        );

        db.execSQL(
                "INSERT INTO Lessons(id, body, title)" +
                        "VALUES (1, \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\", \"Python Basics\"), " +
                        "(2, \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\", \"Lists and Tuples\"), " +
                        "(3, \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\", \"Dictionaries in Python\"), " +
                        "(4, \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\", \"While loops\"), " +
                        "(5, \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\", \"Conditional statements\"), " +
                        "(6, \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\", \"String joining, adding and many more...\");"
        );

        db.execSQL(
                "INSERT INTO Questions(id, body, quizNo, answers)" +
                        // quiz 1
                        "VALUES (1, \"question body 1\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(2, \"question body 2\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(3, \"question body 3\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(4, \"question body 4\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(5, \"question body 5\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(6, \"question body 6\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(7, \"question body 7\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(8, \"aaaaaaaaaaaaa 1\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(9, \"aaaaaaaaaaaaa 2\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(10, \"aaaaaaaaaaaaa 3\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(11, \"aaaaaaaaaaaaa 4\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(12, \"aaaaaaaaaaaaa 5\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(13, \"aaaaaaaaaaaaa 6\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(14, \"aaaaaaaaaaaaa 7\", 1, \"answer-1,y;answer-2,n;answer-3,n\")," +

                        // quiz 2
                        //id  body              quizNo  answers
                        "(15, \"question body 1\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(16, \"question body 2\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(17, \"question body 3\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(18, \"question body 4\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(19, \"question body 5\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(20, \"question body 6\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(21, \"question body 7\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(22, \"bbbbbbbbbbbbb 1\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(23, \"bbbbbbbbbbbbb 2\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(24, \"bbbbbbbbbbbbb 3\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(25, \"bbbbbbbbbbbbb 4\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(26, \"bbbbbbbbbbbbb 5\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(27, \"bbbbbbbbbbbbb 6\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(28, \"bbbbbbbbbbbbb 7\", 2, \"answer-1,y;answer-2,n;answer-3,n\")," +

                        // quiz 3
                        //id  body              quizNo  answers
                        "(29, \"question body 1\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(30, \"question body 2\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(31, \"question body 3\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(32, \"question body 4\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(33, \"question body 5\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(34, \"question body 6\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(35, \"question body 7\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(36, \"ccccccccccccc 1\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(37, \"ccccccccccccc 2\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(38, \"ccccccccccccc 3\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(39, \"ccccccccccccc 4\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(40, \"ccccccccccccc 5\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(41, \"ccccccccccccc 6\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(42, \"ccccccccccccc 7\", 3, \"answer-1,y;answer-2,n;answer-3,n\")," +

                        // quiz 4
                        //id  body              quizNo  answers
                        "(43, \"question body 1\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(44, \"question body 2\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(45, \"question body 3\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(46, \"question body 4\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(47, \"question body 5\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(48, \"question body 6\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(49, \"question body 7\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(50, \"ddddddddddddd 1\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(51, \"ddddddddddddd 2\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(52, \"ddddddddddddd 3\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(53, \"ddddddddddddd 4\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(54, \"ddddddddddddd 5\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(55, \"ddddddddddddd 6\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(56, \"ddddddddddddd 7\", 4, \"answer-1,y;answer-2,n;answer-3,n\")," +

                        // quiz 5
                        //id  body              quizNo  answers
                        "(57, \"question body 1\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(58, \"question body 2\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(59, \"question body 3\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(60, \"question body 4\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(61, \"question body 5\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(62, \"question body 6\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(63, \"question body 7\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(64, \"eeeeeeeeeeeee 1\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(65, \"eeeeeeeeeeeee 2\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(66, \"eeeeeeeeeeeee 3\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(67, \"eeeeeeeeeeeee 4\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(68, \"eeeeeeeeeeeee 5\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(69, \"eeeeeeeeeeeee 6\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(70, \"eeeeeeeeeeeee 7\", 5, \"answer-1,y;answer-2,n;answer-3,n\")," +

                        // quiz 6
                        //id  body              quizNo  answers
                        "(71, \"question body 1\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(72, \"question body 2\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(73, \"question body 3\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(74, \"question body 4\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(75, \"question body 5\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(76, \"question body 6\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(77, \"question body 7\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(78, \"fffffffffffff 1\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(79, \"fffffffffffff 2\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(80, \"fffffffffffff 3\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(81, \"fffffffffffff 4\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(82, \"fffffffffffff 5\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(83, \"fffffffffffff 6\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(84, \"fffffffffffff 7\", 6, \"answer-1,y;answer-2,n;answer-3,n\")," +

                        // quiz 7
                        //id  body              quizNo  answers
                        "(85, \"question body 1\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(86, \"question body 2\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(87, \"question body 3\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(88, \"question body 4\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(89, \"question body 5\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(90, \"question body 6\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(91, \"question body 7\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(92, \"question body 8\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(93, \"question body 9\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(94, \"question body 10\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(95, \"question body 11\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(96, \"question body 12\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(97, \"question body 13\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(98, \"question body 14\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(99, \"ggggggggggggg 1\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(100, \"ggggggggggggg 2\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(101, \"ggggggggggggg 3\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(102, \"ggggggggggggg 4\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(103, \"ggggggggggggg 5\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(104, \"ggggggggggggg 6\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(105, \"ggggggggggggg 7\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(106, \"ggggggggggggg 8\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(107, \"ggggggggggggg 9\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(108, \"ggggggggggggg 10\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(109, \"ggggggggggggg 11\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(110, \"ggggggggggggg 12\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(111, \"ggggggggggggg 13\", 7, \"answer-1,y;answer-2,n;answer-3,n\")," +
                        "(112, \"ggggggggggggg 14\", 7, \"answer-1,y;answer-2,n;answer-3,n\")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Students");
        db.execSQL("DROP TABLE IF EXISTS Lessons");
        db.execSQL("DROP TABLE IF EXISTS Questions");
        onCreate(db);
    }

    public boolean insertUser (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("marks", "0,0,0,0,0,0,0"); // 7 quizzes total, the seventh one is the final
        long res = db.insert("Students", null, contentValues);
        return res != -1;
    }

    public boolean findUser (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username, password};
        Cursor res;
        try {
            res = db.rawQuery("SELECT * FROM Students WHERE username = ? AND password = ?", data);
            boolean b = res.getCount() == 1; // check if user exists

            if (!res.isClosed()) {
                res.close();
            }

            return b;
        } catch (Exception e) {
            Log.e("Silly-Err: ", e.getMessage());
            return false;
        }
    }

    public boolean changePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        // update password
        String[] data = {newPassword, username};
        Cursor res;

        try {
            res = db.rawQuery("UPDATE Students SET password = ? WHERE username = ? ", data);
            res.moveToFirst();

            boolean b = res.getPosition() != -1; // did something gone wrong

            if (!res.isClosed()) {
                res.close();
            }

            return b;
        } catch (Exception e) {
            Log.e("Silly-Err: ", e.getMessage());
            return false;
        }
    }

    public String getMarks (String username) {
        // get user's marks
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username};
        Cursor res;

        try {
            res = db.rawQuery("SELECT * FROM Students WHERE username = ?", data);
            res.moveToFirst();

            // get user's marks
            int marksIndex = res.getColumnIndexOrThrow("marks"); // -> -1 if column doesn't exist
            String marks = res.getString(marksIndex); // ex: "0.55,0.59,0.63,0.89,0.0,0.0,0.0"

            if (!res.isClosed()) {
                res.close();
            }

            return marks;
        } catch (Exception e) {
            Log.e("Some-Stupid-Err: ", e.getMessage());
            return "";
        }
    }

    public boolean updateMark (String username, Double mark, int quiz) { // quiz: 1-7, mark: 0.0-1.0
        // get user's marks
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username};
        Cursor res;

        try {
            res = db.rawQuery("SELECT * FROM Students WHERE username = ?", data);
            res.moveToFirst();

            // get user's marks
            int marksIndex = res.getColumnIndex("marks");
            String[] marks = res.getString(marksIndex).split(",");

            // update marks array
            marks[quiz-1] = Double.toString(mark).substring(0,4); // ex: 0.42... -> just 0.42
            String finalMarks = "";
            for (int i = 0; i < marks.length; i++) {
                if (i < marks.length - 1) {
                    finalMarks = finalMarks + marks[i] + ",";
                } else {
                    finalMarks = finalMarks + marks[i];
                }
            }

            // update user's marks in database
            String[] data2 = {finalMarks, username};

            Cursor res2;

            try {
                res2 = db.rawQuery("UPDATE Students SET marks = ? WHERE username = ?", data2);
                res2.moveToFirst();

                boolean b2 = res2.getPosition() != -1; // did something gone wrong

                if (!res.isClosed()) {
                    res.close();
                }

                if (!res2.isClosed()) {
                    res2.close();
                }

                return b2;
            } catch (SQLiteException e) {
                Log.e("Silly-Err: ", e.getMessage());
                return false;
            }
        } catch (SQLiteException e) {
            Log.e("Silly-Err: ", e.getMessage());
            return false;
        }
    }

    public String commentMark(int quizIndex, Double mark) { // quiz: 1-7, mark: 0.0-1.0
        if (mark <= 0.63) {
            return PyGuruHelper.bad[quizIndex];
        }

        if (mark > 0.63 && mark <= 0.75) {
            return PyGuruHelper.good;
        }

        if (mark > 0.75 && mark < 0.9) {
            return PyGuruHelper.veryGood;
        }

        return PyGuruHelper.excellent;
    }

    public ArrayList<HashMap<String, String>> getQuestions(int quiz) { // quiz: 1-7 and NOT 0-6!
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {Integer.toString(quiz)};
        ArrayList<HashMap<String, String>> questions = new ArrayList();

        // find quiz's questions
        Cursor res;

        try {
            res = db.rawQuery("SELECT * FROM Questions WHERE quizNo = ?", data);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                HashMap<String, String> item = new HashMap();
                item.put("id", res.getString(res.getColumnIndex("id")));
                item.put("body", res.getString(res.getColumnIndex("body")));
                item.put("quizNo", res.getString(res.getColumnIndex("quizNo")));
                item.put("answers", res.getString(res.getColumnIndex("answers")));

                questions.add(item);
                res.moveToNext();
            }

            if (!res.isClosed()) {
                res.close();
            }

            // return seven random questions for simple quizzes(1-6)
            // and 14 random questions for the final quiz
            ArrayList<HashMap<String, String>> result = new ArrayList(); // final array list
            Collections.shuffle(questions); // randomize questions

            // check if we are about to view the final quiz or the simple ones
            if (quiz < 7) {
                for (int i = 0; i < 7; i++) {
                    result.add(questions.get(i)); // take first 7 random questions
                }
            } else {
                for (int i = 0; i < 14; i++) {
                    result.add(questions.get(i)); // take first 14 random questions
                }
            }

            return result;
        } catch (SQLiteException e) {
            Log.e("Silly-Err: ", e.getMessage());
            return new ArrayList();
        }
    }

    public ArrayList<HashMap<String, String>> getLessonsTitles() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> lessons = new ArrayList();
        Cursor res;

        try {
            res = db.rawQuery("SELECT * FROM Lessons", new String[]{});
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                HashMap<String, String> item = new HashMap();
                item.put("title", res.getString(res.getColumnIndex("title")));

                lessons.add(item);
                res.moveToNext();
            }

            if (!res.isClosed()) {
                res.close();
            }

            return lessons;
        } catch (Exception e) {
            Log.e("Silly-Err: ", e.getMessage());
            return new ArrayList();
        }
    }

    public String[] getLesson(int lesson){ // lesson: 1-6
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {Integer.toString(lesson)};
        Cursor res;
        Cursor res2;

        try {
            // find lesson
            res = db.rawQuery("SELECT * FROM Lessons WHERE id = ?", data);
            res.moveToFirst();

            // get lesson's content
            int bodyIndex = res.getColumnIndex("body");
            int titleIndex = res.getColumnIndex("title");
            int visitsIndex = res.getColumnIndex("visits");

            String[] lessonEntry = {res.getString(titleIndex), res.getString(bodyIndex), Integer.toString(res.getInt(visitsIndex) + 1)};

            // update lesson's visits whenever someone click it
            String visits = Integer.toString(res.getInt(visitsIndex) + 1);
            String[] data2 = {visits, Integer.toString(lesson)};

            try {
                res2 = db.rawQuery("UPDATE Lessons SET visits = ? WHERE id = ? ", data2);
                res2.moveToFirst();

                if (!res.isClosed()) {
                    res.close();
                }

                if (!res2.isClosed()) {
                    res2.close();
                }

                return lessonEntry; // ex: title:"foo bar baz", body:"lorem ipsum...", visits:33 -> current user's visits!
            } catch (Exception e) {
                Log.e("Silly-Err: ", e.getMessage());
                return new String[]{};
            }
        } catch (Exception e) {
            Log.e("Silly-Err: ", e.getMessage());
            return new String[]{};
        }
    }
}