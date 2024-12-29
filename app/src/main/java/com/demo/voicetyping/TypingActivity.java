package com.demo.voicetyping;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;


public class TypingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView bck_buttn;
    private ImageView btnSpeak;
    ImageView clear_txt;
    ImageView copy_txt;
    EditText editText;
    ImageView facebook;
    ImageView img_flag;
    String mLocale;
    ImageView paste_btn;
    ImageView read_btn;
    ImageView share_txt;
    ImageView sms_btn;
    Spinner spin;
    TextToSpeech textToSpeech;
    ImageView twiter;
    ImageView whatsapp;
    String[] countryNames = {"English", "Urdu", "German", "French", "Chinese", "Arabic", "Spanish", "Japanese", "Korean", "Hindi", "Azerbaijani", "Danish", "Dutch", "Greek", "Indonesian", "Marathi", "Mongolian", "Nepali", "Portuguese", "Zulu"};
    String mLangCode = "";

    @Override 
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_typing);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);

        this.btnSpeak = (ImageView) findViewById(R.id.mic_btn_pro);
        this.editText = (EditText) findViewById(R.id.edt_txt_pro);
        this.bck_buttn = (ImageView) findViewById(R.id.back_btn);
        this.clear_txt = (ImageView) findViewById(R.id.clear_pro_txt);
        this.copy_txt = (ImageView) findViewById(R.id.copy_pro_txt);
        this.share_txt = (ImageView) findViewById(R.id.share_pro_txt);
        this.read_btn = (ImageView) findViewById(R.id.speak_pro_btn);
        this.paste_btn = (ImageView) findViewById(R.id.paste_btn);
        this.sms_btn = (ImageView) findViewById(R.id.sms_send);
        this.whatsapp = (ImageView) findViewById(R.id.whatsapp);
        this.facebook = (ImageView) findViewById(R.id.facebook);
        this.twiter = (ImageView) findViewById(R.id.twiter);
        this.img_flag = (ImageView) findViewById(R.id.img_flag);
        this.twiter.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().isEmpty()) {
                    Toast.makeText(TypingActivity.this, "No text to Share", Toast.LENGTH_SHORT).show();
                } else if (TypingActivity.this.editText.getText().length() > 0) {
                    TypingActivity.this.shareTwitter(new String());
                }
            }
        });
        this.sms_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().length() > 0) {
                    try {
                        TypingActivity.this.startActivity(new Intent("android.intent.action.SEND").setType("text/plain").setPackage(Telephony.Sms.getDefaultSmsPackage(TypingActivity.this)).putExtra("android.intent.extra.TEXT", TypingActivity.this.editText.getText().toString()));
                        return;
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(TypingActivity.this, "SMS not installed.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(TypingActivity.this, "No text? Please write something", Toast.LENGTH_SHORT).show();
            }
        });
        this.facebook.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().length() > 0) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.TEXT", TypingActivity.this.editText.getText().toString());
                    for (ResolveInfo resolveInfo : TypingActivity.this.getApplicationContext().getPackageManager().queryIntentActivities(intent, 0)) {
                        if (resolveInfo.activityInfo.name.contains("facebook")) {
                            ActivityInfo activityInfo = resolveInfo.activityInfo;
                            ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                            intent.addCategory("android.intent.category.LAUNCHER");
                            intent.setComponent(componentName);
                            TypingActivity.this.startActivity(intent);
                            return;
                        }
                    }
                    return;
                }
                Toast.makeText(TypingActivity.this, "No text? Please write something", Toast.LENGTH_SHORT).show();
            }
        });
        this.whatsapp.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().length() > 0) {
                    try {
                        TypingActivity.this.startActivity(new Intent("android.intent.action.SEND").setType("text/plain").setPackage("com.whatsapp").putExtra("android.intent.extra.TEXT", TypingActivity.this.editText.getText().toString()));
                        return;
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(TypingActivity.this, "Whatsapp  not  installed.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(TypingActivity.this, "No text? Please write something", Toast.LENGTH_SHORT).show();
            }
        });
        this.bck_buttn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                TypingActivity.this.onBackPressed();
            }
        });
        this.btnSpeak.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                TypingActivity.this.promptSpeechInput();
            }
        });
        this.read_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().length() > 0) {
                    TypingActivity.this.speakWord(new Locale(TypingActivity.this.mLocale), TypingActivity.this.mLangCode, TypingActivity.this.editText.getText().toString());
                } else {
                    TypingActivity.this.speakWord(new Locale(TypingActivity.this.mLocale), TypingActivity.this.mLangCode, TypingActivity.this.editText.getHint().toString());
                }
            }
        });
        this.copy_txt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().isEmpty()) {
                    Toast.makeText(TypingActivity.this, "No text to Copy", Toast.LENGTH_SHORT).show();
                    return;
                }
                ((ClipboardManager) TypingActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Code: ", TypingActivity.this.editText.getText().toString()));
                Toast.makeText(view.getContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });
        this.clear_txt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().isEmpty()) {
                    Toast.makeText(TypingActivity.this, "No text to clear", Toast.LENGTH_SHORT).show();
                } else {
                    TypingActivity.this.editText.setText("");
                }
            }
        });
        this.paste_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                TypingActivity typingActivity = TypingActivity.this;
                view.getContext();
                TypingActivity.this.editText.setText(((ClipboardManager) typingActivity.getSystemService(Context.CLIPBOARD_SERVICE)).getText());
            }
        });
        this.sms_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().length() > 0) {
                    try {
                        TypingActivity.this.startActivity(new Intent("android.intent.action.SEND").setType("text/plain").setPackage(Telephony.Sms.getDefaultSmsPackage(TypingActivity.this)).putExtra("android.intent.extra.TEXT", TypingActivity.this.editText.getText().toString()));
                        return;
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(TypingActivity.this, "SMS not installed.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(TypingActivity.this, "No text? Please write something", Toast.LENGTH_SHORT).show();
            }
        });
        this.share_txt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TypingActivity.this.editText.getText().toString().isEmpty()) {
                    Toast.makeText(TypingActivity.this, "No text to share", Toast.LENGTH_SHORT).show();
                    return;
                }
                String obj = TypingActivity.this.editText.getText().toString();
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", obj);
                TypingActivity.this.startActivity(Intent.createChooser(intent, "select app ..."));
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner_pronounce);
        this.spin = spinner;
        spinner.setOnItemSelectedListener(this);
        this.spin.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.spiner_bg));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.countryNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spin.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spin.setSelection(SharedPref.getInstance(getApplicationContext()).getIntPref("selected_lang", 0));
    }

    @Override 
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        SharedPref.getInstance(getApplicationContext()).savePref("selected_lang", i);
        if (i == 0) {
            this.mLocale = "en_US";
            this.mLangCode = "en";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Press mic button below to Write SMS..");
            this.img_flag.setImageResource(R.drawable.english);
        } else if (i == 1) {
            this.mLocale = "ur-PK";
            this.mLangCode = "ur";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("ایس ایم ایس لکھنے کے لئے نیچے مائک بٹن دبائیں");
            this.img_flag.setImageResource(R.drawable.urdu);
        } else if (i == 2) {
            this.mLocale = "de-DE";
            this.mLangCode = "de";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Drücken Sie die Mikrofontaste unten, um eine SMS zu schreiben");
            this.img_flag.setImageResource(R.drawable.german);
        } else if (i == 3) {
            this.mLocale = "fr-FR";
            this.mLangCode = "fr";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Appuyez sur le bouton du micro ci-dessous pour écrire des SMS");
            this.img_flag.setImageResource(R.drawable.french);
        } else if (i == 4) {
            this.mLocale = "yue-Hant-HK";
            this.mLangCode = "zh";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("按下面的麦克风按钮写短信");
            this.img_flag.setImageResource(R.drawable.chinese);
        } else if (i == 5) {
            this.mLocale = "ar-SA";
            this.mLangCode = "ar";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("اضغط على زر الميكروفون أدناه لكتابة الرسائل القصيرة");
            this.img_flag.setImageResource(R.drawable.arabic);
        } else if (i == 6) {
            this.mLocale = "es-ES";
            this.mLangCode = "es";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Presione el botón del micrófono a continuación para escribir SMS");
            this.img_flag.setImageResource(R.drawable.spanish);
        } else if (i == 7) {
            this.mLocale = "ja-JP";
            this.mLangCode = "ja";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("下のマイクボタンを押してSMSを書き込みます");
            this.img_flag.setImageResource(R.drawable.japanese);
        } else if (i == 8) {
            this.mLocale = "ko-KR";
            this.mLangCode = "ko";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("SMS를 작성하려면 아래 마이크 버튼을 누르세요.");
            this.img_flag.setImageResource(R.drawable.korean);
        } else if (i == 9) {
            this.mLocale = "hi-IN";
            this.mLangCode = "hi";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("एसएमएस लिखने के लिए नीचे माइक बटन दबाएं");
            this.img_flag.setImageResource(R.drawable.hindi);
        } else if (i == 10) {
            this.mLocale = "az-AZ";
            this.mLangCode = "az";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("SMS yazmaq üçün aşağıdakı mikro düyməyə basın");
            this.img_flag.setImageResource(R.drawable.azerbaijani);
        } else if (i == 11) {
            this.mLocale = "da-DK";
            this.mLangCode = "da";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Tryk på mikrofonknappen nedenfor for at skrive SMS");
            this.img_flag.setImageResource(R.drawable.danish);
        } else if (i == 12) {
            this.mLocale = "nl-NL";
            this.mLangCode = "nl";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Druk op de microfoonknop hieronder om SMS te schrijven");
            this.img_flag.setImageResource(R.drawable.dutch);
        } else if (i == 13) {
            this.mLocale = "el-GR";
            this.mLangCode = "el";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Πατήστε το κουμπί μικροφώνου παρακάτω για να γράψετε SMS");
            this.img_flag.setImageResource(R.drawable.greek);
        } else if (i == 14) {
            this.mLocale = "id-ID";
            this.mLangCode = "id";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Tekan tombol mic di bawah ini untuk Menulis SMS");
            this.img_flag.setImageResource(R.drawable.indonesian);
        } else if (i == 15) {
            this.mLocale = "mr-IN";
            this.mLangCode = "mr";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("एसएमएस लिहिण्यासाठी खाली माइक बटण दाबा");
            this.img_flag.setImageResource(R.drawable.marathi);
        } else if (i == 16) {
            this.mLocale = "mn-MN";
            this.mLangCode = "mn";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("SMS бичихийн тулд доорхи микро товчийг дарна уу");
            this.img_flag.setImageResource(R.drawable.mangolian);
        } else if (i == 17) {
            this.mLocale = "ne-NP";
            this.mLangCode = "ne";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("एसएमएस लेख्नको लागि माईक बटन थिच्नुहोस्");
            this.img_flag.setImageResource(R.drawable.nepali);
        } else if (i == 18) {
            this.mLocale = "pt-PT";
            this.mLangCode = "pt";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Pressione o botão do microfone abaixo para escrever SMS");
            this.img_flag.setImageResource(R.drawable.portuguese);
        } else if (i == 19) {
            this.mLocale = "zu-ZA";
            this.mLangCode = "zu";
            stopSpeech();
            this.editText.setText("");
            this.editText.setHint("Cindezela inkinobho yemakrofoni ngezansi ukubhala i-SMS");
            this.img_flag.setImageResource(R.drawable.zulu);
        }
    }

    
    public void promptSpeechInput() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", this.mLocale);
        intent.putExtra("android.speech.extra.PROMPT", "Listening!!");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(getApplicationContext(), "Sorry your device not supported", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeTextToSpeech(final Locale locale, final String str, final String str2) {
        this.textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() { 
            @Override 
            public void onInit(int i) {
                if (i == 0) {
                    Locale locale2 = locale;
                    if (locale2 != null) {
                        TypingActivity.this.speakWord(locale2, str, str2);
                        return;
                    }
                    return;
                }
                TypingActivity.this.textToSpeech = null;
            }
        });
    }

    
    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        super.onActivityResult(i, i2, intent);
        if (i == 100 && i2 == -1 && intent != null) {
            this.editText.setText(intent.getStringArrayListExtra("android.speech.extra.RESULTS").get(0));
        }
    }

    
    public void speakWord(Locale locale, String str, String str2) {
        TextToSpeech textToSpeech = this.textToSpeech;
        if (textToSpeech == null) {
            initializeTextToSpeech(locale, str, str2);
            return;
        }
        textToSpeech.setLanguage(locale);
        if (Build.VERSION.SDK_INT >= 21) {
            ttsGreater21(str2);
        } else {
            ttsUnder20(str2);
        }
    }

    private void ttsGreater21(String str) {
        if (this.textToSpeech != null) {
            this.textToSpeech.speak(str, 0, null, hashCode() + "");
        }
    }

    private void ttsUnder20(String str) {
        if (this.textToSpeech != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("utteranceId", "MessageId");
            this.textToSpeech.speak(str, 0, hashMap);
        }
    }

    public void stopSpeech() {
        TextToSpeech textToSpeech = this.textToSpeech;
        if (textToSpeech == null) {
            return;
        }
        try {
            if (textToSpeech.isSpeaking()) {
                this.textToSpeech.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        stopSpeech();
    }

    
    public void shareTwitter(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", "This is a Test.");
        intent.setType("text/plain");
        boolean z = false;
        Iterator<ResolveInfo> it = getPackageManager().queryIntentActivities(intent, 0).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ResolveInfo next = it.next();
            if (next.activityInfo.packageName.startsWith("com.twitter.android")) {
                intent.setClassName(next.activityInfo.packageName, next.activityInfo.name);
                z = true;
                break;
            }
        }
        if (z) {
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.putExtra("android.intent.extra.TEXT", str);
        intent2.setAction("android.intent.action.VIEW");
        intent2.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(str)));
        startActivity(intent2);
        Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
    }

    private String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }
}
