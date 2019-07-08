package gr.example.zografos.vasileios.pythonguru;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class PyGuruHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "PyGuru.db";

    public static String[] bad = {
            "You don't know the basics!\n See Lesson 1 and try again!",
            "You aren't very good at list and tuple processing!\n See Lesson 2 and try again!",
            "You aren't very good at dictionaries!\n See Lesson 3 and try again!",
            "You aren't very good at while loops!\n See Lesson 4 and try again!",
            "You don't know about conditional statements and booleans!\n See Lesson 5 and try again!",
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
                "CREATE TABLE IF NOT EXISTS Lessons(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, title TEXT, visits INTEGER DEFAULT 0, lastVisit TEXT DEFAULT 'No visits')"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Questions(id INTEGER PRIMARY KEY AUTOINCREMENT, body TEXT, quizNo INTEGER, answers TEXT)"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Teachers(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR)"
        );

        String lesson1 = "Variables are nothing but reserved memory locations to store values. This means that when you create a variable you reserve some space in memory.\n" +
                "\n" +
                "Based on the data type of a variable, the interpreter allocates memory and decides what can be stored in the reserved memory. Therefore, by assigning different data types to variables, you can store integers, decimals or characters in these variables.\nPython variables do not need explicit declaration to reserve memory space. The declaration happens automatically when you assign a value to a variable. The equal sign (=) is used to assign values to variables.\n" +
                "\n" +
                "The operand to the left of the = operator is the name of the variable and the operand to the right of the = operator is the value stored in the variable.";

        String lesson2 = "The list is a most versatile datatype available in Python which can be written as a list of comma-separated values (items) between square brackets. Important thing about a list is that items in a list need not be of the same type.\n" +
                "\n" +
                "Creating a list is as simple as putting different comma-separated values between square brackets.\nA tuple is a sequence of immutable Python objects. Tuples are sequences, just like lists. The differences between tuples and lists are, the tuples cannot be changed unlike lists and tuples use parentheses, whereas lists use square brackets.\n" +
                "\n" +
                "Creating a tuple is as simple as putting different comma-separated values. Optionally you can put these comma-separated values between parentheses also.";

        String lesson3 = "Each key is separated from its value by a colon ':', the items are separated by commas, and the whole thing is enclosed in curly braces. An empty dictionary without any items is written with just two curly braces, like this '{}'.\n" +
                "\n" +
                "Keys are unique within a dictionary while values may not be. The values of a dictionary can be of any type, but the keys must be of an immutable data type such as strings, numbers, or tuples.";

        String lesson4 = "A while loop statement in Python programming language repeatedly executes a target statement as long as a given condition is true.\n\nHere, statement(s) may be a single statement or a block of statements. The condition may be any expression, and true is any non-zero value. The loop iterates while the condition is true.\n" +
                "\n" +
                "When the condition becomes false, program control passes to the line immediately following the loop.";

        String lesson5 = "Decision making is anticipation of conditions occurring while execution of the program and specifying actions taken according to the conditions.\n" +
                "\n" +
                "Decision structures evaluate multiple expressions which produce TRUE or FALSE as outcome. You need to determine which action to take and which statements to execute if outcome is TRUE or FALSE otherwise.\n\nPython programming language assumes any non-zero and non-null values as TRUE, and if it is either zero or null, then it is assumed as FALSE value.";

        String lesson6 = "Strings are amongst the most popular types in Python. We can create them simply by enclosing characters in quotes. Python treats single quotes the same as double quotes. Creating strings is as simple as assigning a value to a variable.\n\nPython does not support a character type; these are treated as strings of length one, thus also considered a substring.\n" +
                "\n" +
                "To access substrings, use the square brackets for slicing along with the index or indices to obtain your substring.";

        db.execSQL(
                "INSERT INTO Lessons(id, body, title)" +
                        "VALUES (1, \""+lesson1+"\", \"Python Basics\"), " +
                        "(2, \""+lesson2+"\", \"Lists and Tuples\"), " +
                        "(3, \""+lesson3+"\", \"Dictionaries in Python\"), " +
                        "(4, \""+lesson4+"\", \"While loops\"), " +
                        "(5, \""+lesson5+"\", \"Conditional statements & Booleans\"), " +
                        "(6, \""+lesson6+"\", \"String joining, indexing and many more...\");"
        );

        db.execSQL(
                "INSERT INTO Questions(id, body, quizNo, answers)" +
                        // quiz 1
                        "VALUES (1, \"What Python built-in function returns the unique number assigned to an object\", 1, \"id(),y;ref(),n;refnum(),n\")," +
                        "(2, \"Consider the following sequence of statements: 'n = 100; m = n'. How many objects and how many references we have?\", 1, \"2 obj - 1 ref,y;1 obj - 1 ref,n;2 obj 2 refs,n\")," +
                        "(3, \"In Python, a variable may be assigned a value of one type, and then later assigned a value of a different type\", 1, \"True,y;False,n;None of the above,n\")," +
                        "(4, \"Which of the following statements assigns the value 100 to the variable x\", 1, \"x = 100,y;x := 100,n;x << 100,n\")," +
                        "(5, \"In Python, a variable must be declared before it is assigned a value:\", 1, \"False,y;True,n;None of the above,n\")," +
                        "(6, \"what is the result of a = 5; a += 4\", 1, \"9,y;0,n;-2,n\")," +
                        "(7, \"What is a correct syntax to output 'Hello World' ?\", 1, \"print('Hello World'),y;echo 'Hello world',n;puts 'Hello World',n\")," +
                        "(8, \"Which of these is not a core data type?\", 1, \"Class,y;List,n;Tuple,n\")," +
                        "(9, \"What data type is the object below ? L = [1, 23, ‘hello’, 1]\", 1, \"List,y;Array,n;Dictionary,n\")," +
                        "(10, \"Which of the following function convert a string to a float in python?\", 1, \"float(x),y;str(x),n;int(x, [base]),n\")," +
                        "(11, \"Which of the following statement(s) is TRUE?\", 1, \"A hash function takes a message of arbitrary length and generates a fixed length code,y;A hash function takes a message of fixed length and generates a code of variable length,n;A hash function may give the same hash value for distinct messages,n\")," +
                        "(12, \"What is the output of print(9//2)\", 1, \"4,y;4.5,n;4.0,n\")," +
                        "(13, \"Which function overloads the >> operator?\", 1, \"more(),y;gt(),n;ge(),n\")," +
                        "(14, \"Which operator is overloaded by the or() function?\", 1, \"||,y;|,n;//,n\")," +

                        // quiz 2
                        //id  body              quizNo  answers
                        "(15, \"Which of the following are true of Python lists?\", 2, \"A list can hold any type,y;A list may contain any type of object except another list,n;All elements in a list must be of the same type,n\")," +
                        "(16, \"Assume the following list definition: a = ['foo', 'bar', 'baz']. What is a[-1]?\", 2, \"'baz',y;error,n;'foo',n\")," +
                        "(17, \"If x = [10, [3.141, 20, [30, 'baz', 2.718]], 'foo'] what expr gives 'z' from 'baz'\", 2, \"x[1][2][1][2],y;x[1][2][2][2],n;x[2][1][1][2],n\")," +
                        "(18, \"If x = [10, [3.141, 20, [30, 'baz', 2.718]], 'foo'] what expr gives ['baz', 2.218]\", 2, \"x[1][2][1:],y;x[1][1][1:2],n;x[1][2][0:2],n\")," +
                        "(19, \"If a = [1, 2, 3, 4, 5] what expr deletes elem 2?\", 2, \"del a[1],y;a.remove(2),n;a[2]=[],n\")," +
                        "(20, \"If a = ['a', 'b', 'c'] what expr adds 'd' ?\", 2, \"a.append('d'),y;a[3]='d',n;a += 'd',n\")," +
                        "(21, \"If t = ('foo', 'bar', 'baz') what expr gives 'a' ?\", 2, \"t[0],y;t(0),n;None of the above,n\")," +
                        "(22, \"If a, b, c = (1, 2, 3, 4, 5, 6, 7, 8, 9)[1::3] then b is\", 2, \"3,y;9,n;1,n\")," +
                        "(23, \" Which of the following is a Python tuple?\", 2, \"('a', 4, 0.44),y;{'a': 1, 'b': 2},n;[1, 3, 6],n\")," +
                        "(24, \"If t = (1, 2, 4, 3), which of the following is incorrect?\", 2, \"print(t[3]),y;max(t),n;t[3]=45,n\")," +
                        "(25, \"If t = (1, 2, 4, 3) then what is t[1:3]?\", 2, \"(2, 4),y;(1, 2, 4),n;(2, 4, 3),n\")," +
                        "(26, \"If t = (1, 2, 4, 3), then what is t[1:-1]?\", 2, \"(2, 4),y;(1, 2),n;(2, 4, 3),n\")," +
                        "(27, \"If t = (1, 2, 4, 3, 8, 9) then what is [t[i] for i in range(0, len(t), 2)]?\", 2, \"[1, 4, 8],y;(1, 4, 8),n;[2, 3, 9],n\")," +
                        "(28, \"If a = [14, 52, 7], b = a.copy() then is b == a?\", 2, \"False,y;True,n;None of the above,n\")," +

                        // quiz 3
                        //id  body              quizNo  answers
                        "(29, \"Which of the following is a dictionary?\", 3, \"{1:3 2:4},y;(4 6),n;['a' 'b'],n\")," +
                        "(30, \"Which of the following statements create a dictionary?\", 3, \"All metioned bellow,y;d={“john”:40 “peter”:45},n;d={},n\")," +
                        "(31, \"If d = {'john':40, 'peter':45} the d's keys are:\", 3, \"'john' 'peter',y;40 45,n;None of the above,n\")," +
                        "(32, \"If d = {'john':40, 'peter':45} the d's values are:\", 3, \"40 45,y;'john' 'peter',n;None of the above,n\")," +
                        "(33, \"What will be the output of ('john' in d) if d = {'john':40, 'peter':45}\", 3, \"True,y;False,n;None of the above,n\")," +
                        "(34, \"What will be the output of d1 == d2 if: d1 = {'john':40, 'peter':45}, d2 = {'john':466, 'peter':45}\", 3, \"False,y;True,n;Error,n\")," +
                        "(35, \"What will be the output of d1 > d2 if: d1 = {'john':40, 'peter':45}, d2 = {'john':466, 'peter':45}\", 3, \"Error,y;True,n;False,n\")," +
                        "(36, \"If d = {'john':40, 'peter':45} then d['john'] is\", 3, \"40,y;45,n;None,n\")," +
                        "(37, \"If d = {'john':40, 'peter':45} then to delete 'john' we do\", 3, \"del d['john'],y;del d('john': 40),n;d.delete('john'),n\")," +
                        "(38, \"If d = {'john':40, 'peter':45} then to get the d's size we do\", 3, \"len(d),y;size(d),n;d.len(),n\")," +
                        "(39, \"What will be the output of print(list(d.keys())) if d = {'john':40, 'peter':45}?\", 3, \"['john' 'peter'],y;('john' 'peter'),n;('john':40 'peter':45),n\")," +
                        "(40, \"If d = {'john':40, 'peter':45} then d['bob'] is\", 3, \"None,y;Error,n;Something else,n\")," +
                        "(41, \"If a = {1:'a', 2:'b', 'c':3} then prin(a.get(1,4)) is\", 3, \"'a',y;1,n;4,n\")," +
                        "(42, \"If a = {1:'a', 2:'b', 'c':3} then prin(a.get(5,4)) is\", 3, \"5,y;Error,n;4,n\")," +

                        // quiz 4
                        //id  body              quizNo  answers
                        "(43, \"What is the output of the following?\ni = 1\n" +
                        "x = ['ab', 'cd']"+
                        "while True:\n" +
                        "    if i%3 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        " \n" +
                        "    i + = 1\", 4, \"Error,y;1 2 3,n;1 2,n\")," +
                        "(44, \"What is the output of the following?\ni = 1\n" +
                        "while True:\n" +
                        "    if i%0O7 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        "    i += 1\", 4, \"1 2 3 4 5 6,y;1 2 3 4 5 6 7,n;Error,n\")," +
                        "(45, \"What is the output of the following?\ni = 1\n" +
                        "while True:\n" +
                        "    if i%0O11 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        "    i += 1\", 4, \"5 6 7 8,y;Error,n;5 6 7 8 9 10,n\")," +
                        "(46, \"What is the output of the following?\ni = 5\n" +
                        "while True:\n" +
                        "    if i%0O9 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        "    i += 1\", 4, \"Error,y;5 6 7 8,n;5 6 7 8 9,n\")," +
                        "(47, \"What is the output of the following?\ni = 1\n" +
                        "while True:\n" +
                        "    if i%2 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        "    i += 2\", 4, \"1 3 5 6 9 11..,y;1 2,n;1,n\")," +
                        "(48, \"What is the output of the following?\ni = 2\n" +
                        "while True:\n" +
                        "    if i%3 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        "    i += 2\", 4, \"2 4,y;2 3,n;2 4 6 8 10..,n\")," +
                        "(49, \"What is the output of the following?\ni = 1\n" +
                        "while False:\n" +
                        "    if i%2 == 0:\n" +
                        "        break\n" +
                        "    print(i)\n" +
                        "    i += 2\", 4, \"None of the below,y;1,n;1 3 5 7..,n\")," +
                        "(50, \"What is the output of the following?\nTrue = False\n" +
                        "while True:\n" +
                        "    print(True)\n" +
                        "    break\", 4, \"None of the below,y;None,n;True,n\")," +
                        "(51, \"What is the output of the following?\ni = 0\n" +
                        "while i < 5:\n" +
                        "    print(i)\n" +
                        "    i += 1\n" +
                        "    if i == 3:\n" +
                        "        break\n" +
                        "else:\n" +
                        "    print(0)\", 4, \"0 1 2,y;Error,n;0 1 2 0,n\")," +
                        "(52, \"What is the output of the following?\ni = 0\n" +
                        "while i < 3:\n" +
                        "    print(i)\n" +
                        "    i += 1\n" +
                        "else:\n" +
                        "    print(0)\", 4, \"0 1 2 0,y;0 1 2 3,n;Error,n\")," +
                        "(53, \"What is the output of the following?\nx='abcdef'\nwhile i in x:\n" +
                        "    print(i, end=' ')\", 4, \"i i i i..,y;abcdef,n;a b c d e f,n\")," +
                        "(54, \"What is the output of the following?\nx = 'abcdef'\n" +
                        "i = 'i'\n" +
                        "while i in x:\n" +
                        "    print(i, end=' ')\", 4, \"Nothing,y;abcdef,n;i i i i..,n\")," +
                        "(55, \"What is the output of the following?\nx = 'abcdef'\n" +
                        "i = 'a'\n" +
                        "while i in x:\n" +
                        "    print(i, end =' ')\", 4, \"a a a a..,y;Nothing,n;i i i i..,n\")," +
                        "(56, \"What is the output of the following?\nx = 'abcdef'\n" +
                        "i = 'a'\n" +
                        "while i in x:\n" +
                        "    print('i', end =' ')\", 4, \"i i i i..,y;a b c d e f,n;abcdef,n\")," +

                        // quiz 5
                        //id  body              quizNo  answers
                        "(57, \"In a Python program, a control structure\", 5, \"Directs the order of execution of the statements in the program,y;Dictates what happens before the program starts and after it terminates,n;Manages the input and output of control characters,n\")," +
                        "(58, \"Which one of the following if statements will not execute\", 5, \"if (1, 2):print('foo'),y;if (1, 2): print('foo'),n;if (1, 2):\n\tprint('foo'),n\")," +
                        "(59, \"What signifies the end of a statement block\", 5, \"A line that is indented less than the previous line,y;end,n;},n\")," +
                        "(60, \"What is the output of the following?\nif 'bar' in {'foo': 1, 'bar': 2, 'baz': 3}:\n" +
                        "    print(1)\n" +
                        "    print(2)\n" +
                        "    if 'a' in 'qux':\n" +
                        "        print(3)\n" +
                        "print(4)\", 5, \"1 2 4,y;4,n;Nothing,n\")," +
                        "(61, \"What is the output of the following?\nbool(‘False’)\n" +
                        "bool()\", 5, \"True False,y;False True,n;Error,n\")," +
                        "(62, \"What is the output of the following?\n['hello', 'morning'][bool('')]\", 5, \"hello,y;morning,n;Nothing,n\")," +
                        "(63, \"What is the output of the following?\nnot(3>4)\n" +
                        "not(1&1)\", 5, \"False True,y;True True,n;False False,n\")," +
                        "(64, \"What is the output of the following?\n['f', 't'][bool('spam')]\", 5, \"t,y;f,n;Error,n\")," +
                        "(65, \"What is the output of the following?\nl=[1, 0, 2, 0, 'hello', '', []]\n" +
                        "list(filter(bool, l))\n\", 5, \"[1 2 ‘hello’],y;[1 0 2 ‘hello’ []],n;Error,n\")," +
                        "(66, \"What is the output of the following code if the system date is 21st June, 2017 (Wednesday)\n[ ] or {}\n" +
                        "{} or [ ]\", 5, \"{} [],y;[] [],n;[] {},n\")," +
                        "(67, \"What is the output of the code shown below?\nclass Truth:\n" +
                        "\tpass\n" +
                        "x=Truth()\n" +
                        "bool(x)\", 5, \"False,y;True,n;Error,n\")," +
                        "(68, \"What is the output of the code shown below?\nif (9 < 0) and (0 < -9):\n" +
                        "    print('hello')\n" +
                        "elif (9 > 0) or False:\n" +
                        "    print('good')\n" +
                        "else:\n" +
                        "    print('bad')\", 5, \"good,y;bad,n;hello,n\")," +
                        "(69, \"Which of the following Boolean expressions is not logically equivalent to the other three?\", 5, \"not(-6>10 or-6==10),y;not(-6<10 or-6==10),n;not(-6<0 or-6>10),n\")," +
                        "(70, \"The output of the line of code shown below is:\nnot(10<20) and not(10>30)\", 5, \"False,y;True,n;Nothing,n\")," +

                        // quiz 6
                        //id  body              quizNo  answers
                        "(71, \"What is the output of the following?\n'a'+'bc'\", 6, \"'abc',y;Error,n;'a bc'-3,n\")," +
                        "(72, \"What is the output of the following?\n'abcd'[2:]\", 6, \"cd,y;dc,n;ba,n\")," +
                        "(73, \"The output of executing string.ascii_letters can also be achieved by\", 6, \"string.ascii_lowercase+string.ascii_upercase,y;string.letters,n;string.lowercase_string.upercase,n\")," +
                        "(74, \"What is the output of the following?\n>>> str1 = 'hello'\n" +
                        ">>> str2 = ','\n" +
                        ">>> str3 = 'world'\n" +
                        ">>> str1[-1:]\", 6, \"o,y;hello,n;olleh,n\")," +
                        "(75, \"What arithmetic operators cannot be used with strings ?\", 6, \"-,y;*,n;+,n\")," +
                        "(76, \"What is the output of the following?\n>>>print r'\nhello'\", 6, \"'\nhello',y;Error,n;new line and hello,n\")," +
                        "(77, \"What is the output of the following?\n>>>print('new' 'line')\", 6, \"new line,y;newline,n;Error,n\")," +
                        "(78, \"What is the output when following statement is executed ?\n" +
                        ">>> print(‘x\\97\\x98’)\", 6, \" x\\97,y;\\x97\\x98,n;Error,n\")," +
                        "(79, \"What is the output when following code is executed ?\n>>>str1='helloworld'\n" +
                        ">>>str1[::-1]\", 6, \"dlrowolleh,y;hello,n;world,n\")," +
                        "(80, \"What is the output when following code is executed ?\nprint(0xA + 0xB + 0xC)\", 6, \"33,y;0x22,n;Error,n\")," +
                        "(81, \"What is the output of the following code ?\n" +
                        "\n" +
                        ">>>example = 'snow world'\n" +
                        ">>>print('%s' % example[4:7])\", 6, \"wo,y;world,n;sn,n\")," +
                        "(82, \"What is the output of the following code ?\n" +
                        "\n" +
                        ">>>example = 'snow world'\n" +
                        ">>>example[3] = 's'\n" +
                        ">>>print example\", 6, \"Error,y;snos world,n;snow,n\")," +
                        "(83, \"What is the output of the following code ?\n" +
                        "\n" +
                        ">>>max('what are you')\", 6, \"'Y',y;'U',n;'T',n\")," +
                        "(84, \"What is the output of the following code ?\n>>>example = 'helle'\n" +
                        ">>>example.find('e')\", 6, \"1,y;-1,n;0,n\")," +

                        // quiz 7
                        //id  body              quizNo  answers
                        "(85, \"Which of the following function convert a string to a float in python?\", 7, \"float(x),y;str(x),n;int(x, [base]),n\")," +
                        "(86, \"Which of the following statement(s) is TRUE?\", 7, \"A hash function takes a message of arbitrary length and generates a fixed length code,y;A hash function takes a message of fixed length and generates a code of variable length,n;A hash function may give the same hash value for distinct messages,n\")," +
                        "(87, \"What is the output of print(9//2)\", 7, \"4,y;4.5,n;4.0,n\")," +
                        "(88, \"Which function overloads the >> operator?\", 7, \"more(),y;gt(),n;ge(),n\")," +
                        "(89, \"If t = (1, 2, 4, 3), which of the following is incorrect?\", 7, \"print(t[3]),y;max(t),n;t[3]=45,n\")," +
                        "(90, \"If t = (1, 2, 4, 3) then what is t[1:3]?\", 7, \"(2, 4),y;(1, 2, 4),n;(2, 4, 3),n\")," +
                        "(91, \"If t = (1, 2, 4, 3), then what is t[1:-1]?\", 7, \"(2, 4),y;(1, 2),n;(2, 4, 3),n\")," +
                        "(92, \"If t = (1, 2, 4, 3, 8, 9) then what is [t[i] for i in range(0, len(t), 2)]?\", 7, \"[1, 4, 8],y;(1, 4, 8),n;[2, 3, 9],n\")," +
                        "(93, \"Which of the following is a dictionary?\", 7, \"{1:3 2:4},y;(4 6),n;['a' 'b'],n\")," +
                        "(94, \"Which of the following statements create a dictionary?\", 7, \"All metioned bellow,y;d={“john”:40 “peter”:45},n;d={},n\")," +
                        "(95, \"If d = {'john':40, 'peter':45} the d's keys are:\", 7, \"'john' 'peter',y;40 45,n;None of the above,n\")," +
                        "(96, \"If a = {1:'a', 2:'b', 'c':3} then prin(a.get(5,4)) is\", 7, \"5,y;Error,n;4,n\")," +
                        "(97, \"What arithmetic operators cannot be used with strings ?\", 7, \"-,y;*,n;+,n\")," +
                        "(98, \"What is the output of the following?\n>>>print r'\nhello'\", 7, \"'\nhello',y;Error,n;new line and hello,n\")," +
                        "(99, \"What is the output of the following?\n>>>print('new' 'line')\", 7, \"new line,y;newline,n;Error,n\")," +
                        "(100, \"What is the output when following code is executed ?\nprint(0xA + 0xB + 0xC)\", 7, \"33,y;0x22,n;Error,n\")," +
                        "(101, \"In a Python program, a control structure\", 7, \"Directs the order of execution of the statements in the program,y;Dictates what happens before the program starts and after it terminates,n;Manages the input and output of control characters,n\")," +
                        "(102, \"Which one of the following if statements will not execute\", 7, \"if (1, 2):print('foo'),y;if (1, 2): print('foo'),n;if (1, 2):\n\tprint('foo'),n\")," +
                        "(103, \"What signifies the end of a statement block\", 7, \"A line that is indented less than the previous line,y;end,n;},n\")," +
                        "(104, \"What is the output of the following?\n['hello', 'morning'][bool('')]\", 7, \"hello,y;morning,n;Nothing,n\")," +
                        "(105, \"What is the output of the following?\n['f', 't'][bool('spam')]\", 7, \"t,y;f,n;Error,n\")," +
                        "(106, \"Which of the following Boolean expressions is not logically equivalent to the other three?\", 7, \"not(-6>10 or-6==10),y;not(-6<10 or-6==10),n;not(-6<0 or-6>10),n\")," +
                        "(107, \"The output of the line of code shown below is:\nnot(10<20) and not(10>30)\", 7, \"False,y;True,n;Nothing,n\")," +
                        "(108, \"Which of the following is a dictionary?\", 7, \"{1:3 2:4},y;(4 6),n;['a' 'b'],n\")," +
                        "(109, \"What will be the output of d1 > d2 if: d1 = {'john':40, 'peter':45}, d2 = {'john':466, 'peter':45}\", 7, \"Error,y;True,n;False,n\")," +
                        "(110, \"Which of the following are true of Python lists?\", 7, \"A list can hold any type,y;A list may contain any type of object except another list,n;All elements in a list must be of the same type,n\")," +
                        "(111, \"Assume the following list definition: a = ['foo', 'bar', 'baz']. What is a[-1]?\", 7, \"'baz',y;error,n;'foo',n\")," +
                        "(112, \"What Python built-in function returns the unique number assigned to an object\", 7, \"id(),y;ref(),n;refnum(),n\")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Students");
        db.execSQL("DROP TABLE IF EXISTS Lessons");
        db.execSQL("DROP TABLE IF EXISTS Questions");
        db.execSQL("DROP TABLE IF EXISTS Teachers");
        onCreate(db);
    }

    public boolean insertTeacher (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long res = db.insert("Teachers", null, contentValues);
        return res != -1; // if res == -1 then something gone wrong
    }

    public boolean findTeacher (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username, password};
        Cursor res;
        try {
            res = db.rawQuery("SELECT * FROM Teachers WHERE username = ? AND password = ?", data);
            res.moveToFirst();
            boolean b = res.getCount() == 1; // check if teacher exists

            if (!res.isClosed()) {
                res.close();
            }

            return b;
        } catch (Exception e) {
            Log.e("Silly-Err: ", e.getMessage());
            return false;
        }
    }

    public boolean changeTeachersPassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        // update password
        String[] data = {newPassword, username};
        Cursor res;

        try {
            res = db.rawQuery("UPDATE Teachers SET password = ? WHERE username = ? ", data);
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

    public boolean insertQuestion(String question, int quiz, String answers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("body", question);
        contentValues.put("quizNo", quiz);
        contentValues.put("answers", answers);
        long res = db.insert("Questions", null, contentValues);
        return res != -1; // if res == -1 then something gone wrong
    }

    public boolean insertUser (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("marks", "---,---,---,---,---,---,---"); // 7 quizzes total, the seventh one is the final
        long res = db.insert("Students", null, contentValues);
        return res != -1;
    }

    public boolean userExists(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numOfRows = (int) DatabaseUtils.queryNumEntries(db, "Students");
        return numOfRows == 1; // check if user already registered in the local database
    }

    public boolean findUsername (String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username};
        Cursor res;
        try {
            res = db.rawQuery("SELECT * FROM Students WHERE username = ?", data);
            res.moveToFirst();
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

    public boolean findUser (String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {username, password};
        Cursor res;
        try {
            res = db.rawQuery("SELECT * FROM Students WHERE username = ? AND password = ?", data);
            res.moveToFirst();
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
            marks[quiz-1] = Double.toString(mark);
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

    public String commentMark(int quizIndex, Double mark) { // quiz: 1-7, mark: 0.0 - 100.0
        if (mark <= 63.0) {
            return PyGuruHelper.bad[quizIndex];
        }

        if (mark > 63.0 && mark <= 75.0) {
            return PyGuruHelper.good;
        }

        if (mark > 75.0 && mark < 9.0) {
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
                item.put("lastVisit", res.getString(res.getColumnIndex("lastVisit")));

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

        // store the date of last visit
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - HH:mm:ss a");
        String datetime = dateFormat.format(c.getTime());

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
            String[] data2 = {visits, datetime, Integer.toString(lesson)};

            try {
                res2 = db.rawQuery("UPDATE Lessons SET visits = ?, lastVisit = ? WHERE id = ? ", data2);
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