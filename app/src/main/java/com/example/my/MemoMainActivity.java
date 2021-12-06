package com.example.my;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MemoMainActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ImageButton btnadd;

    List<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        //dbHelper = new SQLiteHelper(MemoMainActivity.this);
        memoList = new ArrayList<>();
        //memoList = dbHelper.selectAll();
//        memoList.add(new Memo("test","testtest","test"));
//        memoList.add(new Memo("test1","testtest1",0));
//        memoList.add(new Memo("test2","testtest2",0));
//        memoList.add(new Memo("test3","testtest3",0));
//        memoList.add(new Memo("test4","testtest4",0));
//        memoList.add(new Memo("test5","testtest5",0));
//        memoList.add(new Memo("test6","testtest6",0));
//        memoList.add(new Memo("test7","testtest7",0));
//        memoList.add(new Memo("test8","testtest8",0));
//        memoList.add(new Memo("test9","testtest9",0));



        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MemoMainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);

        btnadd = (ImageButton) findViewById(R.id.btnAdd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoMainActivity.this,MemoAddActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 0){
            String titlemain = data.getStringExtra("title");
            String contentmain = data.getStringExtra("content");
            String strsub = data.getStringExtra("sub");

            Memo memo = new Memo(titlemain,contentmain,strsub);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            //dbHelper.insertMemo(memo);
        }

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

        private List<Memo> listdata;

        public RecyclerAdapter(List<Memo> listdata){
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i){

            Memo memo = listdata.get(i);

            itemViewHolder.maintext.setTag(memo.getSeq());

            itemViewHolder.maintext.setText(memo.getMaintext());
            itemViewHolder.contenttext.setText(memo.getContentstext());
            itemViewHolder.subtext.setText(memo.getSubtext());
        }

        void  addItem(Memo memo){
            listdata.add(memo);
        }

        void removeItem(int position){
            listdata.remove(position);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{

            private TextView maintext;
            private TextView subtext;
            private TextView contenttext;


            public  ItemViewHolder(@NonNull View itemView){
                super(itemView);

                maintext = itemView.findViewById(R.id.item_maintext);
                subtext = itemView.findViewById(R.id.item_subtext);
                contenttext = itemView.findViewById(R.id.item_contenttext);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        int position = getAdapterPosition();

                        int seq = (int)maintext.getTag();

                        if(position != RecyclerView.NO_POSITION){
                            dbHelper.deleteMemo(seq);
                            removeItem(position);
                            notifyDataSetChanged();
                        }

                        return false;
                    }
                });
            }
        }
    }
}