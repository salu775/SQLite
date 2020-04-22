package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button createBTN, insertBTN, getValuesBTN;
    private DatabaseClass objectDatabaseClass;
    private EditText nameET, locationET;
    private TextView getValuesTV;
    private RecyclerView objectRecyclerView;

    private ArrayList<ModelClass> objectModelClassArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

    }

    private void initialize() {
        try {
            createBTN=findViewById(R.id.createBTN);
            nameET=findViewById(R.id.nameET);
            getValuesBTN=findViewById(R.id.getValuesBTN);
            getValuesTV=findViewById(R.id.getValuesTV);
            locationET=findViewById(R.id.locationET);
            insertBTN=findViewById(R.id.insertBTN);
            objectRecyclerView=findViewById(R.id.RV);
            objectDatabaseClass= new DatabaseClass(this);
            objectModelClassArrayList = new ArrayList<>();

            createBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  createDB();
                }
            });
            insertBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertValues();
                }
            });
            getValuesBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchValuesFromDatabase();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "initialize", Toast.LENGTH_SHORT).show();
        }
    }
    private void createDB()
    {
        try
        {
            objectDatabaseClass.getReadableDatabase();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "createDB", Toast.LENGTH_SHORT).show();

        }
    }
    private void insertValues()
    {
        try
        {
            if (!nameET.getText().toString().isEmpty() && !locationET.getText().toString().isEmpty())
            {
                SQLiteDatabase objectSQLiteDatabase=objectDatabaseClass.getWritableDatabase();
                if (objectSQLiteDatabase!=null)
                {
                    ContentValues objectContentValues= new ContentValues();
                    objectContentValues.put("Location", locationET.getText().toString());
                    objectContentValues.put("Name", nameET.getText().toString());
                    long check = objectSQLiteDatabase.insert("myTable",null,objectContentValues);
                    if (check!=-1)
                    {
                        Toast.makeText(this, "Value Inserted", Toast.LENGTH_SHORT).show();
                        nameET.setText(null);
                        locationET.setText(null);
                        nameET.requestFocus();
                    }
                    else
                    {
                        Toast.makeText(this, "Failed to add values", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "DB Obj is null", Toast.LENGTH_SHORT).show();
                }
            }
            else if(nameET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
                nameET.requestFocus();
            }
            else if (locationET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter Location", Toast.LENGTH_SHORT).show();
                locationET.requestFocus();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "insertValues"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchValuesFromDatabase()
    {
        try
        {
            SQLiteDatabase objectSQLiteDatabase = objectDatabaseClass.getReadableDatabase();
            if (objectSQLiteDatabase!=null)
            {
               Cursor objectCursor = objectSQLiteDatabase.rawQuery("select * from myTable", null);
               StringBuffer objectStringBuffer = new StringBuffer();
               if (objectCursor.getCount()!=0)
               {
                    while (objectCursor.moveToNext())
                    {
                        ModelClass objectModelClass = new ModelClass();
                        objectModelClass.setName(objectCursor.getString(0));
                        objectModelClass.setLocation(objectCursor.getString(1));
                        objectModelClassArrayList.add(objectModelClass);
                    //  objectStringBuffer.append("Name:"+objectCursor.getString(0)+"\n");
                    //    objectStringBuffer.append("Location:"+objectCursor.getString(1)+"\n");

                    }
                   // getValuesTV.setText(objectStringBuffer);
                   objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    AdapterClass objectAdapterClass = new AdapterClass(objectModelClassArrayList);
                    objectRecyclerView.setAdapter(objectAdapterClass);

               }
               else
               {
                   Toast.makeText(this, "No values fetched", Toast.LENGTH_SHORT).show();
               }
            }
            else 
            {
                Toast.makeText(this, "Database Object is Null", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "fetchValuesFromDatabase"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}