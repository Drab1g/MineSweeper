package com.example.usrlocal.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class FrontSquareAdapter extends BaseAdapter{

    Context context;
    int size;
    Board board;

    public FrontSquareAdapter(Context context, Board board){
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final GridView grid = (GridView)parent;
        int size = grid.getColumnWidth();
        final Button button;
        if(convertView==null){
            button=new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(size, size));
            button.setBackground(context.getDrawable(R.drawable.empty_button));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int currentCol=position%board.getCols();
                    int currentRow=(position-currentCol)/board.getCols();
                    int currentValue=board.getBoard()[currentRow][currentCol];
                    //TODO
                    // switch for every state
                    switch (currentValue){
                        case 0:
                            //game over
                            for (View v : parent.getTouchables()){
                                v.setVisibility(View.GONE);
                            }
                            Toast.makeText(context.getApplicationContext(),"Game over",Toast.LENGTH_LONG).show();
                            break;

                        case 10:
                            for (int i = 0; i < board.getRows(); i++) {
                                for (int j = 0; j < board.getCols(); j++) {
                                    if(board.getBoard()[i][j] == 10){
                                        View b =  parent.getChildAt((board.getCols() * i) + j);
                                        b.setVisibility(View.GONE);
                                        boolean l = (j - 1) >= 0;
                                        boolean r = (j + 1) < board.getCols();
                                        boolean u = (i - 1) >= 0;
                                        boolean d = (i + 1) < board.getRows();

                                        if (u) {
                                            parent.getChildAt((board.getCols() * (i-1)) + j).setVisibility(View.GONE);
                                            if (l) {
                                                parent.getChildAt((board.getCols() * (i-1)) + j-1).setVisibility(View.GONE);
                                            }
                                            if (r) {
                                                parent.getChildAt((board.getCols() * (i-1)) + j+1).setVisibility(View.GONE);
                                            }
                                        }
                                        if (d) {
                                            parent.getChildAt((board.getCols() * (i+1)) + j).setVisibility(View.GONE);
                                            if (l) {
                                                parent.getChildAt((board.getCols() * (i+1)) + j-1).setVisibility(View.GONE);
                                            }
                                            if (r) {
                                                parent.getChildAt((board.getCols() * (i+1)) + j+1).setVisibility(View.GONE);
                                            }
                                        }
                                        if (l) {
                                            parent.getChildAt((board.getCols() * i) + j-1).setVisibility(View.GONE);
                                        }
                                        if (r) {
                                            parent.getChildAt((board.getCols() * i) + j+1).setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                            break;
                        default:
                            view.setVisibility(View.GONE);
                            break;
                    }
                }
            });
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    button.setBackground(context.getDrawable(R.drawable.flag_button));
                    return true;
                }
            });
        }
        else{
            button=(Button)convertView;
        }
        return button;
    }
}
