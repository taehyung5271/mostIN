package com.example.mostin.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.mostin.models.DateModel;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.AttendanceRecordModel;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.OrderDetailModel;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mostin.db";
    private static final int DATABASE_VERSION = 3;

    // 테이블 이름 상수 정의
    public static final String TABLE_EMPLOYEE_MD = "employee_md";
    public static final String TABLE_WORK_PLACE = "work_place";
    private static final String TABLE_ANNUAL_LEAVE = "annual_leave";
    private static final String TABLE_COMMUTE = "commute";
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_GOODS = "goods";
    private static final String TABLE_ORDERING = "ordering";

    private static final String TABLE_NAME = "commute";
    private static final String COLUMN_DATE = "commute_day";
    private static final String COLUMN_CLOCK_IN = "start_time";
    private static final String COLUMN_CLOCK_OUT = "end_time";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 기존 테이블 강제 삭제
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_MD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNUAL_LEAVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMUTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERING);

        // 테이블 새로 생성
        // employee_md 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_EMPLOYEE_MD + " (" +
                        "employee_id VARCHAR(40) NOT NULL, " +
                        "employee_name VARCHAR(20) NOT NULL, " +
                        "employee_pwd VARCHAR(40) NOT NULL, " +
                        "phone_num VARCHAR(20) NOT NULL, " +
                        "employee_type VARCHAR(10) NOT NULL, " +
                        "address VARCHAR(40), " +
                        "work_place_name VARCHAR(40) NOT NULL, " +
                        "PRIMARY KEY (employee_id, employee_name))");

        // work_place 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_WORK_PLACE + " (" +
                        "work_place_name VARCHAR(40) PRIMARY KEY NOT NULL, " +
                        "latitude REAL NOT NULL, " +
                        "longitude REAL NOT NULL)");

        // annual_leave 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_ANNUAL_LEAVE + " (" +
                        "employee_id VARCHAR(40) NOT NULL, " +
                        "employee_name VARCHAR(20) NOT NULL, " +
                        "total_annual_leave INTEGER NOT NULL, " +
                        "use_annual_leave INTEGER NOT NULL, " +
                        "remain_annual_leave INTEGER NOT NULL, " +
                        "PRIMARY KEY (employee_id, employee_name), " +
                        "FOREIGN KEY (employee_id, employee_name) REFERENCES " +
                        TABLE_EMPLOYEE_MD + "(employee_id, employee_name))");

        // commute 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_COMMUTE + " (" +
                        "commute_day DATE NOT NULL, " +
                        "employee_id VARCHAR(40) NOT NULL, " +
                        "employee_name VARCHAR(20) NOT NULL, " +
                        "start_time DATE NOT NULL, " +
                        "end_time DATE NOT NULL, " +
                        "work_place_name VARCHAR(40) NOT NULL, " +
                        "PRIMARY KEY (commute_day, employee_id, employee_name), " +
                        "FOREIGN KEY (employee_id, employee_name) REFERENCES " +
                        TABLE_EMPLOYEE_MD + "(employee_id, employee_name))");

        // login 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_LOGIN + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "employee_id VARCHAR(40) NOT NULL, " +
                        "employee_name VARCHAR(20) NOT NULL, " +
                        "ip VARCHAR(10) NOT NULL, " +
                        "login_date DATE NOT NULL, " +
                        "FOREIGN KEY (employee_id, employee_name) REFERENCES " +
                        TABLE_EMPLOYEE_MD + "(employee_id, employee_name))");

        // goods 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_GOODS + " (" +
                        "barcode VARCHAR(20) NOT NULL, " +
                        "goods_name2 VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (barcode, goods_name2))");

        // ordering 테이블 생성
        db.execSQL(
                "CREATE TABLE " + TABLE_ORDERING + " (" +
                        "ordering_day DATE NOT NULL, " +
                        "employee_id VARCHAR(40) NOT NULL, " +
                        "employee_name VARCHAR(20) NOT NULL, " +
                        "box_num INTEGER, " +
                        "barcode VARCHAR(20) NOT NULL, " +
                        "goods_name2 VARCHAR(40) NOT NULL, " +
                        "PRIMARY KEY (ordering_day, employee_id, employee_name, barcode), " +
                        "FOREIGN KEY (employee_id, employee_name) REFERENCES " +
                        TABLE_EMPLOYEE_MD + "(employee_id, employee_name))");

        // 데이터베이스 생성 로그
        Log.d("SQLiteHelper", "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_MD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNUAL_LEAVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMUTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERING);

        // 테이블 다시 생성
        onCreate(db);
    }

    // 출근/퇴근 데이터 삽입 또는 업데이트
    public void saveAttendance(String date, String clockIn, String clockOut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_CLOCK_IN, clockIn);
        values.put(COLUMN_CLOCK_OUT, clockOut);

        // 데이터 삽입 또는 업데이트
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // 특정 날짜의 출근/퇴근 데이터 조회
    public Cursor getAttendanceByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_DATE + "=?", new String[]{date}, null, null, null);
    }

    public List<DateModel> getAttendanceForMonth(String employeeId, String employeeName, int year, int month) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DateModel> attendanceList = new ArrayList<>();

        // SQL 쿼리로 특정 월의 데이터를 가져오
        String query = "SELECT commute_day, start_time, end_time FROM " + TABLE_COMMUTE +
                " WHERE employee_id = ? AND employee_name = ? " +
                " AND strftime('%Y', commute_day) = ? " +
                " AND strftime('%m', commute_day) = ?";

        String[] selectionArgs = {
                employeeId,
                employeeName,
                String.valueOf(year),
                String.format(Locale.getDefault(), "%02d", month + 1)
        };

        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow("commute_day"));
            String startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));

            DateModel dateModel = new DateModel(date, startTime);
            dateModel.setClockOutTime(endTime);
            attendanceList.add(dateModel);
        }

        cursor.close();
        db.close();
        return attendanceList;
    }

    // 퇴근 시 업데이트 메소드 추가
    public void updateClockOut(String date, String clockOutTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOCK_OUT, clockOutTime);

        // 해당 날짜의 레코드 업데이트
        db.update(TABLE_NAME,
                values,
                COLUMN_DATE + "=?",
                new String[]{date});
        db.close();
    }

    // 가장 최근 출퇴근 기록 가져오기
    public String[] getRecentAttendance() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] result = new String[2]; // [0]: 최근 출근, [1]: 최근 퇴근

        // 가장 최근 기록 조회
        String query = "SELECT * FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_DATE + " DESC, " +
                COLUMN_CLOCK_IN + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String clockIn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLOCK_IN));
            String clockOut = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLOCK_OUT));

            // "YYYY-MM-DD / HH:mm" 형식으로 변환
            result[0] = date + " / " + clockIn;
            result[1] = clockOut != null ? date + " / " + clockOut : "";

            cursor.close();
        }

        db.close();
        return result;
    }

    // 초기 데이터 삽입
    public void insertInitialData() {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            
            // 근무지 정보 추가
            Cursor workplaceCursor = db.rawQuery("SELECT * FROM " + TABLE_WORK_PLACE, null);
            if (workplaceCursor.getCount() == 0) {
                // 실제 이마트, 홈플러스 지점 위치 추가
                String[][] workplaces = {
                    {"이마트 왕십리점", "37.565213", "127.024805"},
                    {"이마트 자양점", "37.565213", "127.024805"},
                    {"홈플러스 동대문점", "37.574497", "127.038738"},
                    {"이마트 청계천점", "37.571020", "127.022210"},
                    {"이마트 하월곡점", "37.604803", "127.030929"},
                    {"이마트 가든5점", "37.478484", "127.118794"}
                };

                for (String[] workplace : workplaces) {
                    ContentValues values = new ContentValues();
                    values.put("work_place_name", workplace[0]);
                    values.put("latitude", Double.parseDouble(workplace[1]));
                    values.put("longitude", Double.parseDouble(workplace[2]));
                    db.insert(TABLE_WORK_PLACE, null, values);
                }
                
                Log.d("InitialData", "Workplace locations added");
            }
            workplaceCursor.close();

            // 각 계정이 존재하는지 확인고 없으면 삽입
            // admin 계정 확인 및 삽입
            Cursor adminCursor = db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEE_MD + " WHERE employee_id = 'admin'", null);
            if (adminCursor.getCount() == 0) {
                ContentValues adminValues = new ContentValues();
                adminValues.put("employee_id", "admin");
                adminValues.put("employee_name", "관리자");
                adminValues.put("employee_pwd", "admin123");
                adminValues.put("phone_num", "010-0000-0000");
                adminValues.put("employee_type", "admin");
                adminValues.put("address", "서울시");
                adminValues.put("work_place_name", "본사");  // 관리자 근무지
                long adminResult = db.insert(TABLE_EMPLOYEE_MD, null, adminValues);
                Log.d("InitialData", "Admin insert result: " + adminResult);
            }
            adminCursor.close();

            // user01 계정 확인 및 삽입
            Cursor user1Cursor = db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEE_MD + " WHERE employee_id = 'user01'", null);
            if (user1Cursor.getCount() == 0) {
                ContentValues userValues = new ContentValues();
                userValues.put("employee_id", "user01");
                userValues.put("employee_name", "홍길동");
                userValues.put("employee_pwd", "user123");
                userValues.put("phone_num", "010-1234-5678");
                userValues.put("employee_type", "MD");
                userValues.put("address", "서울시");
                userValues.put("work_place_name", "이마트 청계천점");  // user01 근무지
                long user1Result = db.insert(TABLE_EMPLOYEE_MD, null, userValues);
                Log.d("InitialData", "User1 insert result: " + user1Result);
            }
            user1Cursor.close();

            // user02 계정 확인 및 삽입
            Cursor user2Cursor = db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEE_MD + " WHERE employee_id = 'user02'", null);
            if (user2Cursor.getCount() == 0) {
                ContentValues user2Values = new ContentValues();
                user2Values.put("employee_id", "user02");
                user2Values.put("employee_name", "김철수");
                user2Values.put("employee_pwd", "user456");
                user2Values.put("phone_num", "010-9876-5432");
                user2Values.put("employee_type", "MD");
                user2Values.put("address", "서울시");
                user2Values.put("work_place_name", "홈플러스 동대문점");  // user02 근무지
                long user2Result = db.insert(TABLE_EMPLOYEE_MD, null, user2Values);
                Log.d("InitialData", "User2 insert result: " + user2Result);
            }
            user2Cursor.close();

            // Goods 테이블 데이터 초기화
            Cursor goodsCursor = db.rawQuery("SELECT * FROM " + TABLE_GOODS, null);
            if (goodsCursor.getCount() == 0) {
                String[][] goodsData = {
                    {"8801858045982", "카스화이트464캔"},
                    {"8801021220857", "구스ipa"},
                    {"8801021223575", "덕덕구스"},
                    {"8801858046149", "카스제로500캔"},
                    {"8801858045258", "카스제로355캔"},
                    {"8801858133368", "카스라이트 1.6L"},
                    {"8801858046927", "카스레몬500캔"},
                    {"8801021233925", "버드와이저제로355캔"},
                    {"8801021106380", "한맥350캔"},
                    {"8801021229423", "버드와이저제로500캔"},
                    {"8801858011116", "카스355캔"},
                    {"8801021229232", "호가든로제500캔"},
                    {"8801858047146", "카스롤링팩"},
                    {"8801858047399", "카스레몬제로355캔"},
                    {"0018200250002", "버드와이저740캔"},
                    {"8801021230863", "스텔라740캔"},
                    {"8801021223124", "스텔라500캔"},
                    {"8801021215013", "코로나 병"},
                    {"8801858045456", "카500캔"},
                    {"8801858011239", "카스라이트355캔"},
                    {"8801858047443", "카스라이트500캔"},
                    {"8801021214320", "호가든500캔"},
                    {"8801021214375", "호가든355캔"},
                    {"8801021228075", "필굿엑스트라"},
                    {"8801021105192", "필굿1.6L"},
                    {"8801021217116", "버드와이저355캔"},
                    {"8801021223018", "스텔라355캔"},
                    {"8801021233826", "미켈롭alu330"},
                    {"8801858041984", "카스1L"},
                    {"8801858046583", "카스740캔"},
                    {"8801858044701", "카스병"},
                    {"8801021106045", "한맥1.6L"},
                    {"8801858133337", "카스1.6L"},
                    {"8801858047429", "카스라이트496팩"},
                    {"8801021232775", "호가든제제로355캔"},
                    {"8801021229294", "호가든제로500캔"},
                    {"8801021106588", "한맥464캔"},
                    {"4901777153325", "산토리500캔"},
                    {"8801858047245", "카스미니"},
                    {"8801858046743", "카스알뜰팩"},
                    {"8801858047344", "카스가성비팩"},
                    {"8801021232966", "호가든그린애플500캔"},
                    {"8801021233604", "엘파500캔"},
                    {"8801021213217", "버드와이저500캔"},
                    {"8801021105840", "한맥500캔"},
                    {"8801021105604", "한맥355캔"},
                    {"8801021105130", "필굿355캔"},
                    {"8801021083520", "카프리병"},
                    {"8801858047283", "카스alu473"}
                };

                for (String[] goods : goodsData) {
                    ContentValues values = new ContentValues();
                    values.put("barcode", Long.parseLong(goods[0]));
                    values.put("goods_name2", goods[1]);
                    db.insert(TABLE_GOODS, null, values);
                }
                
                Log.d("InitialData", "Goods data added");
            }
            goodsCursor.close();

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("InitialData", "Error inserting initial data: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    // 로그인 검증
    public EmployeeModel validateLogin(String id, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Log.d("LoginDebug", "Attempting login with id: " + id + " and password: " + password);
        
        // 먼저 사용자가 존재하는지 확인
        String checkQuery = "SELECT * FROM " + TABLE_EMPLOYEE_MD + " WHERE employee_id = ?";
        Cursor checkCursor = db.rawQuery(checkQuery, new String[]{id});
        
        if (checkCursor != null && checkCursor.moveToFirst()) {
            Log.d("LoginDebug", "User found, checking password");
            String storedPassword = checkCursor.getString(checkCursor.getColumnIndexOrThrow("employee_pwd"));
            Log.d("LoginDebug", "Stored password: " + storedPassword);
            
            // 모든 컬럼 값 출력
            String[] columns = checkCursor.getColumnNames();
            for (String column : columns) {
                String value = checkCursor.getString(checkCursor.getColumnIndexOrThrow(column));
                Log.d("LoginDebug", column + ": " + value);
            }
            
            checkCursor.close();
            
            if (password.equals(storedPassword)) {
                // 비밀번호가 일치하면 사용자 정보 반환
                String query = "SELECT * FROM " + TABLE_EMPLOYEE_MD +
                        " WHERE employee_id = ? AND employee_pwd = ?";
                
                Cursor cursor = db.rawQuery(query, new String[]{id, password});
                
                if (cursor != null && cursor.moveToFirst()) {
                    try {
                        EmployeeModel employee = new EmployeeModel(
                            cursor.getString(cursor.getColumnIndexOrThrow("employee_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("employee_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("employee_type")),
                            cursor.getString(cursor.getColumnIndexOrThrow("work_place_name"))
                        );
                        Log.d("LoginDebug", "Login successful for user: " + id);
                        cursor.close();
                        return employee;
                    } catch (Exception e) {
                        Log.e("LoginDebug", "Error creating EmployeeModel: " + e.getMessage());
                        cursor.close();
                        return null;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } else {
                Log.d("LoginDebug", "Password mismatch for user: " + id);
            }
        } else {
            Log.d("LoginDebug", "User not found: " + id);
        }
        if (checkCursor != null) {
            checkCursor.close();
        }
        
        return null;
    }

    // 모든 직원 조회
    public List<EmployeeModel> getAllEmployees() {
        List<EmployeeModel> employees = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_EMPLOYEE_MD + " WHERE employee_id != 'admin'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                EmployeeModel employee = new EmployeeModel(
                        cursor.getString(cursor.getColumnIndexOrThrow("employee_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("employee_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("employee_type")),
                        cursor.getString(cursor.getColumnIndexOrThrow("work_place_name"))
                );
                employee.setPhoneNum(cursor.getString(cursor.getColumnIndexOrThrow("phone_num")));
                employees.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return employees;
    }

    // 모든 근무지 조회
    public List<String> getAllWorkPlaces() {
        List<String> workPlaces = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(true, TABLE_WORK_PLACE,
                new String[]{"work_place_name"},
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                workPlaces.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return workPlaces;
    }

    // 직원 추가
    public void addEmployee(String employeeId, String name, String password,
                            String phone, String type, String address, String workPlace) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("employee_id", employeeId);
        values.put("employee_name", name);
        values.put("employee_pwd", password);
        values.put("phone_num", phone);
        values.put("employee_type", type);
        values.put("address", address);
        values.put("work_place_name", workPlace);

        db.insert(TABLE_EMPLOYEE_MD, null, values);
        db.close();
    }



    public List<AttendanceRecordModel> getMonthlyAttendance(String employeeId, int year, int month) {
        List<AttendanceRecordModel> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT c.*, e.employee_name FROM " + TABLE_COMMUTE + " c " +
                "JOIN " + TABLE_EMPLOYEE_MD + " e ON c.employee_id = e.employee_id " +
                "WHERE c.employee_id = ? " +
                "AND strftime('%Y', c.commute_day) = ? " +
                "AND strftime('%m', c.commute_day) = ? " +
                "ORDER BY c.commute_day ASC";

        String[] selectionArgs = {
                employeeId,
                String.valueOf(year),
                String.format(Locale.getDefault(), "%02d", month)
        };

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                AttendanceRecordModel record = new AttendanceRecordModel(
                        cursor.getString(cursor.getColumnIndexOrThrow("commute_day")),
                        cursor.getString(cursor.getColumnIndexOrThrow("employee_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("employee_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("start_time")),
                        cursor.getString(cursor.getColumnIndexOrThrow("end_time")),
                        cursor.getString(cursor.getColumnIndexOrThrow("work_place_name"))
                );
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }


    // 오늘의 출퇴근 기록 조회
    public Cursor getTodayCommute(String employeeId, String employeeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String query = "SELECT * FROM " + TABLE_COMMUTE +
                " WHERE employee_id = ? AND employee_name = ? AND commute_day = ?";

        return db.rawQuery(query, new String[]{employeeId, employeeName, currentDate});
    }

    // 출근 기록 저장
    public void saveCommute(String employeeId, String employeeName, String workPlaceName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        values.put("commute_day", currentDate);
        values.put("employee_id", employeeId);
        values.put("employee_name", employeeName);
        values.put("start_time", currentTime);
        values.put("end_time", "");
        values.put("work_place_name", workPlaceName);

        db.insertWithOnConflict(TABLE_COMMUTE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // 퇴근 시간 업데이트
    public void updateCommuteEndTime(String employeeId, String employeeName, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        values.put("end_time", currentTime);

        String whereClause = "employee_id = ? AND employee_name = ? AND commute_day = ?";
        String[] whereArgs = {employeeId, employeeName, date};

        db.update(TABLE_COMMUTE, values, whereClause, whereArgs);
        db.close();
    }

    // 최근 출근 기록 조회
    public String[] getRecentCommute(String employeeId, String employeeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] result = new String[2]; // [0]: 최근 출근, [1]: 최근 퇴근

        String query = "SELECT * FROM " + TABLE_COMMUTE +
                " WHERE employee_id = ? AND employee_name = ? " +
                " ORDER BY commute_day DESC, start_time DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{employeeId, employeeName});

        if (cursor != null && cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow("commute_day"));
            String startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));

            result[0] = date + " " + startTime;
            result[1] = endTime != null ? date + " " + endTime : "";

            cursor.close();
        }

        return result;
    }

    // 모든 상품 조회
    public List<GoodsModel> getAllGoods() {
        List<GoodsModel> goodsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GOODS, null, null, null, null, null, "goods_name2 ASC");

        if (cursor.moveToFirst()) {
            do {
                goodsList.add(new GoodsModel(
                    cursor.getString(cursor.getColumnIndexOrThrow("barcode")),
                    cursor.getString(cursor.getColumnIndexOrThrow("goods_name2"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return goodsList;
    }

    // 주문 저장
    public void saveOrder(String date, String employeeId, String employeeName, int boxCount, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        Log.d("OrderSave", "Attempting to save order: " + barcode + " for " + employeeName);
        
        // 상품명 조회
        Cursor cursor = db.query(TABLE_GOODS, 
            new String[]{"goods_name2"}, 
            "barcode = ?", 
            new String[]{barcode}, 
            null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String goodsName = cursor.getString(cursor.getColumnIndexOrThrow("goods_name2"));
            
            ContentValues values = new ContentValues();
            values.put("ordering_day", date);
            values.put("employee_id", employeeId);
            values.put("employee_name", employeeName);
            values.put("box_num", boxCount);
            values.put("barcode", barcode);
            values.put("goods_name2", goodsName);

            long result = db.insert(TABLE_ORDERING, null, values);
            Log.d("OrderSave", "Order saved with result: " + result + " for " + goodsName);
            
            cursor.close();
        } else {
            Log.e("OrderSave", "Product not found for barcode: " + barcode);
        }
        db.close();
    }

    // 특정 직원의 발주 내역 조회
    public List<OrderHistoryModel> getOrderHistory(String employeeId, String employeeName) {
        List<OrderHistoryModel> orderHistory = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT ordering_day, barcode, goods_name2, box_num FROM " + TABLE_ORDERING +
                " WHERE employee_id = ? AND employee_name = ?" +
                " ORDER BY ordering_day DESC";

        Cursor cursor = db.rawQuery(query, new String[]{employeeId, employeeName});

        if (cursor.moveToFirst()) {
            do {
                orderHistory.add(new OrderHistoryModel(
                    cursor.getString(cursor.getColumnIndexOrThrow("ordering_day")),
                    cursor.getString(cursor.getColumnIndexOrThrow("barcode")),
                    cursor.getString(cursor.getColumnIndexOrThrow("goods_name2")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("box_num"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderHistory;
    }

    // 날별로 그룹화된 발주 내역 조회
    public List<OrderHistoryModel> getOrderHistoryGroupByDate(String employeeId, String employeeName) {
        List<OrderHistoryModel> orderHistory = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT DISTINCT ordering_day FROM " + TABLE_ORDERING +
                " WHERE employee_id = ? AND employee_name = ?" +
                " ORDER BY ordering_day DESC";

        Cursor cursor = db.rawQuery(query, new String[]{employeeId, employeeName});

        if (cursor.moveToFirst()) {
            do {
                String orderingDay = cursor.getString(cursor.getColumnIndexOrThrow("ordering_day"));
                orderHistory.add(new OrderHistoryModel(
                    orderingDay,
                    employeeId,
                    employeeName,
                    getWorkPlaceNameForEmployee(employeeId, employeeName)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderHistory;
    }

    // 특정 날짜의 발주 상세 내역 회
    public List<OrderDetailModel> getOrderDetails(String employeeId, String employeeName, String orderingDay) {
        List<OrderDetailModel> details = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT goods_name2, box_num FROM " + TABLE_ORDERING +
                " WHERE employee_id = ? AND employee_name = ? AND ordering_day = ?" +
                " ORDER BY goods_name2 ASC";

        Log.d("OrderDetail", "Querying orders for " + employeeName + " on " + orderingDay);
        Cursor cursor = db.rawQuery(query, new String[]{employeeId, employeeName, orderingDay});

        if (cursor.moveToFirst()) {
            do {
                String goodsName = cursor.getString(cursor.getColumnIndexOrThrow("goods_name2"));
                int boxCount = cursor.getInt(cursor.getColumnIndexOrThrow("box_num"));
                
                Log.d("OrderDetail", "Found order: " + goodsName + " - " + boxCount + "박스");
                
                details.add(new OrderDetailModel(goodsName, boxCount));
            } while (cursor.moveToNext());
        } else {
            Log.d("OrderDetail", "No orders found");
        }
        cursor.close();
        return details;
    }

    // 직원의 근무지 이름 조회
    private String getWorkPlaceNameForEmployee(String employeeId, String employeeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String workPlaceName = "";

        String query = "SELECT work_place_name FROM " + TABLE_EMPLOYEE_MD +
                " WHERE employee_id = ? AND employee_name = ?";
        
        try {
            Cursor cursor = db.rawQuery(query, new String[]{employeeId, employeeName});
            
            if (cursor != null && cursor.moveToFirst()) {
                workPlaceName = cursor.getString(cursor.getColumnIndexOrThrow("work_place_name"));
            }
            
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error getting workplace name: " + e.getMessage());
        }

        return workPlaceName;
    }

    // 특정 날짜의 주문 삭제
    public void deleteOrdersByDate(String date, String employeeId, String employeeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_ORDERING + 
                            " WHERE ordering_day = ? AND employee_id = ? AND employee_name = ?";
        db.execSQL(deleteQuery, new String[]{date, employeeId, employeeName});
        db.close();
    }

    public void insertGoods(GoodsModel goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("barcode", goods.getBarcode());
        values.put("goods_name2", goods.getName());
        
        db.insert(TABLE_GOODS, null, values);
        db.close();
    }

    public void updateGoods(GoodsModel goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("goods_name2", goods.getName());
        
        db.update(TABLE_GOODS, values, "barcode = ?", 
            new String[]{goods.getBarcode()});
    }

    public void deleteGoods(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOODS, "barcode = ?", new String[]{barcode});
    }

    public void updateEmployee(EmployeeModel employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put("employee_name", employee.getEmployeeName());
        values.put("phone_num", employee.getPhoneNum());
        values.put("work_place_name", employee.getWorkPlaceName());
        
        db.update(TABLE_EMPLOYEE_MD, values, 
            "employee_id = ?", 
            new String[]{employee.getEmployeeId()});
        db.close();
    }

    public void deleteEmployee(String employeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE_MD, 
            "employee_id = ?", 
            new String[]{employeeId});
        db.close();
    }

    public void updateAllUserTypesToMD() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_type", "MD");
        
        db.update(TABLE_EMPLOYEE_MD, 
            values, 
            "employee_type = ?", 
            new String[]{"user"});
        db.close();
    }
}