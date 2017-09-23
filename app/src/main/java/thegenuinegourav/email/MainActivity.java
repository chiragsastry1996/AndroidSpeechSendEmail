package thegenuinegourav.email;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

        import javax.mail.PasswordAuthentication;
        import javax.mail.Session;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextToSpeech.OnInitListener {
    public int flag=0;
    public int flag1=0;
    public String confirm;
    public String confirm1 = "send";
    public String again;
    public String again1 = "exit";
    public String cont;
    public String cont1 = "continue";
    private TextToSpeech tts;
    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);


        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        //speakOut();

        //Adding click listener
        buttonSend.setOnClickListener(this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                initiate();
            }
        }, 2000);



    }
    public void initiate(){
        speakOut();
        email();
    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
    public  void speech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try{
            startActivityForResult(intent,200);
        }catch (ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(),"Intent problem", Toast.LENGTH_SHORT).show();
        }
    }

    public void email(){
        flag1 = 1;
        speakOut();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flag = 1;
                speech();
            }
        }, 2000);

    }
    public void subject(){
        flag1 = 2;
        speakOut();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flag = 2;
                speech();
            }
        }, 2000);
    }
    public void body(){
        flag1 = 3;
        speakOut();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flag  = 3;
                speech();
            }
        }, 2000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                switch (flag) {
                    case 1 :  editTextEmail.setText("devilhercules@gmail.com"); subject();break;
                    case 2 :  editTextSubject.setText(result.get(0)); body(); break;
                    case 3 : editTextMessage.setText(result.get(0)); send(); break;
                    case 4 :    confirm = result.get(0).toString();
                                System.out.println("11111"+confirm);
                                if(confirm.equals(confirm1)){
                                    System.out.println("11111"+"doneeeeeee");
                                    sendEmail();
                                    again();
                    }break;
                    case 5 : again = result.get(0).toString();
                        System.out.println("22222"+again);
                        if(again.equals(again1)) {
                            System.out.println("2222" + "doneeeeeee");
                            finish();
                        }
                        else if(again.equals(cont1)) {
                            System.out.println("33333" + "doneeeeeee");
                            email();
                        }
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(MainActivity.this, rec.class);
        startActivity(myIntent);
    }
    public void send() {
        flag1 = 4;
        speakOut();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flag =4;
                speech();
            }
        }, 2500);

    }
    public void again() {
        flag1 = 5;
        speakOut();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flag =5;
                speech();
            }
        }, 2500);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage((new Locale("en","IN")));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
              //  btnSpeak.setEnabled(true);
               // speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    private void speakOut() {

        //CharSequence text = txtText.getText();
        CharSequence text = "Yo Mother fucker, it's not working";

        switch (flag1) {
            case 1 :  text = "Enter Email address";break;
            case 2 :  text = "Enter Subject";break;
            case 3 :  text = "Enter Body";break;
            case 4 :  text = "Do you wish to send? If yes, say send";break;
            case 5 :  text = "If you wish to continue say continue or else say exit";break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"id1");
        }
    }
}
