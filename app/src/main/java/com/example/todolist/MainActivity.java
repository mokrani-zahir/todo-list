package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList listTask;
    MyCustomAdapter myadpter;
    Button addTask;
    EditText textTask;
    Base base = new Base(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.list_view);
        addTask = (Button) findViewById(R.id.addTask);
        textTask = (EditText) findViewById(R.id.textTask);

        listTask = new ArrayList();


        myadpter = new MyCustomAdapter(listTask);
        listview.setAdapter(myadpter);
        setViewTask();

        addTask.setOnClickListener(v -> {
            base.insetTask(textTask.getText().toString());
            setViewTask();
        });



    }

    public void setViewTask(){
        //Items.add(new ListIngridiant("Farin",500,"g"));
        listTask.removeAll(listTask);

        Cursor c = base.getAllTask();

        for (int i = 0; i < c.getCount(); i++) {

            // Position the cursor
            c.moveToNext();

            // Fetch your data values
            int ids = c.getInt(c.getColumnIndex("id"));
            String textTask = c.getString(c.getColumnIndex("textTask"));

            listTask.add(new Task(ids,textTask));

        }

        myadpter.notifyDataSetChanged();
    }

    class MyCustomAdapter extends BaseAdapter {

        ArrayList<Task> Items = new ArrayList<Task>();
        MyCustomAdapter(ArrayList Items){
            this.Items = Items;
        }

        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public Object getItem(int position) {
            return Items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item,null);

            TextView textItem = (TextView) view.findViewById(R.id.text_task);
            LinearLayout btnEdit = (LinearLayout) view.findViewById(R.id.btn_edit);
            LinearLayout btnRemove = (LinearLayout) view.findViewById(R.id.btn_remove);
            textItem.setText(Items.get(position).getTextTask());

            btnEdit.setOnClickListener(v -> {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_edit_task);

                EditText textTask = (EditText) dialog.findViewById(R.id.new_text_task);
                Button btnedit = (Button) dialog.findViewById(R.id.btn_task_edit);
                Button btnColse = (Button) dialog.findViewById(R.id.btn_edit_close);

                textTask.setText(Items.get(position).getTextTask());

                btnedit.setOnClickListener(v1 -> {
                    Log.i("App",Items.get(position).getTextTask()+","+Items.get(position).getIdTask());
                    base.modifierTask(textTask.getText().toString(),Items.get(position).getIdTask());
                    setViewTask();
                    dialog.dismiss();
                });

                btnColse.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });



                dialog.show();
            });

            btnRemove.setOnClickListener(v -> {
                base.deletTask(Items.get(position).getIdTask());
                setViewTask();
            });
            //textItem.setText((Integer) Items.get(position));
            return view;
        }
    }
}