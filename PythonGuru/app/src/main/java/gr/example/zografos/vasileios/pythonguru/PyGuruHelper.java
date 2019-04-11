package gr.example.zografos.vasileios.pythonguru;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.HashMap;

public class PyGuruHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "PyGuru.db";

    public static String[] bad = {
            "You don't know the basics! See Lesson 1! and try again!",
            "You aren't very good at list and tuple processing! See Lesson 2 and try again!",
            "You aren't very good at dictionaries! See Lesson 3 and try again!",
            "You aren't very good at list and tuple processing! See Lesson 4 and try again!",
            "You don't know about conditional statements! See Lesson 5 and try again!",
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
                "CREATE TABLE IF NOT EXISTS Students(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR, marks TEXT DEFAULT \"0.0,0.0,0.0,0.0,0.0,0.0,0.0\")"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Answers(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, isCorrect BOOLEAN, question INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Lessons(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, title TEXT, visits INTEGER DEFAULT 0)"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Questions(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, quizNo INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Students");
        db.execSQL("DROP TABLE IF EXISTS Answers");
        db.execSQL("DROP TABLE IF EXISTS Lessons");
        db.execSQL("DROP TABLE IF EXISTS Questions");
        onCreate(db);
    }

    public boolean insertUser (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("marks", "0.0,0.0,0.0,0.0,0.0,0.0,0.0"); // 7 quizzes total the seventh one is the final
        long res = db.insert("Students", null, contentValues);
        return res != -1;
    }

    public boolean findUser (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username, password};
        Cursor res = db.rawQuery("SELECT * FROM Students WHERE username = ? AND password = ?", data);

        boolean b = res.getCount() == 1; // check if user exists

        if (!res.isClosed()) {
            res.close();
        }

        return b;
    }

    public boolean changePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        // update password
        String[] data = {newPassword, username};
        Cursor res = db.rawQuery("UPDATE Students SET password = ? WHERE username = ? ", data);

        boolean b = res.getPosition() != -1; // did something gone wrong

        if (!res.isClosed()) {
            res.close();
        }

        return b;
    }

    public String getMarks (String username) {
        // get user's marks
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username};
        Cursor res = db.rawQuery("SELECT * FROM Students WHERE username = ?", data);

        // get user's marks
        int marksIndex = res.getColumnIndex("marks");
        String marks = res.getString(marksIndex); // ex: "0.55,0.59,0.63,0.89,0.0,0.0,0.0"

        if (!res.isClosed()) {
            res.close();
        }
        return marks;
    }

    public boolean updateMark (String username, float mark, int quiz) { // quiz: 1-7, mark: 0.0-1.0
        // get user's marks
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username};
        Cursor res = db.rawQuery("SELECT * FROM Students WHERE username = ?", data);

        // get user's marks
        int marksIndex = res.getColumnIndex("marks");
        String[] marks = res.getString(marksIndex).split(",");

        // update marks array
        marks[quiz-1] = Float.toString(mark);
        String finalMarks = "";
        for (int i = 0; i < marks.length; i++) {
            if (i < marks.length) {
                finalMarks = finalMarks + marks[i] + ",";
            } else {
                finalMarks = finalMarks + marks[i];
            }
        }

        // update user's marks in database
        String[] data2 = {finalMarks, username};
        Cursor res2 = db.rawQuery("UPDATE Students SET marks = ? WHERE username = ? ", data2);

        boolean b2 = res2.getPosition() != -1; // did something gone wrong

        if (!res.isClosed()) {
            res.close();
        }

        if (!res2.isClosed()) {
            res2.close();
        }

        return b2;
    }

    public String commentMark(int quizIndex, float mark) { // quiz: 1-7, mark: 0.0-1.0
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

    public float calculateMark(int correctAnswers, int quiz) { // quiz: 1-7
        if (quiz < 7) { // 7 questions per quiz
            return correctAnswers / 7;
        } else {
            return correctAnswers / 14; // 14 questions for final quiz
        }
    }

    public HashMap<String, String>[] getQuestions(int quiz) { // quiz: 1-7 and NOT 0-6!
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {Integer.toString(quiz)};

        // find quiz's questions
        Cursor res = db.rawQuery("SELECT * FROM Questions WHERE quizNo = ?", data);
        res.moveToFirst();

        HashMap<String, String>[] questions = new HashMap[]{};
        while (res.isAfterLast() == false) {
            HashMap<String, String> item = new HashMap();
            item.put("id", res.getString(res.getColumnIndex("id")));
            item.put("body", res.getString(res.getColumnIndex("body")));
            item.put("quizNo", res.getString(res.getColumnIndex("quizNo")));

            questions[res.getPosition()] = item;
            res.moveToNext();
        }

        if (!res.isClosed()) {
            res.close();
        }

        return questions;
    }

    public HashMap<String, String>[] getAnswers(int question) { // answers: 1-3? and question:1-7/1-14
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {Integer.toString(question)};

        // find question's answers
        Cursor res = db.rawQuery("SELECT * FROM Answers WHERE question = ?", data);
        res.moveToFirst();

        HashMap<String, String>[] answers = new HashMap[]{};
        while(res.isAfterLast() == false){
            HashMap<String, String> item = new HashMap();
            item.put("id", res.getString(res.getColumnIndex("id")));
            item.put("body", res.getString(res.getColumnIndex("body")));
            item.put("isCorrect", res.getString(res.getColumnIndex("isCorrect")));

            answers[res.getPosition()] = item;
            res.moveToNext();
        }

        if (!res.isClosed()) {
            res.close();
        }

        return answers;
    }

    public HashMap<String, String>[] getLessonsTitles() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Lessons", new String[]{});
        res.moveToFirst();

        HashMap<String, String>[] lessons = new HashMap[]{};
        while(res.isAfterLast() == false){
            HashMap<String, String> item = new HashMap();
            item.put("title", res.getString(res.getColumnIndex("title")));

            lessons[res.getPosition()] = item;
            res.moveToNext();
        }

        if (!res.isClosed()) {
            res.close();
        }

        return lessons;
    }

    public String[] getLesson(int lesson){ // lesson: 1-6
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {Integer.toString(lesson)};

        // find lesson
        Cursor res = db.rawQuery("SELECT * FROM Lessons WHERE id = ?", data);

        // get lesson's content
        int bodyIndex = res.getColumnIndex("body");
        int titleIndex = res.getColumnIndex("title");
        int visitsIndex = res.getColumnIndex("visits");

        String[] lessonEntry = {res.getString(titleIndex), res.getString(bodyIndex), res.getString(visitsIndex) + 1};

        // update lesson's visits whenever someone click it
        String visits = res.getString(visitsIndex) + 1;
        String[] data2 = {visits, Integer.toString(lesson)};
        Cursor res2 = db.rawQuery("UPDATE Lessons SET visits = ? WHERE id = ?", data2);

        if (!res.isClosed()) {
            res.close();
        }

        if (!res2.isClosed()) {
            res2.close();
        }

        return lessonEntry; // ex: title:"foo bar baz", body:"lorem ipsum...", visits:33 -> current user's visits!
    }
}