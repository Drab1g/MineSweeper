package com.example.usrlocal.minesweeper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BackSquareAdapter extends BaseAdapter{

    Context context;
    int size;
    Board board;

    public BackSquareAdapter(Context context, Board board){
        this.context=context;
        this.board=board;
        this.size=board.getRows()*board.getCols();
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GridView grid = (GridView)parent;
        int size = grid.getColumnWidth();

        int currentCol=position%board.getCols();
        int currentRow=(position-currentCol)/board.getCols();
        int currentValue=board.getBoard()[currentRow][currentCol];

        if(currentValue==0){
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(size, size));
                imageView.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
                imageView.setImageDrawable(context.getDrawable(R.drawable.mine_noire));
            }
            else{
                imageView=(ImageView)convertView;
            }
            return imageView;
        }
        else{
            TextView textView;
            if(convertView==null){
                textView=new TextView(context);
                textView.setLayoutParams(new GridView.LayoutParams(size, size));
                textView.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
                if (currentValue !=10) textView.setText(String.valueOf(currentValue));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(18);
                switch (currentValue){
                    case 1:
                        textView.setTextColor(context.getResources().getColor(R.color.one));
                        break;
                    case 2:
                        textView.setTextColor(context.getResources().getColor(R.color.two));
                        break;
                    case 3:
                        textView.setTextColor(context.getResources().getColor(R.color.three));
                        break;
                    case 4:
                        textView.setTextColor(context.getResources().getColor(R.color.four));
                        break;
                }
            }
            else{
                textView=(TextView)convertView;
            }
            return textView;
        }
    }
}
