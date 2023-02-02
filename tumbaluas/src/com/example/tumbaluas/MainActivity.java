package com.example.tumbaluas;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    dbh dm;
    EditText kode, judul, pengarang, idDel;
    Button addBtn, delBtn;
    TableLayout tabel4data;// tabel for data

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new dbh(this);
        setupView();
        fungsiBtn();
        updateTable();
    }
    public void setupView() {
        tabel4data = (TableLayout) findViewById(R.id.tabel_data);
        
        kode = (EditText) findViewById(R.id.inkode);
        judul= (EditText) findViewById(R.id.injudul);
        pengarang = (EditText) findViewById(R.id.inpeng);
        idDel = (EditText) findViewById(R.id.indel);

        addBtn = (Button) findViewById(R.id.btn_simpan);
        delBtn = (Button) findViewById(R.id.button1);
    }
    
    public void fungsiBtn() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpKamuta();
                kosongkanField();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                deleteData();
                kosongkanField();
            }
        });

    }
//    . fungsi button
    
    protected void kosongkanField() {
        kode.setText("");
        judul.setText("");
        pengarang.setText("");
        idDel.setText("");
    }

    private void deleteData() {
        dm.deleteBaris(idDel.getText().toString());
        updateTable();
    }
    
    //         penyimpanan data
        protected void simpKamuta() {
             try {
             dm.addRow(kode.getText().toString(),judul.getText().toString(),
             pengarang.getText().toString());
             updateTable();
             } catch (Exception e) {
                 e.printStackTrace();
         Toast.makeText(getBaseContext(),"gagal simpan,"+
         e.toString(),Toast.LENGTH_LONG).show();
         }
         }
//        . penyimpanan data
       
        // update tabel
                 protected void updateTable() {
                 while (tabel4data.getChildCount() > 1) {
                 tabel4data.removeViewAt(1);
                 }
                 ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();//
                 for (int posisi = 0; posisi < data.size(); posisi++) {
                 TableRow tabelBaris = new TableRow(this);
                 ArrayList<Object> baris = data.get(posisi);
               
                 TextView kodeTxt = new TextView(this);
                 kodeTxt.setText(baris.get(0).toString());
                 tabelBaris.addView(kodeTxt);
               
                 TextView namaTxt = new TextView(this);
                 namaTxt.setText(baris.get(1).toString());
                 tabelBaris.addView(namaTxt);
               
                 TextView hobiTxt = new TextView(this);
                 hobiTxt.setText(baris.get(2).toString());
                 tabelBaris.addView(hobiTxt);
               
                 tabel4data.addView(tabelBaris);
                 }
                 }
//                 . update tabel
    

}
