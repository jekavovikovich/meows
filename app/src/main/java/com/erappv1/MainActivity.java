package com.erappv1;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    Button btnflash;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Parameters params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnflash = (Button) findViewById(R.id.btnflash);



        /*
         * Обработчик события нажатий по кнопке
         */
        btnflash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                if (isFlashOn) {
                    //Выключаем фонарик:
                    turnOffFlash();
                } else {
                    //Включаем фонарик:
                    turnOnFlash();
                }
            }
        });





    }


    //Получаем параметры камеры:
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Ошибка, невозможно запустить: ", e.getMessage());
            }
        }
    }

    /*
     * Включаем
     */
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }


    /*
     * Выключаем
     */
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Временно выключаем фонарик:
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Продолжаем работу фонарика:
        if(hasFlash)
            turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Получаем для приложения параметры камеры:
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Закрываем работу камеры:
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


}
