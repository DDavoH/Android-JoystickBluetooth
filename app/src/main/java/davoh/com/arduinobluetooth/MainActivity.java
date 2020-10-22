package davoh.com.arduinobluetooth;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    //VARIABLES DE BOTONES
    RelativeLayout layout_joystick;
    RelativeLayout layout_joystick2;

    TextView textView1, textView2, textView3, textView4, textView5;
    TextView textView6, textView7, textView8, textView9, textView10;

    JoyStickClass js;
    JoyStickClass js2;



    //VARIABLES BLUETOOTH
    public Handler bluetooothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //private static final UUID BTMODULEUUID = UUID.fromString("FDA50693A4E24FB1AFCFC6EB07647825");
    private static String address = null;
    int direction=0;
    int direction2=0;
    int stop=0;
    int stop2=0;
    int valory=0;
    int valory2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerifivarEstadoBT();

        //JOYSTICK
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);

        layout_joystick = findViewById(R.id.layout_joystick);
        layout_joystick2 = findViewById(R.id.layout_joystick2);

        //de 0 a 300

        /*js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        js2 = new JoyStickClass(getApplicationContext()
                , layout_joystick2, R.drawable.image_button);
        js2.setStickSize(150, 150);
        js2.setLayoutSize(500, 500);
        js2.setLayoutAlpha(150);
        js2.setStickAlpha(100);
        js2.setOffset(90);
        js2.setMinimumDistance(50);
*/
        //dde 100 a 999
        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        js2 = new JoyStickClass(getApplicationContext()
                , layout_joystick2, R.drawable.image_button);
        js2.setStickSize(150, 150);
        js2.setLayoutSize(500, 500);
        js2.setLayoutAlpha(150);
        js2.setStickAlpha(100);
        js2.setOffset(90);
        js2.setMinimumDistance(50);


        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    //almacenar valory
                    valory=js.getY();
                    if(valory>0){
                        direction=1;
                    }else if(valory<0){
                        valory=((valory)*(-1));
                        direction=0;
                    }



                    if(valory<=50 && valory>0){
                        valory=50;
                    }
                    if(valory==0){
                        stop=1;
                        valory=100;
                    }else if(valory>0){
                        stop=0;
                        //va a ir de 101 a 500
                        valory=valory*2;
                    }



                    textView3.setText("Angle : " +(js.getAngle()));
                    textView4.setText("Distance : " +(js.getDistance()));

                    int direction = js.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        textView5.setText("Direction : Up");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        textView5.setText("Direction : Up Right");
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        textView5.setText("Direction : Down Right");
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        textView5.setText("Direction : Down");
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        textView5.setText("Direction : Down Left");
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        textView5.setText("Direction : Up Left");
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        textView5.setText("Direction : Center");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    stop=1;
                    valory=0;
                    textView1.setText("X :");
                    textView2.setText("Y :");
                    textView3.setText("Angle :");
                    textView4.setText("Distance :");
                    textView5.setText("Direction :");
                }


                return true;
            }
        });
        //JOYSTICK

        //JOYSTICK2
        layout_joystick2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js2.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView6.setText("X : " + (js2.getX()));
                    textView7.setText("Y : " + (js2.getY()));
                    valory2=js2.getY();

                    if(valory2>0){
                        direction2=1;
                    }else if(valory2<0){
                        valory2=((valory2)*(-1));
                        direction2=0;
                    }



                    if(valory2<=50 && valory2>0){
                        valory2=50;
                    }
                    if(valory2==0){
                        stop2=1;
                        valory2=100;
                    }else if(valory2>0){
                        stop2=0;
                        //va a ir de 101 a 500
                        valory2=valory2*2;
                    }



                    EnviarPosiciones();


                    textView8.setText("Angle : " + (js2.getAngle()));
                    textView9.setText("Distance : " + (js2.getDistance()));

                    int direction = js2.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        textView10.setText("Direction : Up");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        textView10.setText("Direction : Up Right");
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        textView10.setText("Direction : Right");
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        textView10.setText("Direction : Down Right");
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        textView10.setText("Direction : Down");
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        textView10.setText("Direction : Down Left");
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        textView10.setText("Direction : Left");
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        textView10.setText("Direction : Up Left");
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        textView10.setText("Direction : Center");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    //reset values
                    stop2=1;
                    valory2=0;
                    textView6.setText("X :");
                    textView7.setText("Y :");
                    textView8.setText("Angle :");
                    textView9.setText("Distance :");
                    textView10.setText("Direction :");
                }


                return true;
            }
        });
//JOYSTICK2




    }


    public void EnviarPosiciones(){

            MyConexionBT.write("*" + direction + stop + valory + direction2+ stop2 +valory2+"#");

    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    public void onResume() {
        super.onResume();
        Intent intent = getIntent();

        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);

        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacion del socket fallo", Toast.LENGTH_SHORT).show();

        }

        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
            }
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            btSocket.close();

        } catch (IOException e2) {
        }
    }

    private void VerifivarEstadoBT() {
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_SHORT).show();

        } else {
            if (btAdapter.isEnabled()) {

            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //Crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetooothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}




