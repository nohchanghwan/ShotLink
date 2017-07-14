package com.test.sy.shotlinkone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.provider.Browser;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class FileService extends Service {

    ScreenshotObserver sc;
    FileObserver fo;

    String filepath="a";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HandlerThread handlerThread = new HandlerThread("content_observer");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };


        getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                new ContentObserver(handler) {
                    @Override
                    public boolean deliverSelfNotifications() {
                        Log.d("dd", "deliverSelfNotifications");
                        return true;
                    }

                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                    }

                    @Override
                    public void onChange(boolean selfChange, Uri uri) {

                        try {
                            Cursor mcursor = getContentResolver().query(uri, null, null, null, null );
                            //미디어 폴더 전체의 변화를 감지한다.
                            mcursor.moveToLast();   // cursor 끝으로 이동

                            String paths = mcursor.getString(mcursor.getColumnIndex("_data"));
                            filepath=paths;

                            //스크린 샷인가??
                            if (paths.contains("Screenshots")) {
                                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(FileService.this);


                                    try {

                                        String[] proj = new String[] { Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL};     //내가 가져올 것
                                        Uri uriasd = Uri.parse("content://com.sec.android.app.sbrowser.browser/history");   //History로 접근
                                        String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark

                                        Cursor mCur = getContentResolver().query(uriasd, proj, sel, null, null);    //History 쿼리 가져오기
                                        mCur.moveToLast();  //최근 History 받기


                                        String url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL));





                                builder.setSmallIcon(R.drawable.asd)   // Push 알람에서 이미지 선택
                                        .setColor(Color.argb(255, 248, 238, 225))
                                        .setContentTitle("스크린샷 저장")
                                        .setContentText("스크린샷을 확인해주세요!")
                                        .setAutoCancel(true);


                                        Intent intent = new Intent(getApplicationContext(),SQLSaved.class);
                                        intent.putExtra("filepath", filepath);
                                        intent.putExtra("url",url);


                                        PendingIntent pintent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        builder.setContentIntent(pintent);
                                        manager.notify(1,builder.build());


                                    }
                                    catch (Exception e) {
                                        e.getMessage();
                                    }


                            } else {
                                /*Toast.makeText(FileService.this, "스크린 샷이 아님", Toast.LENGTH_SHORT).show();*/
                            }
                        }catch(Exception e)
                        {
                            Log.d("No file","파일이 삭제됨");
                            e.printStackTrace();
                        }


                        super.onChange(selfChange, uri);
                    }
                }
        );
        return 0;
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();
    }
}

