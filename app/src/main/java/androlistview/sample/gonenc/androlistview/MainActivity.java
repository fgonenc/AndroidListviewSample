package androlistview.sample.gonenc.androlistview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ListView listView1;

    EditText editText1;
    EditText editText2;

    ImageView image;

    final String TABLE_NAME = "tblStudents";
    final String DB_NAME = "db1.db";
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView1);
        image.setImageResource(R.drawable.sgms);

        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, Name1 TEXT NOT NULL, Age INT(2));");

        listView1 = (ListView) findViewById(R.id.listView1); // add listview

        // adding listener
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
            {
                String item;
                switch (item = ((TextView) view).getText().toString() ){

                default: Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                    break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // settings
            Toast.makeText(getBaseContext(), "not available", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_LVfromArray) {
            // Fill LV from Array
            FillfromArray();
            return true;
        }

        if (id == R.id.action_show) {
            // Fill LV from DB
            FillfromDB();
            return true;
        }

        if (id == R.id.action_insert) {
            // Insert into table
            InsertData();
            return true;
        }

        if (id == R.id.action_quit) {
            // quit
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void InsertData() {
        View view;
        // add data to table
        editText1 = (EditText) findViewById(R.id.editText1); // important
        editText2 = (EditText) findViewById(R.id.editText2); // important

        if (editText1.getText().toString().length()<1 || editText2.getText().toString().length()<1 ) {
            editText1.setText("Enter Data !", TextView.BufferType.EDITABLE);
        }
        else
        {
            String str1 = editText1.getText().toString(); // Name
            String str2 = editText2.getText().toString(); // Age

            String strSQL = ("INSERT INTO tblStudents ('Name1','Age') VALUES ('" + str1 + "','" + str2 + "');");

            db.execSQL(strSQL);
            Log.i("MyActivity", strSQL + "<-strSQL value------------------");
            String strData = str1 + " " + str2;
            if(str1.length()>0 && str1 != null && !str1.isEmpty() && !android.text.TextUtils.isDigitsOnly(str1) ) {
                if(str2.length()>0 && str2 != null && !str2.isEmpty() && android.text.TextUtils.isDigitsOnly(str2) ) {
                    Toast toast = Toast.makeText(MainActivity.this, strData + " added to table " + TABLE_NAME, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.FILL, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "\n\nEnter Age !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.FILL, 0, 0);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(MainActivity.this, "\n\nEnter Name !" + TABLE_NAME, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.FILL, 0, 0);
                toast.show();
            }
        }
    }

    private void FillfromDB() {
        c = db.rawQuery("SELECT id, Name1, Age FROM tblStudents", null);
        Log.i("MyActivity", c.getCount() + "<-c.get count e------------------");
        int count = c.getCount();
            String values[] = new String[count];
            //int i = 0;
            int i = count - count;

            while(c.moveToNext())
            {
                values[i]= "id: " + c.getString(c.getColumnIndex("id"))+ "\n" + "Name: " + c.getString(c.getColumnIndex("Name1"))+ "\n" + "Age: " + c.getString(c.getColumnIndex("Age"));
                //values[i]= c.getString(c.getColumnIndex("Name1"));
                i++;
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1,values);
            listView1.setAdapter(adapter2);

    }

    private void FillfromArray() {
        // fill listview from Array
        String[] contenu = new String[] {"Paris","Istanbul","London","Helsinki","Frankfurt","New York","Tokyo","Milano","Madrid","Barcelona"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1,contenu); // simple_list_item_1
        listView1.setAdapter(adapter);
        Toast.makeText(getBaseContext(), "Done.", Toast.LENGTH_SHORT).show();
    }
}
