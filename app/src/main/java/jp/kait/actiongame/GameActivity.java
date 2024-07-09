package jp.kait.actiongame;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements Runnable{

    private ImageView kyara1;
    private ImageView rect;

    private TextView scoretext;

    private Button leftbtn;
    private Button rightbtn;

    private FrameLayout frame;

    private float rectx = 50f;
    private float recty = -100f;
    private float kyarax = 500f;
    private float kyaray = 0f;

    private float kyarawidth;
    private float kyaraheight;
    private float rectwidth;
    private float rectheight;

    private float screenx;
    private float screeny;

    private int score = 0;

    private volatile boolean clicklphase = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        kyara1 = findViewById(R.id.kyara1);
        rect = findViewById(R.id.rect);
        scoretext = findViewById(R.id.scoretext);
        frame = findViewById(R.id.frame);

        leftbtn = findViewById(R.id.leftbtn);
        rightbtn = findViewById(R.id.rightbtn);

        WindowManager win = getWindowManager();
        Display dis = win.getDefaultDisplay();
        Point poi = new Point();
        dis.getSize(poi);

        screenx = poi.x;
        screeny = poi.y;;

        ViewTreeObserver observer = kyara1.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                kyarawidth = kyara1.getWidth();
                kyaraheight = kyara1.getHeight();

                //yarax = screenx / 2;
                //kyara1.setX(kyarax);
                kyara1.setX(screenx / 2);
                kyara1.setY(screeny-kyaraheight);

                //scoretext.setText("kyara"+kyaraheight + "screen" + screeny);
            }
        });

        ViewTreeObserver observer2 = frame.getViewTreeObserver();
        observer2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                screenx = frame.getWidth();
                screeny = frame.getHeight();

                kyaray = screeny-kyaraheight;
                kyara1.setY(kyaray);
                //scoretext.setText("kyara"+k);
            }
        });

        ViewTreeObserver obser3 = rect.getViewTreeObserver();
        obser3.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rectwidth = rect.getWidth();
                rectheight = rect.getHeight();
            }
        });



        rect.setX(rectx);
        rect.setY(recty);

        scoretext.setText("Score:"+score);


        clicklphase = false;
        Thread thread = new Thread(this);
        thread.start();

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kyarax<=50f){
                    kyarax = 0f;
                }else{
                    kyarax -= 50f;
                }
                kyara1.setX(kyarax);
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kyarax >= (screenx-kyarawidth-50f)){
                    kyarax = (screenx-kyarawidth);
                }else{
                    kyarax += 50f;
                }
                kyara1.setX(kyarax);
            }
        });
    }

    @Override
    public void run() {
        int period = 10;
        while (!clicklphase){
            try {
                Thread.sleep(period);
            }catch (InterruptedException e){
                clicklphase = true;
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    recty += 3f;
                    if(recty>screeny){
                        recty = -100f;
                        Random random = new Random();
                        int x = random.nextInt((int)screenx-(int)rectx);
                        rectx = x;
                        //score += 1;
                        //scoretext.setText("score:"+score);
                    }

                    if((recty + rectheight) >= kyaray && recty <= (kyaray + kyaraheight) &&
                            (rectx + rectwidth) > kyarax && rectx < (kyarax + kyarawidth)) {
                        //clicklphase = true;
                        //scoretext.setText("GAME OVER");

                        score += 100;//追加
                        scoretext.setText("Score:"+score);//追加
                        resetRectPosition(); // rectの位置をリセット
                    }
                    rect.setX(rectx);
                    rect.setY(recty);
                }
            });
        }
    }
    private void resetRectPosition() {
        recty = -100f;
        Random random = new Random();
        int x = random.nextInt((int) screenx - (int) rectx);
        rectx = x;
    }
}
