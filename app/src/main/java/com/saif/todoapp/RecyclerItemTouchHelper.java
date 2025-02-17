package com.saif.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.todoapp.ui.ToDoAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private final ToDoAdapter adapter;

    public RecyclerItemTouchHelper(ToDoAdapter adapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }
    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure want to delete this Task?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            adapter.editItem(position);
            adapter.notifyItemChanged(viewHolder.getAdapterPosition());

        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerview, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurretlyActive){
        super.onChildDraw(c, recyclerview, viewHolder, dX, dY, actionState, isCurretlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if (dX>0){
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.colorPrimaryDark));
        } else{
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete);
            background = new ColorDrawable(Color.RED);
        }

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if(dX>0){ //Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicHeight();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() +((int)dX) + backgroundCornerOffset, itemView.getBottom());
        }else {
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicHeight();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int)dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }
    background.draw(c);
    icon.draw(c);
    }

}