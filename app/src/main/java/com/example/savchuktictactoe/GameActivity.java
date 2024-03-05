package com.example.savchuktictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savchuktictactoe.databinding.ActivityGameBinding;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;

    private ArrayList<ImageButton> btns = new ArrayList<>();
    private int count = 0;
    private String fname;
    private String sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        fname = intent.getStringExtra("First");
        sname = intent.getStringExtra("Second");
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        changeName();
        addBtns();
        for (int i = 0; i < btns.size(); i++) {
            final int buttonIndex = i;
            btns.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count % 2 == 0) {
                        changeImage(buttonIndex,"x");
                    } else {
                        changeImage(buttonIndex,"o");
                    }
                }
            });
        }
    }
    private void checkTie(){
        if(count==9){
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("Result","Tie");
            startActivity(intent);
        }
    }
    private void changeImage(int buttonIndex, String symb){
        if(btns.get(buttonIndex).getTag() == null){
            if(symb.equals("x")){
                btns.get(buttonIndex).setBackgroundResource(R.drawable.x);
            }else{
                btns.get(buttonIndex).setBackgroundResource(R.drawable.o);
            }
            btns.get(buttonIndex).setTag(symb);
            if(checkWinner(symb)){
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra("Result","Winner "+symb+": "+fname);
                startActivity(intent);
            }else{
                count++;
                changeName();
                checkTie();
            }
        }
    }
    private void changeName(){
        if(count%2==0){
            binding.name.setText("Move: "+fname);
        }else{
            binding.name.setText("Move: "+sname);
        }
    }
    private void addBtns(){
        btns.add(findViewById(R.id.num_1));
        btns.add(findViewById(R.id.num_2));
        btns.add(findViewById(R.id.num_3));
        btns.add(findViewById(R.id.num_4));
        btns.add(findViewById(R.id.num_5));
        btns.add(findViewById(R.id.num_6));
        btns.add(findViewById(R.id.num_7));
        btns.add(findViewById(R.id.num_8));
        btns.add(findViewById(R.id.num_9));
    }
    private boolean checkWinner(String symb) {
        for (int i = 0; i < 3; i++) {
            if (btns.get(i * 3).getTag() == symb &&
                    btns.get(i * 3 + 1).getTag() == symb &&
                    btns.get(i * 3 + 2).getTag() == symb) {
                return true;
            }
            if (btns.get(i).getTag() == symb &&
                    btns.get(i + 3).getTag() == symb &&
                    btns.get(i + 6).getTag() == symb) {
                return true;
            }
        }
        if (btns.get(0).getTag() == symb &&
                btns.get(4).getTag() == symb &&
                btns.get(8).getTag() == symb) {
            return true;
        }
        if (btns.get(2).getTag() == symb &&
                btns.get(4).getTag() == symb &&
                btns.get(6).getTag() == symb) {
            return true;
        }
        return false;
    }
}