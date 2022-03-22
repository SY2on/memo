package com.example.mymemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button btnAdd;

    List<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteHelper(MainActivity.this);
        memoList = dbHelper.selectAll();

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){

            String strMain = data.getStringExtra("main");
            String strTime = data.getStringExtra("time");

            Memo memo = new Memo(strMain, strTime, 0);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            dbHelper.insertMemo(memo);
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private List<Memo> listmemo;

        public RecyclerAdapter(List<Memo> listmemo) {
            this.listmemo = listmemo;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listmemo.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            Memo memo = listmemo.get(i);

            itemViewHolder.mainContent.setTag(memo.getSeq());

            itemViewHolder.mainContent.setText(memo.getMainContent());
            itemViewHolder.regTime.setText(memo.getRegTime());

            if(memo.getIsdone() == 0){
                itemViewHolder.checkbox.setBackgroundColor(Color.LTGRAY);
            } else{
                itemViewHolder.checkbox.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.skyblue));
            }
        }

        void addItem(Memo memo){
            listmemo.add(memo);
        }

        void removeItem(int position){
            listmemo.remove(position);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{

            private TextView mainContent;
            private TextView regTime;
            private ImageView checkbox;

            public ItemViewHolder(@NonNull final View itemView){
                super(itemView);

                mainContent = itemView.findViewById(R.id.item_mainContent);
                regTime= itemView.findViewById(R.id.item_regTime);
                checkbox= itemView.findViewById(R.id.item_checkbox);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        int position =getBindingAdapterPosition();
                        int seq = (int)mainContent.getTag();
                        Toast.makeText(MainActivity.this, "길게 누르면 삭제됩니다.", Toast.LENGTH_SHORT).show();
                        if(position != RecyclerView.NO_POSITION){
                            dbHelper.deleteMemo(seq);
                            removeItem(position);
                            notifyDataSetChanged();
                            }

                        return false;
                    }
                });
                checkbox.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ColorDrawable buttonColor = (ColorDrawable) checkbox.getBackground();
                        int colorId = buttonColor.getColor();
                        if( colorId == Color.LTGRAY){
                            checkbox.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.skyblue));
                        } else{
                            checkbox.setBackgroundColor(Color.LTGRAY);
                        }
                        Toast.makeText(MainActivity.this,"checked!",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }
    }
}
