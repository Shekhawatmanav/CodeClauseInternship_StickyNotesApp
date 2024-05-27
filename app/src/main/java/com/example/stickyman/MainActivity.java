package com.example.stickyman;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText noteEdt;
    private Button incsizeBtn,decrsizeBtn,BoldBtn,ItlBtn,UdlBtn;
    private TextView textsize;
    StickyNote note = new StickyNote();
    float currentsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteEdt = findViewById(R.id.idEdt);
        incsizeBtn = findViewById(R.id.idincreasesize);
        decrsizeBtn = findViewById(R.id.idreducesize);
        BoldBtn = findViewById(R.id.IdBtnBold);
        UdlBtn = findViewById(R.id.IdBtnUdl);
        ItlBtn = findViewById(R.id.IdBtnIt);
        textsize = findViewById(R.id.Size);
        currentsize = noteEdt.getTextSize();
        textsize.setText( ""+currentsize);

        incsizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textsize.setText(""+currentsize);
                currentsize++;
                noteEdt.setTextSize(currentsize);
            }
        });


        decrsizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textsize.setText(""+currentsize);
                currentsize-- ;
                noteEdt.setTextSize(currentsize);
            }
        });

        BoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItlBtn.setTextColor(getResources().getColor(R.color.white));
                ItlBtn.setBackgroundColor(getResources().getColor(R.color.black));
                if(noteEdt.getTypeface().isBold()){
                    noteEdt.setTypeface(Typeface.DEFAULT);
                    BoldBtn.setTextColor(getResources().getColor(R.color.white));
                    BoldBtn.setBackgroundColor(getResources().getColor(R.color.black));
                }else {
                    BoldBtn.setTextColor(getResources().getColor(R.color.black));
                    BoldBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    noteEdt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            }
        });

        UdlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteEdt.getPaintFlags()==8){
                    UdlBtn.setTextColor(getResources().getColor(R.color.white));
                    UdlBtn.setBackgroundColor(getResources().getColor(R.color.black));
                noteEdt.setPaintFlags(noteEdt.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));

                }else{
                    UdlBtn.setTextColor(getResources().getColor(R.color.black));
                    UdlBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    noteEdt.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                }

            }
        });

        ItlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoldBtn.setTextColor(getResources().getColor(R.color.white));
                BoldBtn.setBackgroundColor(getResources().getColor(R.color.black));

                if(noteEdt.getTypeface().isItalic() ){
                    noteEdt.setTypeface(Typeface.DEFAULT);
                    ItlBtn.setTextColor(getResources().getColor(R.color.white));
                    ItlBtn.setBackgroundColor(getResources().getColor(R.color.black));
                }else {
                    ItlBtn.setTextColor(getResources().getColor(R.color.black));
                    ItlBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    noteEdt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                }
            }
        });


    }

    public void SaveButton(View view) {
        note.setStick(noteEdt.getText().toString(),this );
        UpdateWidget();
        Toast.makeText(this,"Note Has Been Updated!",Toast.LENGTH_SHORT).show();
    }

    private void UpdateWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.widget_layout);
        ComponentName thsWidget = new ComponentName(this,AppWidget.class);
        remoteViews.setTextViewText(R.id.idTVwidget,noteEdt.getText().toString());
        appWidgetManager.updateAppWidget(thsWidget,remoteViews);

    }
}